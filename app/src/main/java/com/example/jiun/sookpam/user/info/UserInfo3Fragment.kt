package com.example.jiun.sookpam.user.info

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.setting.SettingCategory
import kotlinx.android.synthetic.main.fragment_user_info3.*

class UserInfo3Fragment : Fragment() {
    private var userInfo3View: View? = null
    private var userInfo3Activity: Activity? = null
    private var userInfo3Context: Context? = null
    private var settingCategory: SettingCategory? = null
    private var detailButtons: ArrayList<Button> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo3View = inflater.inflate(R.layout.fragment_user_info3, container, false)
        userInfo3Activity = activity
        userInfo3Context = userInfo3View!!.context
        settingCategory = SettingCategory(userInfo3Context!!, SettingCategory.PAGE3)
        return userInfo3View
    }

    override fun onStart() {
        super.onStart()
        initializeDetailButtons()
    }

    override fun onResume() {
        super.onResume()
        settingCategory!!.setColorsOf(detailButtons)
    }

    private fun initializeDetailButtons() {
        detailButtons.add(user_info3_scholarship_btn)
        detailButtons.add(user_info3_academic_btn)
        detailButtons.add(user_info3_event_btn)
        detailButtons.add(user_info3_recruit_btn)
        detailButtons.add(user_info3_system_btn)
        detailButtons.add(user_info3_global_btn)
        detailButtons.add(user_info3_career_btn)
        detailButtons.add(user_info3_student_btn)

        for (button in detailButtons) {
            button.setOnClickListener {
                settingCategory!!.setCategoryButtonListener(button
                        , getString(R.string.user_info3_uninterest_already_checked))
            }
        }
    }
}
