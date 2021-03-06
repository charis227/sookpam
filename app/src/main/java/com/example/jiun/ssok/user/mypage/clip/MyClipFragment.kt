package com.example.jiun.ssok.user.mypage.clip

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.jiun.ssok.R
import com.example.jiun.ssok.util.RecyclerItemClickListener
import com.example.jiun.ssok.model.DualModel
import com.example.jiun.ssok.model.RecordDBManager
import com.example.jiun.ssok.model.vo.RecordVO
import com.example.jiun.ssok.server.ApiUtils
import com.example.jiun.ssok.server.RecordResponse
import com.example.jiun.ssok.util.DateFormatter
import com.example.jiun.ssok.util.MsgContentGenerator
import com.example.jiun.ssok.web.WebContentActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_my_clip.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MyClipFragment : Fragment() {
    private var adapter: ClipItemRecyclerViewAdapter? = null
    private lateinit var errorLinearLayout: LinearLayout
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbManager : ClipDBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_clip, container, false)
        recyclerView = view.recylerView
        recyclerView.visibility = View.VISIBLE
        errorLinearLayout = view.common_empty_linear
        errorLinearLayout.visibility = View.INVISIBLE
        errorImageView = view.common_error_img
        errorTextView = view.common_error_txt

        dbManager = ClipDBManager(Realm.getDefaultInstance())

        if (dbManager.select().isEmpty())
            showNoData()
        else
            search()
        return view
    }

    fun search() {
        val webList = dbManager.selectWebs()
        if(webList.isNotEmpty()) {
            var query = ""
            webList.forEach { unit ->
                query = "$query&${unit.db_id}"
            }
            Log.v("query>", query.substring(1))
            searchBoth(query.substring(1))
        }
        else {
            searchOnlyMessage()
        }
    }

    private fun searchBoth(query : String) {
        val service = ApiUtils.getClipService()
        service.getItems(query).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>) {
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                var modelList = ArrayList<DualModel>()

                val records = response.body()
                modelList.addAll(records!!.toList())
                val msgList = dbManager.selectMessages()
                msgList.forEach { unit ->
                    val recordManager = RecordDBManager(Realm.getDefaultInstance())
                    val recordVos = recordManager.contains(unit.title) as List<RecordVO>
                    modelList.add(recordVos[0])}
                //sortBy comparator
                modelList.sortByDescending { sorter(it) }
                adapter = ClipItemRecyclerViewAdapter(modelList)
                recyclerView.adapter = adapter
                recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                        RecyclerItemClickListener.OnItemClickListener { view, position ->
                            val data = modelList!!.get(position)
                            showMessageBody(data)
                        }))
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })
    }

    private fun searchOnlyMessage() {
        var modelList = ArrayList<DualModel>()
        val msgList = dbManager.selectMessages()
        msgList.forEach { unit ->
            val recordManager = RecordDBManager(Realm.getDefaultInstance())
            val recordVos = recordManager.contains(unit.title) as List<RecordVO>
            modelList.add(recordVos[0])}
        //sortBy comparator
        modelList.sortByDescending { sorter(it) }
        adapter = ClipItemRecyclerViewAdapter(modelList)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = modelList!!.get(position)
                    showMessageBody(data)
                }))
    }

    private fun sorter(item : DualModel) : String{
        if (item is RecordVO)
            return DateFormatter.getFormatted(item.message!!.date)
        else
            return (item as RecordResponse).date
    }

    private fun showMessageBody(data: DualModel) {
        if (data is RecordVO) {
            MsgContentGenerator.showMessageBody(context, data)
        } else {
            val intent = Intent(context, WebContentActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("record", data as RecordResponse)
            intent!!.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun showNoData() {
        recyclerView.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
    }

}
