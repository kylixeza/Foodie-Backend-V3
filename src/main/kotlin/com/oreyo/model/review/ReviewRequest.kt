package com.oreyo.model.review

import com.google.gson.annotations.SerializedName

data class ReviewRequest(
	@field:SerializedName("uid")
	val uid: String,
)
