package com.mut_jaeryo.givmkeyword


import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.DrawingDB
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DrawingAdapter
import com.mut_jaeryo.givmkeyword.view.Items.RecyclerDecoration
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import kotlinx.android.synthetic.main.fragment_story.*

/**
 * A simple [Fragment] subclass.
 */
class StoryFragment : Fragment() {


    lateinit var TodayGoal:String
    lateinit var adater: DrawingAdapter

    lateinit var Keyword_array:ArrayList<drawingItem>

    override fun onResume() {
        super.onResume()
        TodayGoal = BasicDB.getKeyword(context!!) ?: ""
        //GoalTextView.text = TodayGoal
        Keyword_array.clear()
        SettingRecycler()
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
        SettingRecycler()
    }

    fun SettingRecycler(){


        Keyword_array.add(drawingItem("","","test","",0,false))

        adater.notifyDataSetChanged()

//        val db = FirebaseFirestore.getInstance()
//
//        db.collection(TodayGoal)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        val name: String = document.getString("name") ?: "알수없음"
//                        val content: String = document.getString("content") ?: ""
//                        val heartNum: Int = document.getLong("heart")?.toInt() ?: 0
//                        Keyword_array.add(drawingItem(document.id,TodayGoal,name, content,heartNum,DrawingDB.db.getMyHeart(document.id)))
//                    }
//
//                    if(Keyword_array.size>0)
//                    adater.notifyDataSetChanged()
//                    else
//                        UploadedZero()
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("GetDrawing", "Error getting documents: ", exception)
//                    UploadedZero()
//                }
    }


}
