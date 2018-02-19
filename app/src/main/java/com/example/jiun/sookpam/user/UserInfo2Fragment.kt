package com.example.jiun.sookpam.user

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_user_info2.*


class UserInfo2Fragment : Fragment() {
    private var userInfo2View: View? = null
    private var userInfo2Activity: Activity? = null
    private var userInfo2Context: Context? = null
    private lateinit var schoolScholarShipCheckBox: CheckBox
    private lateinit var suburbanScholarShipCheckBox: CheckBox
    private lateinit var governmentScholarShipCheckBox: CheckBox
    private lateinit var studentStatusRadioGroup: RadioGroup
    private lateinit var statusInRadioButton: RadioButton
    private lateinit var statusOutRadioButton: RadioButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userInfo2View = inflater.inflate(R.layout.fragment_user_info2, container, false)
        userInfo2Activity = activity
        userInfo2Context = userInfo2View!!.context
        return userInfo2View
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    override fun onResume() {
        super.onResume()
        loadPage2Data()
    }

    private fun initialize() {
        schoolScholarShipCheckBox = user_info2_school_scholarship_checkbox
        suburbanScholarShipCheckBox = user_info2_suburban_scholarship_checkbox
        governmentScholarShipCheckBox = user_info2_government_scholarship_checkbox
        studentStatusRadioGroup = user_info2_status_radio_group
        statusInRadioButton = user_info2_status_in_radio_btn
        statusOutRadioButton = user_info2_status_out_radio_btn
        setCheckBoxListener(schoolScholarShipCheckBox)
        setCheckBoxListener(suburbanScholarShipCheckBox)
        setCheckBoxListener(governmentScholarShipCheckBox)
        setStatusListener()
    }

    private fun loadPage2Data() {
        loadCheckBoxData(schoolScholarShipCheckBox)
        loadCheckBoxData(suburbanScholarShipCheckBox)
        loadCheckBoxData(governmentScholarShipCheckBox)
        loadRadioGroupData(studentStatusRadioGroup)
    }

    private fun loadRadioGroupData(radioGroup: RadioGroup) {
        val selectedRadioButton = SharedPreferenceUtil.get(userInfo2Context, STUDENT_STATUS, true)
        if (selectedRadioButton) {
            radioGroup.check(statusInRadioButton.id)
        } else {
            radioGroup.check(statusOutRadioButton.id)
        }
    }

    private fun loadCheckBoxData(checkBox: CheckBox) {
        checkBox.isChecked = SharedPreferenceUtil.get(userInfo2Context, checkBox.text.toString(), false)
    }

    private fun setCheckBoxListener(checkBox: CheckBox) {
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                SharedPreferenceUtil.set(userInfo2Context, checkBox.text.toString(), CHECKED)
            } else {
                SharedPreferenceUtil.set(userInfo2Context, checkBox.text.toString(), UNCHECKED)
            }
        }
    }

    private fun setStatusListener() {
        studentStatusRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                statusInRadioButton.id -> {
                    SharedPreferenceUtil.set(userInfo2Context, STUDENT_STATUS, statusInRadioButton.text.toString())
                }
                statusOutRadioButton.id -> {
                    SharedPreferenceUtil.set(userInfo2Context, STUDENT_STATUS, statusOutRadioButton.text.toString())
                }
            }
        }
    }

    companion object {
        const val UNCHECKED = false
        const val CHECKED = true
        const val STUDENT_STATUS = "student_status"
    }
}
