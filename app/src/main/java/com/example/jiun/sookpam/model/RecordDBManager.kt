package com.example.jiun.sookpam.model

import android.content.Context
import android.util.Log
import com.example.jiun.sookpam.message.MessageList
import com.example.jiun.sookpam.model.vo.*
import io.realm.Realm

class RecordDBManager(val realm: Realm) {
    private lateinit var context: ContactDBManager

    fun categorizeMessages(context: Context) {
        this.context = context as ContactDBManager
        var messageList = MessageList(realm).getList()
        for (message in messageList) {
            if (fromUniversity(message.phoneNumber) and doesNotExist(message.body))
                createMessageCategory(message)
        }
    }

    private fun fromUniversity(phoneNumber: String?): Boolean {
        var result = realm.where(ContactVO::class.java).equalTo("phone", phoneNumber).findFirst()
        return (result != null)
    }

    private fun doesNotExist(value: String?): Boolean {
        var result = realm.where(RecordVO::class.java).equalTo("message.body", value).findFirst()
        return (result == null)
    }

    private fun createMessageCategory(message: MessageVO) {
        realm.executeTransaction { realm ->
            var recordRecord: RecordVO = realm.createObject(RecordVO::class.java)
            val department: String? = context.getDepartmentOf(message.phoneNumber, realm)
            recordRecord.message = message
            recordRecord.division = department
            recordRecord.category = context.getCategory(department,realm)
        }
    }

    fun getDataByDivision(request: String): ArrayList<String> {
        var messageList = realm.where(RecordVO::class.java).equalTo("division", request).findAll()
        var responseList: ArrayList<String> = ArrayList<String>()

        for (record in messageList) {
            if (record.message != null) {
                val msgBody: String by lazy<String> { (record.message as MessageVO).body }
                responseList.add(msgBody)
            }
        }
        return responseList
    }
}