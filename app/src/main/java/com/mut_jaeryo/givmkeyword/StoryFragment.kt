package com.mut_jaeryo.givmkeyword


import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB

/**
 * A simple [Fragment] subclass.
 */
class StoryFragment : Fragment() {


    lateinit var TodayGoal:String

    override fun onResume() {
        super.onResume()
        TodayGoal = BasicDB.getKeyword(context!!) ?: ""
        //GoalTextView.text = TodayGoal

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false)
    }

    fun UploadedZero(){
        val notice :TextView = view!!.findViewById(R.id.story_notice)

        notice.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
             HtmlCompat.fromHtml(context!!.getString(R.string.KeywordDrawingNone), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(context!!.getString(R.string.KeywordDrawingNone))
        }
        notice.visibility= View.VISIBLE
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
