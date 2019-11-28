package com.mut_jaeryo.givmkeyword


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.setPadding
import com.divyanshu.draw.widget.DrawView
import com.mut_jaeryo.givmkeyword.database.BasicDB
import com.mut_jaeryo.givmkeyword.keyword.Keyword
import kotlinx.android.synthetic.main.fragment_today__goal.view.*

/**
 * A simple [Fragment] subclass.
 */
class TodayGoalFragment : Fragment() {


    private lateinit var drawView: DrawView
    private var drawUtility :Boolean = false
    private var commentShow :Boolean = true
    private var usingBrush : Boolean = true
    private lateinit var commentLayout : RelativeLayout
    private lateinit var paintLayout : RelativeLayout
    private lateinit var goalTextView: TextView
    private var selecteColorIndex = 0
    private lateinit var commentBtn : ImageButton
    private lateinit var drawBtn : ImageButton
    private lateinit var sizeSeekBar: SeekBar
    private lateinit var alphaSeekBar: SeekBar

    private var brushColor = Color.rgb(0,0,0) //black

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today__goal, container, false)
        val centerText:TextView  = view.findViewById(R.id.today_goal_t2)
        drawView = view.findViewById(R.id.draw_view)
        drawView.setOnTouchListener{ _: View, motionEvent: MotionEvent ->
            drawView.onTouchEvent(motionEvent)

            if(centerText.visibility == View.VISIBLE)
                centerText.visibility = View.GONE
            true
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        commentLayout = view.findViewById(R.id.today_goal_layout)
        paintLayout = view.findViewById(R.id.today_paint_layout)
        goalTextView = commentLayout.findViewById(R.id.today_goal_realGoal)
        commentBtn = view.findViewById(R.id.today_goal_show)
        drawBtn = view.findViewById(R.id.today_goal_draw_utility)


        ContentLayoutInit()
        PaintLayoutInit()

        view.findViewById<ImageButton>(R.id.today_goal_send).setOnClickListener {
            drawView.mPaths
            Toast.makeText(context,"그림 저장",Toast.LENGTH_LONG).show()
        }

        drawBtn.setOnClickListener{
            it as ImageButton
            drawUtility = !drawUtility
            if(drawUtility){
                it.setImageResource(R.drawable.draw_utility_selected)
                paintLayout.visibility =View.VISIBLE

                if(commentShow) {
                    commentShow = false
                    commentBtn.setImageResource(R.drawable.comment)
                    commentLayout.visibility = View.GONE
                }
            }else {
                it.setImageResource(R.drawable.draw_utility)
                paintLayout.visibility = View.GONE
            }

        }

        commentBtn.setOnClickListener {
            it as ImageButton
            commentShow = !commentShow
            if (commentShow) {
                it.setImageResource(R.drawable.comment_selected)
                commentLayout.visibility = View.VISIBLE

                if(drawUtility)
                {
                    drawUtility = false
                    drawBtn.setImageResource(R.drawable.draw_utility)
                    paintLayout.visibility = View.GONE
                }
            } else {
                it.setImageResource(R.drawable.comment)
                commentLayout.visibility = View.GONE
            }
        }

    }

    fun ContentLayoutInit(){
        goalTextView.text = BasicDB.getKeyword(context!!)
        commentLayout.findViewById<ImageButton>(R.id.today_goal_refresh).setOnClickListener{
            goalTextView.text = ""
            goalTextView.visibility = View.GONE

            Handler().postDelayed({
                goalTextView.visibility=View.VISIBLE
                val keyword = Keyword.getKeyword(context!!)
                BasicDB.setKeyword(context!!,keyword)
                goalTextView.text = keyword
            },1000)
        }
    }



    fun PaintLayoutInit(){

        sizeSeekBar = paintLayout.findViewById(R.id.today_goal_draw_size_SeekBar)
        alphaSeekBar = paintLayout.findViewById(R.id.today_goal_draw_alpha_SeekBar)

        drawView.setAlpha(alphaSeekBar.progress)
        drawView.setStrokeWidth(sizeSeekBar.progress.toFloat())
        sizeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
               drawView.setStrokeWidth(p1.toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        alphaSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                drawView.setAlpha(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_brush).setOnClickListener {
           it as ImageButton

            if(!usingBrush) {
                usingBrush = true
                drawView.setColor(brushColor)
                it.setImageResource(R.drawable.brush_selected)
                paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_eraser).setImageResource(R.drawable.eraser)
            }
        }

        paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_eraser).setOnClickListener {
            it as ImageButton

            if(usingBrush)
            {
                usingBrush = false
                it.setImageResource(R.drawable.eraser_selected)
                drawView.setColor(Color.rgb(255,255,255))
                paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_brush).setImageResource(R.drawable.brush)
            }
        }

        paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_undo).setOnClickListener {
            drawView.undo()
        }

        paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_redo).setOnClickListener {
            drawView.redo()
        }

        paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_delete).setOnClickListener { drawView.clearCanvas() }

        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_black).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_blue).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_green).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_pink).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_red).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_yellow).setOnClickListener(colorClickListener)
    }

    private val colorClickListener = View.OnClickListener {

         val index =  when(it.id)
          {
              R.id.today_goal_draw_color_black -> {
                  brushColor = Color.BLACK
                  0
              }
             R.id.today_goal_draw_color_blue-> {
                 brushColor = Color.rgb(0,176,255)
                 1
             }

             R.id.today_goal_draw_color_green -> {
                 brushColor = Color.GREEN
                 2
             }

             R.id.today_goal_draw_color_pink -> {
                 brushColor = Color.rgb(213,0,249)
                 3
             }

             R.id.today_goal_draw_color_red -> {
                 brushColor = Color.RED
                 4
             }

             else -> {
                 brushColor = Color.YELLOW
                 5
             }
          }

        if(selecteColorIndex!=index)
        {
            drawView.setColor(brushColor)
            unseletedColor(selecteColorIndex)
            selecteColorIndex = index
            it.setPadding(resources.getDimension(R.dimen.colorSelect).toInt())
        }
    }

    fun unseletedColor(index : Int)
    {
        val res : Int = when (index)
        {
            0 -> R.id.today_goal_draw_color_black

            1 -> R.id.today_goal_draw_color_blue

            2 -> R.id.today_goal_draw_color_green

            3 -> R.id.today_goal_draw_color_pink

            4 -> R.id.today_goal_draw_color_red

            else -> R.id.today_goal_draw_color_yellow
        }

        val it:ImageView = paintLayout.findViewById(res)


        it.setPadding(resources.getDimension(R.dimen.colorUnSelect).toInt())
    }
}


