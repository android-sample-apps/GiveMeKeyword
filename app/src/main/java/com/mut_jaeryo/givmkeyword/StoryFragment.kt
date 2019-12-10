package com.mut_jaeryo.givmkeyword


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mut_jaeryo.givmkeyword.database.BasicDB

/**
 * A simple [Fragment] subclass.
 */
class StoryFragment : Fragment() {

    lateinit var GoalTextView:TextView

    lateinit var TodayGoal:String

    override fun onResume() {
        super.onResume()
        TodayGoal = BasicDB.getKeyword(context!!) ?: ""
        GoalTextView.text = TodayGoal

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GoalTextView = view.findViewById(R.id.today_story_goal)
    }

}
