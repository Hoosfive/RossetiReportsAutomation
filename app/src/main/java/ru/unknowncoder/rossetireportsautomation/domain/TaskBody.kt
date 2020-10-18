package ru.unknowncoder.rossetireportsautomation.domain

import com.google.gson.annotations.SerializedName

data class TaskBody(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("isFavorite") val isFavorite: Boolean,
    @SerializedName("createdDate") val createdDate: Int,
    @SerializedName("photoUrl") val photoUrl: String?
)