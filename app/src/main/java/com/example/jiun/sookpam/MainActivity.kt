package com.example.jiun.sookpam

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

import com.example.jiun.sookpam.model.mms.MmsReader
import com.example.jiun.sookpam.model.sms.SmsReader
import com.gun0912.tedpermission.PermissionListener

import com.gun0912.tedpermission.TedPermission

class MainActivity : AppCompatActivity() {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var categoryManager: CategoryDBManager
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: MainListviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MainListviewAdapter()
        val listView = findViewById<View>(R.id.main_listView) as ListView
        listView.adapter = adapter
        initialize()
        checkMessagePermission()
        listView.onItemClickListener = AdapterView.OnItemClickListener {
            adapterView, view, position, id ->
            var selectedMain = listView.getItemAtPosition(position) as MainItem
            go(selectedMain.category)
        }
    }

    override fun onResume() {
        super. onResume()
        scatterCheckedCategories()
    }

    private fun scatterCheckedCategories() {
        val contactDBManager = applicationContext as ContactDBManager
        val categoryList = contactDBManager.getCategoryList()
        adapter.clear()
        for (category in categoryList) {
            if (SharedPreferenceUtil.get(applicationContext, category, false))
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), category)
        }
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "학교")
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "기타")
    }

    private fun checkMessagePermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                readMessageList()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                finish()
            }
        }

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleTitle(getString(R.string.read_sms_request_title))
                .setRationaleMessage(getString(R.string.read_sms_request_detail))
                .setDeniedTitle(getString(R.string.denied_read_sms_title))
                .setDeniedMessage(getString(R.string.denied_read_sms_detail))
                .setGotoSettingButtonText(getString(R.string.move_setting))
                .setPermissions(android.Manifest.permission.READ_SMS)
                .check()
    }

    private fun initialize() {
        smsReader = SmsReader()
        mmsReader = MmsReader()
        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        categoryManager = CategoryDBManager()
    }

    private fun go(category: String) {
        val intent = Intent(this, DataActivity::class.java)
        intent.putExtra("category", category);
        startActivity(intent)
    }

    private fun readMessageList() {
        smsReader.setSms(this)
        mmsReader.setMms(this)
        categoryManager.categorizeMessages(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_synchronization -> {
                scatterCheckedCategories()
                readMessageList()
                true
            }
            R.id.action_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}