package ru.unknowncoder.rossetireportsautomation.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("userInfo") val userInfoResponseBody: UserInfoResponseBody
)