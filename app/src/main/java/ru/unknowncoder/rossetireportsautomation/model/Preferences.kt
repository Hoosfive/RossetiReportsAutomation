package ru.unknowncoder.rossetireportsautomation.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import ru.unknowncoder.rossetireportsautomation.model.response.UserInfoResponseBody

class Preferences {
    companion object {
        private const val USER_DATA_PREFERENCES = "UserData"
        private const val AUTH_TOKEN_PREFERENCES = "auth_token"
        private const val ID_PREFERENCES = "id"
        private const val USERNAME_PREFERENCES = "username"
        private const val FIRST_NAME_PREFERENCES = "firstName"
        private const val LAST_NAME_PREFERENCES = "lastName"
        private const val DESCRIPTION_PREFERENCES = "userDescription"
        private const val DEF_VALUE = "no_data_found"
        private lateinit var editor: SharedPreferences.Editor

        private fun getPref(context: Context): SharedPreferences {
            return context.getSharedPreferences(USER_DATA_PREFERENCES, MODE_PRIVATE)
        }

        fun editAuthTokenPref(context: Context, authToken: String) {
            editor = getPref(
                context
            ).edit()
            editor.putString(AUTH_TOKEN_PREFERENCES, authToken)
            editor.apply()
        }

        fun editUserInfoPrefs(context: Context, userInfoResponseBody: UserInfoResponseBody) {
            editor = getPref(
                context
            ).edit()
            editor.putInt(ID_PREFERENCES, userInfoResponseBody.id)
            editor.putString(USERNAME_PREFERENCES, userInfoResponseBody.username)
            editor.putString(FIRST_NAME_PREFERENCES, userInfoResponseBody.firstName)
            editor.putString(LAST_NAME_PREFERENCES, userInfoResponseBody.lastName)
            editor.putString(DESCRIPTION_PREFERENCES, userInfoResponseBody.userDescription)
            editor.apply()
        }

        fun getAuthTokenPref(context: Context): String? {
            return getPref(context).getString(
                AUTH_TOKEN_PREFERENCES,
                DEF_VALUE
            )
        }

        fun getUserInfoPrefs(context: Context): UserInfoResponseBody {
            val pref = getPref(context)
            return UserInfoResponseBody(
                pref.getInt(ID_PREFERENCES, 0),
                pref.getString(
                    USERNAME_PREFERENCES,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    FIRST_NAME_PREFERENCES,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    LAST_NAME_PREFERENCES,
                    DEF_VALUE
                ).toString(),
                pref.getString(
                    DESCRIPTION_PREFERENCES,
                    DEF_VALUE
                ).toString()
            )
        }
    }
}