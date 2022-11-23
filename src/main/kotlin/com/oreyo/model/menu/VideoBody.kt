package com.oreyo.model.menu

import com.google.gson.annotations.SerializedName

data class VideoBody(
    @SerializedName("video_url")
    val videoUrl: String
)
