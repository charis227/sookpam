package com.example.jiun.ssok.web.common

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.ssok.R
import com.example.jiun.ssok.util.RecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_web_common_topic.view.*

class WebCommonTopicFragment : Fragment() {
    private lateinit var webCommonTopicRecyclerView: RecyclerView
    private lateinit var topics: List<CommonTopic>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web_common_topic, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        topics = CommonTopicAdapter.getInterestOrNormalTopics(context!!)
        webCommonTopicRecyclerView = view.web_common_topic_recycler
        webCommonTopicRecyclerView.layoutManager = LinearLayoutManager(context)
        webCommonTopicRecyclerView.adapter = CommonTopicRecyclerAdapter(topics)
        webCommonTopicRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { _, position ->
            val intent = Intent(context, WebRecyclerActivity::class.java)
            intent.putExtra("category", "공통")
            intent.putExtra("division", topics[position].topicTitle)
            intent.putExtra("background", topics[position].topicImage)
            startActivity(intent)
        }))
    }
}
