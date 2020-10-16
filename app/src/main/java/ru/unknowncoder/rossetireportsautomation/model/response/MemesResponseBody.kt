package ru.unknowncoder.rossetireportsautomation.model.response

import com.google.gson.annotations.SerializedName

data class MemesResponseBody(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("isFavorite") val isFavorite: Boolean,
    @SerializedName("createdDate") val createdDate: Int,
    @SerializedName("photoUrl") val photoUrl: String?
)