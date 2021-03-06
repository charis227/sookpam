package com.example.jiun.ssok.user.setting

import android.content.Context
import com.example.jiun.ssok.util.SharedPreferenceUtil

class SettingCategory(val context: Context) {
    companion object {
        private const val NORMAL_CATEGORY = 0
        private const val INTEREST_CATEGORY = 1
        val categories = listOf("장학", "학사", "행사", "모집", "시스템", "국제", "취업", "학생")

        fun countInterestCategories(context: Context): Int {
            return SettingCategory.categories.count { getCategoryStatus(it, context) == SettingCategory.INTEREST_CATEGORY }
        }

        private fun getCategoryStatus(key: String, context: Context): Int {
            return SharedPreferenceUtil.get(context, key, SettingCategory.NORMAL_CATEGORY)
        }
    }
}
