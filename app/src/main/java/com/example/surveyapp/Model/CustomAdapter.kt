package com.example.surveyapp.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.surveyapp.R

class CustomAdapter(private val appContext: Context, private val surveyTitles: ArrayList<String>,
                    private val startDateList: ArrayList<String>, private val endDateList: ArrayList<String>
) : BaseAdapter() {


    private val inflater: LayoutInflater
            = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return surveyTitles.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        view = inflater.inflate(R.layout.activity_listview, parent, false)


        val titles = view.findViewById<TextView>(R.id.textViewTitle)
        val sDate = view.findViewById<TextView>(R.id.textViewStartDate)
        val eDate = view.findViewById<TextView>(R.id.textViewEndDate)

        titles.text = surveyTitles[position]
        sDate.text = startDateList[position]
        eDate.text = endDateList[position]

        //val adminHome = AdminHomeActivity(titles)


        return view
    }



}
