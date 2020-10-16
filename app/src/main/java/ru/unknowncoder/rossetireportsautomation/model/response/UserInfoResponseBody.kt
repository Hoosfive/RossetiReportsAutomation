package ru.unknowncoder.rossetireportsautomation.model.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponseBody(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("userDescription") val userDescription: String
)