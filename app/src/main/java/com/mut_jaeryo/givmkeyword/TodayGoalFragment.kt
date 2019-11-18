package com.mut_jaeryo.givmkeyword


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.divyanshu.draw.widget.DrawView

/**
 * A simple [Fragment] subclass.
 */
class TodayGoalFragment : Fragment() {


    private lateinit var dragView: DrawView
    private var drawUtility :Boolean = false
    private var commentShow :Boolean = true
    private lateinit var commentLayout : RelativeLayout
    private lateinit var goalTextView: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today__goal, container, false)
        val centerText:TextView  = view.findViewById(R.id.today_goal_t2)
        dragView = view.findViewById(R.id.draw_view)
        dragView.setOnTouchListener{ _: View, motionEvent: MotionEvent ->
            dragView.onTouchEvent(motionEvent)

            if(centerText.visibility == View.VISIBLE)
                centerText.visibility = View.GONE
            true
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        commentLayout = view.findViewById(R.id.today_goal_layout)
        goalTextView = commentLayout.findViewById(R.id.today_goal_realGoal)
        commentLayout.findViewById<ImageButton>(R.id.today_goal_refresh).setOnClickListener{
            goalTextView.text = ""
            goalTextView.visibility = View.GONE

            Handler().postDelayed({
                goalTextView.visibility=View.VISIBLE
                goalTextView.text = "가로등 밑에서 비를 맞고 있는 사람"
            },1000)
        }

        view.findViewById<ImageButton>(R.id.today_goal_draw_utility).setOnClickListener{
            it as ImageButton
            drawUtility = !drawUtility
            if(drawUtility){
                it.setImageResource(R.drawable.draw_utility_selected)
            }else
                it.setImageResource(R.drawable.draw_utility)
        }

        view.findViewById<ImageButton>(R.id.today_goal_show).setOnClickListener {
            it as ImageButton
            commentShow = !commentShow
            if (commentShow) {
                it.setImageResource(R.drawable.comment_selected)
                commentLayout.visibility = View.VISIBLE
            } else {
                it.setImageResource(R.drawable.comment)
                commentLayout.visibility = View.GONE
            }
        }

    }


}
