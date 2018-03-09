package com.example.jiun.sookpam

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.common_topic_item.view.*

class CommonTopicRecyclerAdapter(private val topics: List<CommonTopic>) : RecyclerView.Adapter<CommonTopicRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.common_topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val topic = topics[position]
        holder!!.topicTitleTextView.text = topic.topicTitle
        holder.topicDetailTextView.text = topic.topicDetail
        holder.topicStatusTextView.text = topic.topicStatus
        if(topic.topicStatus!="INTEREST"){
            holder.topicStatusTextView.setTextColor(Color.DKGRAY)
        }
        else {
            holder.topicStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var topicTitleTextView = view.web_common_topic_title_txt
        var topicDetailTextView = view.web_common_topic_detail_txt
        var topicStatusTextView = view.web_common_topic_status_txt
    }
}
