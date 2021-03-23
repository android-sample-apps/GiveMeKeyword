package com.mut_jaeryo.givmkeyword.ui.story


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.databinding.FragmentStoryBinding
import com.mut_jaeryo.givmkeyword.ui.main.MainActivity
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.mut_jaeryo.givmkeyword.utils.database.DrawingDB
import com.mut_jaeryo.givmkeyword.view.adapter.DrawingAdapter
import com.mut_jaeryo.givmkeyword.view.Items.RecyclerDecoration
import com.mut_jaeryo.givmkeyword.entities.DrawingItem
import com.tistory.blackjinbase.base.BaseFragment

class StoryFragment : BaseFragment<FragmentStoryBinding>(R.layout.fragment_story) {

    override var logTag: String = "StoryFragment"

    lateinit var TodayGoal: String
    lateinit var adater: DrawingAdapter

    var newest = true
    var mode: StoryMode = StoryMode.NEW

    var keyword_array: ArrayList<DrawingItem>? = null
    var hottest_array: ArrayList<DrawingItem>? = null
    var myArt_array: ArrayList<DrawingItem>? = null

    var isPaging = false

    var uploadWork: Int = 0
    var hottest_more = true
    var hottest_last: DocumentSnapshot? = null
    var newest_more = true
    var newest_last: DocumentSnapshot? = null
    var myArt_more = true
    var myArt_last: DocumentSnapshot? = null


//    override fun onResume() {
//        super.onResume()
//        val keyword = Preference.getKeyword(context!!) ?: ""
//        if (TodayGoal != keyword || uploadWork < Preference.getWork(context!!)) {
//            TodayGoal = keyword
//
//            uploadWork = Preference.getWork(context!!)
//            newest_more = true
//            hottest_more = true
//            myArt_more = true
//            myArt_last = null
//            newest = true
//            newest_last = null
//            hottest_array = null
//            hottest_last = null
//            //GoalTextView.text = TodayGoal
//            drawing_story_progress.visibility = View.VISIBLE
//            drawing_story_progress.spin()
//            keyword_array = null
//            hottest_array = null
//            myArt_array = null
//
//            when (mode) {
//                StoryMode.NEW -> {
//                    keyword_array = ArrayList()
//                }
//
//                StoryMode.HOT -> {
//                    hottest_array = ArrayList()
//                }
//
//                StoryMode.MY -> {
//                    myArt_array = ArrayList()
//                }
//            }
//            SettingRecycler()
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false)
    }


    private fun uploadedZero() {
        if (mode != StoryMode.MY) {
            binding.storyNotice.text =
                HtmlCompat.fromHtml(requireContext().getString(R.string.keyword_drawing_none), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.storyNotice.text = "내 그림이 존재하지 않습니다 ㅠㅠ\n 그림을 올려주세요"
        }
        binding.storyNotice.visibility = View.VISIBLE
        binding.storyRecycler.visibility = View.GONE
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        keyword_array = ArrayList()
//        TodayGoal = Preference.getKeyword(context!!) ?: ""
//        val spaceDecoration = RecyclerDecoration(40)
//
//        uploadWork = Preference.getWork(context!!) ?: 0
//        adater = DrawingAdapter(keyword_array!!, activity!!)
//        story_recycler.addItemDecoration(spaceDecoration)
//        story_recycler.adapter = adater
//        var spanCount = 2
//        if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            spanCount = 3
//        }
//
//        story_recycler.layoutManager = GridLayoutManager(context, spanCount)
//
//
//        //페이징 처리
//        story_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (!story_recycler.canScrollVertically(-1)) {
//                    Log.i("list", "Top of list")
//                } else if (!story_recycler.canScrollVertically(1)) {
//                    Log.i("list", "End of list")
//                    if ((mode == StoryMode.NEW && newest_more) && !isPaging || (mode == StoryMode.HOT && hottest_more) && !isPaging || (mode == StoryMode.MY && myArt_more) && !isPaging) {
//                        drawing_story_progress.visibility = View.VISIBLE
//                        drawing_story_progress.spin()
//                        initRecycler()
//                        isPaging = true
//                    }
//                } else {
//                    Log.i("list", "idle")
//                }
//            }
//        })
//
//
//        sort_newest.setOnTouchListener { view: View, motionEvent: MotionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    if (mode != StoryMode.NEW) {
//                        (view as Button).background = context!!.getDrawable(R.drawable.round_ripple)
//                        if (mode == StoryMode.HOT)
//                            sort_hottest.setBackgroundResource(0)
//                        else {
//                            sort_myArt.setBackgroundResource(0)
//                        }
//                    }
//                }
//            }
//            //true하면 터치가 막힌다.
//            false
//        }
//
//        sort_newest.setOnClickListener {
//
//            if (mode != StoryMode.NEW) {
//                mode = StoryMode.NEW
//                drawing_story_progress.visibility = View.VISIBLE
//                story_recycler.visibility = View.INVISIBLE
//                drawing_story_progress.spin()
//
//                if (keyword_array!!.size == 0) {
//                    drawing_story_progress.stopSpinning()
//                    uploadedZero()
//                } else {
//                    if (story_notice.visibility == View.VISIBLE)
//                        story_notice.visibility = View.GONE
//                    adater.changeArray(keyword_array!!)
//                    story_recycler.visibility = View.VISIBLE
//                    adater.notifyDataSetChanged()
//                    drawing_story_progress.stopSpinning()
//                }
//            }
//        }
//
//        sort_myArt.setOnTouchListener { view: View, motionEvent: MotionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    if (mode != StoryMode.MY) {
//
//                        (view as Button).background = context!!.getDrawable(R.drawable.round_ripple)
//                        if (mode == StoryMode.NEW)
//                            sort_newest.setBackgroundResource(0)
//                        else
//                            sort_hottest.setBackgroundResource(0)
//                    }
//                }
//            }
//            //true하면 터치가 막힌다.
//            false
//        }
//
//        sort_myArt.setOnClickListener {
//            if (Preference.getName(context!!) == "이름 미정") {
//
//                (activity as MainActivity).goToEditName()
//                AlertUtills.BasicAlert(context!!, "이름을 등록해주세요!")
//
//                mode = StoryMode.MY
//                story_notice.visibility = View.GONE
//                // NEW mode로 변경
//                //callOnClick onClickListener만 호출 , performClick 사용자의 실제 클릭효과까지 호출
//                // sort_newest.callOnClick()
//            } else if (mode != StoryMode.MY) {
//                drawing_story_progress.visibility = View.VISIBLE
//                story_recycler.visibility = View.INVISIBLE
//                drawing_story_progress.spin()
//
//                mode = StoryMode.MY
//                if (myArt_array == null) {
//                    if (story_notice.visibility == View.VISIBLE)
//                        story_notice.visibility = View.GONE
//                    myArt_array = ArrayList()
//                    adater.changeArray(myArt_array!!)
//                    initRecycler()
//                } else {
//                    if (myArt_array!!.size == 0) {
//                        drawing_story_progress.stopSpinning()
//                        uploadedZero()
//                    } else {
//                        if (story_notice.visibility == View.VISIBLE)
//                            story_notice.visibility = View.GONE
//                        adater.changeArray(myArt_array!!)
//                        story_recycler.visibility = View.VISIBLE
//                        adater.notifyDataSetChanged()
//                        drawing_story_progress.stopSpinning()
//                    }
//                }
//            }
//        }
//
//        binding.sortHottest.setOnTouchListener { view: View, motionEvent: MotionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    if (mode != StoryMode.HOT) {
//                        (view as Button).background = requireContext().getDrawable(R.drawable.round_ripple)
//                        if (mode == StoryMode.NEW)
//                            binding.sortNewest.setBackgroundResource(0)
//                        else
//                            binding.sortMyArt.setBackgroundResource(0)
//                    }
//                }
//            }
//            false
//        }
//
//        binding.sortHottest.setOnClickListener {
//            if (mode != StoryMode.HOT) //좋아요순으로 바꾸기
//            {
//                binding.drawingStoryProgress.visibility = View.VISIBLE
//                binding.storyRecycler.visibility = View.INVISIBLE
//                binding.drawingStoryProgress.spin()
//                mode = StoryMode.HOT
//                if (hottest_array == null) {
//                    if (binding.storyNotice.visibility == View.VISIBLE)
//                        binding.storyNotice.visibility = View.GONE
//                    hottest_array = ArrayList()
//                    adater.changeArray(hottest_array!!)
//                    initRecycler()
//                } else {
//                    if (hottest_array!!.size == 0) {
//                        binding.drawingStoryProgress.stopSpinning()
//                        uploadedZero()
//                    } else {
//                        if (binding.storyNotice.visibility == View.VISIBLE)
//                            binding.storyNotice.visibility = View.GONE
//                        adater.changeArray(hottest_array!!)
//                        binding.storyRecycler.visibility = View.VISIBLE
//                        adater.notifyDataSetChanged()
//                        binding.drawingStoryProgress.stopSpinning()
//                    }
//                }
//            }
//        }
//
//        initRecycler()
//    }

    fun initRecycler() {

        Log.d("test", "불러오기")
        val db = FirebaseFirestore.getInstance()
        var more_check = 0

        var array: ArrayList<DrawingItem> = keyword_array!!
        var last = newest_last
        val collectionRef = db.collection(TodayGoal)
        var query: Query = collectionRef.limit(25)

        when (mode) {
            StoryMode.HOT -> {
                query = collectionRef.orderBy("heart", Query.Direction.DESCENDING).limit(25)
                array = hottest_array!!
                last = hottest_last
            }
            StoryMode.MY -> {
                Log.d("test", "내 그림")
          //      query = db.collection("users").document(Preference.getName(context!!)!!).collection("images").limit(25)
                array = myArt_array!!
                last = myArt_last
            }
        }


        if (last != null)
            query = query.startAfter(last)

        query.get()
                .addOnSuccessListener { documents ->

                    if (mode != StoryMode.MY) {
                        if (documents.size() > 0) {

                            if (mode == StoryMode.NEW)
                                newest_last = documents.documents[documents.size() - 1]
                            else
                                hottest_last = documents.documents[documents.size() - 1]

                        }
                        for (document in documents) {
                            more_check++
                            val name: String = document.getString("name") ?: "알수없음"
                            val content: String = document.getString("content") ?: ""
                            val heartNum: Int = document.getLong("heart")?.toInt() ?: 0
                            array.add(DrawingItem(document.id, TodayGoal, name, content, heartNum, DrawingDB.db.getMyHeart(document.id)))

                        }

                        if (more_check < 25) {
                            if (mode == StoryMode.NEW) newest_more = false
                            else hottest_more = false
                        }

                        if (array.size > 0) {

                            if (binding.storyRecycler.visibility == View.INVISIBLE || binding.storyRecycler.visibility == View.GONE)
                                binding.storyNotice.visibility = View.VISIBLE
                            if (binding.drawingStoryProgress.isSpinning) {
                                binding.drawingStoryProgress.stopSpinning()
                                binding.drawingStoryProgress.visibility = View.INVISIBLE
                            }
                            if (binding.storyNotice.visibility == View.VISIBLE)
                                binding.storyNotice.visibility = View.GONE
                            Log.d("test", array.toString())
                            adater.notifyDataSetChanged()

                        } else {
                            uploadedZero()
                            if (binding.drawingStoryProgress.isSpinning) {
                                binding.drawingStoryProgress.stopSpinning()
                                binding.drawingStoryProgress.visibility = View.INVISIBLE
                            }
                        }
                        isPaging = false
                    } else {
                        //페이징
                        if (documents.size() > 0)
                            myArt_last = documents.documents[documents.size() - 1]
                        else {
                            uploadedZero()
                            if (binding.drawingStoryProgress.isSpinning) {
                                binding.drawingStoryProgress.stopSpinning()
                                binding.drawingStoryProgress.visibility = View.INVISIBLE
                            }
                        }
                        for (document in documents) {
                            more_check++
                            Log.d("test", document.toString())
                            val id = document.id
                            val keyword = document.getString("keyword") ?: ""
                            db.collection(keyword).document(id).get().addOnSuccessListener {
                                Log.d("test", it.toString())
                                val name: String = it.getString("name") ?: "알수없음"
                                val content: String = it.getString("content") ?: ""
                                val heartNum: Int = it.getLong("heart")?.toInt() ?: 0
                                array.add(DrawingItem(document.id, keyword, name, content, heartNum, DrawingDB.db.getMyHeart(id)))

                                if (binding.storyRecycler.visibility == View.INVISIBLE)
                                    binding.storyRecycler.visibility = View.VISIBLE
                                if (binding.drawingStoryProgress.isSpinning) {
                                    binding.drawingStoryProgress.stopSpinning()
                                    binding.drawingStoryProgress.visibility = View.INVISIBLE
                                }
                                if (binding.storyNotice.visibility == View.VISIBLE)
                                    binding.storyNotice.visibility = View.GONE
                                adater.notifyDataSetChanged()

                            }.addOnFailureListener {
                                uploadedZero()
                                if (binding.drawingStoryProgress.isSpinning) {
                                    binding.drawingStoryProgress.stopSpinning()
                                    binding.drawingStoryProgress.visibility = View.INVISIBLE
                                }
                            }.addOnCanceledListener {
                                uploadedZero()
                                if (binding.drawingStoryProgress.isSpinning) {
                                    binding.drawingStoryProgress.stopSpinning()
                                    binding.drawingStoryProgress.visibility = View.INVISIBLE
                                }
                            }
                        }

                        if (more_check < 25) {
                            myArt_more = false
                        }

                        isPaging = false
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("GetDrawing", "Error getting documents: ", exception)
                    uploadedZero()
                    if (binding.drawingStoryProgress.isSpinning) {
                        binding.drawingStoryProgress.stopSpinning()
                        binding.drawingStoryProgress.visibility = View.INVISIBLE
                    }
                    isPaging = false
                }
    }
}
