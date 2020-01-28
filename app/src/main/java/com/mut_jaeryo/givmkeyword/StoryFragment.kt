package com.mut_jaeryo.givmkeyword


import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.DrawingDB
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DrawingAdapter
import com.mut_jaeryo.givmkeyword.view.Items.RecyclerDecoration
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import kotlinx.android.synthetic.main.activity_drawing_main.*
import kotlinx.android.synthetic.main.fragment_story.*

/**
 * A simple [Fragment] subclass.
 */
class StoryFragment : Fragment() {


    lateinit var TodayGoal:String
    lateinit var adater: DrawingAdapter
    var newest = true
    lateinit var Keyword_array:ArrayList<drawingItem>
    var hottest_array:ArrayList<drawingItem>? = null

    var isPaging = false
    var uploadWork : Int = 0
    var hottest_more = true
    var hottest_last : DocumentSnapshot? = null
    var newest_more = true
    var newest_last : DocumentSnapshot? = null



    override fun onResume() {
        super.onResume()

        val keyword = BasicDB.getKeyword(context!!) ?: ""
        if(TodayGoal != keyword || uploadWork < BasicDB.getWork(context!!)) {
            TodayGoal = keyword

            uploadWork = BasicDB.getWork(context!!)
            newest_more = true
            hottest_more = true
            newest = true
            newest_last = null
            hottest_array=null
            hottest_last = null
            //GoalTextView.text = TodayGoal
            drawing_story_progress.visibility = View.VISIBLE
            drawing_story_progress.spin()
            Keyword_array.clear()
            SettingRecycler()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false)
    }


    fun UploadedZero(){
        val notice :TextView = view!!.findViewById(R.id.story_notice)


        if(notice.text == "")
        notice.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
             HtmlCompat.fromHtml(context!!.getString(R.string.KeywordDrawingNone), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(context!!.getString(R.string.KeywordDrawingNone))
        }
        notice.visibility= View.VISIBLE
        story_recycler.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        Keyword_array = ArrayList()
        TodayGoal = BasicDB.getKeyword(context!!) ?: ""
        val spaceDecoration = RecyclerDecoration(40)

        adater = DrawingAdapter(Keyword_array,activity!!)
        story_recycler.addItemDecoration(spaceDecoration)
        story_recycler.adapter =adater
        var spanCount = 2
        if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
        }

        story_recycler.layoutManager = GridLayoutManager(context, spanCount)

        story_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!story_recycler.canScrollVertically(-1)) {
                    Log.i("list", "Top of list")
                } else if (!story_recycler.canScrollVertically(1)) {
                    Log.i("list", "End of list")
                    if((newest&&newest_more)&&!isPaging||(!newest&&hottest_more)&&!isPaging)
                    {
                        drawing_story_progress.visibility = View.VISIBLE
                        drawing_story_progress.spin()
                        SettingRecycler()
                        isPaging = true
                    }
                } else {
                    Log.i("list", "idle")
                }
            }
        })

        drawing_story_progress.visibility = View.VISIBLE
        drawing_story_progress.spin()


        sort_newest.setOnTouchListener{ view: View, motionEvent: MotionEvent ->
            when(motionEvent.action)
            {
                MotionEvent.ACTION_DOWN ->{
                    if(!newest) {

                        (view as Button).background= context!!.getDrawable(R.drawable.round_ripple)
                        sort_hottest.setBackgroundResource(0)

                    }
                }

            }
            //true하면 터치가 막힌다.
            false
        }

        sort_newest.setOnClickListener {
            if(!newest) //최신식으로 바꾸기
            {
                drawing_story_progress.visibility = View.VISIBLE
                story_recycler.visibility = View.INVISIBLE
                drawing_story_progress.spin()

                adater.changeArray(Keyword_array)
                story_recycler.visibility = View.VISIBLE
                adater.notifyDataSetChanged()
                drawing_story_progress.stopSpinning()
                newest= true

            }
        }
        sort_hottest.setOnTouchListener{ view: View, motionEvent: MotionEvent ->
            when(motionEvent.action)
            {
                MotionEvent.ACTION_DOWN ->{
                    if(newest) {
                        (view as Button).background= context!!.getDrawable(R.drawable.round_ripple)
                        sort_newest.setBackgroundResource(0)
                    }
                }
            }
            false
        }

        sort_hottest.setOnClickListener {
            if(newest) //좋아요순으로 바꾸기
            {
                drawing_story_progress.visibility = View.VISIBLE
                story_recycler.visibility = View.INVISIBLE
                drawing_story_progress.spin()
                newest = false
                if(hottest_array == null)
                {
                    hottest_array = ArrayList()
                    adater.changeArray(hottest_array!!)
                    SettingRecycler()
                }else {
                    adater.changeArray(hottest_array!!)
                    story_recycler.visibility = View.VISIBLE
                    adater.notifyDataSetChanged()
                    drawing_story_progress.stopSpinning()
                }
            }
        }

        SettingRecycler()
    }

    fun SettingRecycler(){

        val db = FirebaseFirestore.getInstance()
        var more_check = 0
        var array:ArrayList<drawingItem> = Keyword_array
        var last = newest_last
        val collectionRef = db.collection(TodayGoal)
        var query : Query = collectionRef.limit(25)
         if(!newest){
             Log.d("test","hottest")
             query = collectionRef.orderBy("heart", Query.Direction.DESCENDING).limit(25)
             array = hottest_array!!
             last = hottest_last
         }
        Log.d("page",last.toString())

        if(last != null)
            query = query.startAfter(last)

                query.get()
                .addOnSuccessListener { documents ->


                    Log.d("page","size: ${documents.size()}")
                    if (documents.size() > 0){
                        Log.d("page","last exist")
                        if(newest)
                        newest_last = documents.documents[documents.size() - 1]
                        else
                            hottest_last = documents.documents[documents.size() - 1]
                        Log.d("page","${documents.documents[documents.size() - 1]}")
                    }



                    for (document in documents) {
                        more_check++
                        val name: String = document.getString("name") ?: "알수없음"
                        val content: String = document.getString("content") ?: ""
                        val heartNum: Int = document.getLong("heart")?.toInt() ?: 0
                        array.add(drawingItem(document.id, TodayGoal, name, content, heartNum, DrawingDB.db.getMyHeart(document.id)))
                    }

                    if (more_check < 25) {
                        if (newest) newest_more = false
                        else hottest_more = false
                    }

                    if (array.size > 0) {
                        if (story_recycler.visibility == View.INVISIBLE)
                            story_recycler.visibility = View.VISIBLE
                        if (drawing_story_progress.isSpinning) {
                            drawing_story_progress.stopSpinning()
                            drawing_story_progress.visibility = View.INVISIBLE
                        }
                        if(story_notice.visibility == View.VISIBLE)
                         story_notice.visibility = View.GONE
                        adater.notifyDataSetChanged()
                    } else {
                        UploadedZero()
                        if (drawing_story_progress.isSpinning) {
                            drawing_story_progress.stopSpinning()
                            drawing_story_progress.visibility = View.INVISIBLE
                        }
                    }
                    isPaging = false
                }
                .addOnFailureListener { exception ->
                    Log.w("GetDrawing", "Error getting documents: ", exception)
                    UploadedZero()
                    if (drawing_story_progress.isSpinning) {
                        drawing_story_progress.stopSpinning()
                        drawing_story_progress.visibility = View.INVISIBLE
                    }
                    isPaging = false
                }
    }



}
