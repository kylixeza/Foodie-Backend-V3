package com.oreyo.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
	@SerializedName("status")
	val status: String = "",
	
	@SerializedName("message")
	val message: String = "",
	
	@SerializedName("data")
	val data: T?
	
)
