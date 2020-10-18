package ru.unknowncoder.rossetireportsautomation.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import ru.unknowncoder.rossetireportsautomation.domain.LoginDataBody
import ru.unknowncoder.rossetireportsautomation.domain.UserBody

class Preferences {
    companion object {
        private const val USER_DATA_PREFERENCES = "UserData"
        private const val USER_TOKEN = "user_token"
        private const val EMAIL_PREFERENCES = "email"
        private const val FIRST_NAME_PREFERENCES = "firstName"
        private const val LAST_NAME_PREFERENCES = "lastName"
        private const val PATRONYMIC_PREFERENCES = "patronymic"
        private const val POINT_PREFERENCES = "point"
        private const val DEF_VALUE = "no_data_found"
        private lateinit var editor: SharedPreferences.Editor


        private fun getPref(context: Context): SharedPreferences {
            return context.getSharedPreferences(USER_DATA_PREFERENCES, MODE_PRIVATE)
        }

        fun editUserLoginDataPref(context: Context, loginDataBody: LoginDataBody) {
            editor = getPref(
                context
            ).edit()
            editor.putString(USER_TOKEN, loginDataBody.uid)
            editor.putString(EMAIL_PREFERENCES, loginDataBody.email)
            editor.apply()
        }

        fun editUserInfoPrefs(context: Context, userBody: UserBody) {
            editor = getPref(
                context
            ).edit()
            editor.putString(FIRST_NAME_PREFERENCES, userBody.firstName)
            editor.putString(LAST_NAME_PREFERENCES, userBody.lastName)
            editor.putString(PATRONYMIC_PREFERENCES, userBody.patronymic)
            editor.putString(POINT_PREFERENCES, userBody.point)
            editor.apply()
        }

        fun getUserLoginDataPref(context: Context): LoginDataBody {
            val pref = getPref(context)
            return LoginDataBody(
                pref.getString(
                    USER_TOKEN,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    EMAIL_PREFERENCES,
                    DEF_VALUE
                ).toString()
            )
        }

        fun getUserInfoPrefs(context: Context): UserBody {
            val pref = getPref(context)
            return UserBody(
                pref.getString(
                    FIRST_NAME_PREFERENCES,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    LAST_NAME_PREFERENCES,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    PATRONYMIC_PREFERENCES,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    POINT_PREFERENCES,
                    DEF_VALUE
                ).toString()
            )
        }
    }
}