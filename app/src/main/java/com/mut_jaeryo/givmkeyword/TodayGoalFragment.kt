package com.mut_jaeryo.givmkeyword


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
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

import com.mut_jaeryo.givmkeyword.database.BasicDB
import com.mut_jaeryo.givmkeyword.keyword.Keyword
import com.mut_jaeryo.givmkeyword.view.InkView

import kotlinx.android.synthetic.main.fragment_today__goal.view.*

/**
 * A simple [Fragment] subclass.
 */
class TodayGoalFragment : Fragment() {


    private lateinit var drawView: InkView
    private var drawUtility :Boolean = false
    private var commentShow :Boolean = true
    private var usingBrush : Boolean = true
    private lateinit var commentLayout : RelativeLayout
    private lateinit var paintLayout : RelativeLayout
    private lateinit var goalTextView: TextView
    private var selecteColorIndex = 0
    private lateinit var commentBtn : ImageButton
    private lateinit var drawBtn : ImageButton
    private lateinit var MaxsizeSeekBar: SeekBar
    private lateinit var MinsizeSeekBar: SeekBar
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

        MaxsizeSeekBar = paintLayout.findViewById(R.id.today_goal_draw_Maxsize_SeekBar)
        MinsizeSeekBar =  paintLayout.findViewById(R.id.today_goal_draw_Minsize_SeekBar)
        alphaSeekBar = paintLayout.findViewById(R.id.today_goal_draw_alpha_SeekBar)

        drawView.setAlpha(alphaSeekBar.progress)
        drawView.setMaxStrokeWidth(MaxsizeSeekBar.progress/2f)
        drawView.setMinStrokeWidth(MinsizeSeekBar.progress/2f)

        MaxsizeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
             drawView.setMaxStrokeWidth(p1/2f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        MinsizeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p1>MaxsizeSeekBar.progress)
                {
                    Toast.makeText(context,"최대 크기보다 작아야 합니다.",Toast.LENGTH_LONG).show()
                    MinsizeSeekBar.progress = MaxsizeSeekBar.progress
                }else
                drawView.setMinStrokeWidth(p1/2f)
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


        paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_delete).setOnClickListener { drawView.clear() }

        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_black).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_blue).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_green).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_pink).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_red).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_yellow).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_darkBlue).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_darkGreen).setOnClickListener(colorClickListener)
        paintLayout.findViewById<ImageView>(R.id.today_goal_draw_color_wood).setOnClickListener(colorClickListener)
    }

    private val colorClickListener = View.OnClickListener {
        if(!usingBrush){
            usingBrush=true
            paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_brush).setImageResource(R.drawable.brush_selected)
            paintLayout.findViewById<ImageButton>(R.id.today_goal_draw_eraser).setImageResource(R.drawable.eraser)
        }
         val index =  when(it.id)
          {
              R.id.today_goal_draw_color_black -> {
                  brushColor = Color.BLACK
                  0
              }
             R.id.today_goal_draw_color_blue-> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.colorPrimary)
                 }else {
                     resources.getColor(R.color.colorPrimary)
                 }
                 1

             }

             R.id.today_goal_draw_color_green -> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.green)
                 }else {
                     resources.getColor(R.color.green)
                 }
                 2
             }

             R.id.today_goal_draw_color_pink -> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.pink)
                 }else {
                     resources.getColor(R.color.pink)
                 }
                 3
             }

             R.id.today_goal_draw_color_red -> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.red)
                 }else {
                     resources.getColor(R.color.red)
                 }
                 4
             }

             R.id.today_goal_draw_color_yellow -> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.yellow)
                 }else {
                     resources.getColor(R.color.yellow)
                 }
                 5
             }

             R.id.today_goal_draw_color_darkBlue -> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.darkBlue)
                 }else {
                     resources.getColor(R.color.darkBlue)
                 }
                 6
             }

             R.id.today_goal_draw_color_darkGreen -> {
                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.darkGreen)
                 }else {
                     resources.getColor(R.color.darkGreen)
                 }
                 7
             }

             else -> {

                 brushColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     context!!.getColor(R.color.wood)
                 }else {
                     resources.getColor(R.color.wood)
                 }
                     8

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

            5-> R.id.today_goal_draw_color_yellow

            6 -> R.id.today_goal_draw_color_darkBlue

            7 -> R.id.today_goal_draw_color_darkGreen

            else -> R.id.today_goal_draw_color_wood
        }

        val it:ImageView = paintLayout.findViewById(res)


        it.setPadding(resources.getDimension(R.dimen.colorUnSelect).toInt())
    }
}


