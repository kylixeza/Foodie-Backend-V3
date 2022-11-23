package com.oreyo.model.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
	
	@field:SerializedName("transaction_id")
	val transactionId: String,
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("time_stamp")
	val timeStamp: String,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("image")
	val image: String,
	
	@field:SerializedName("variant")
	val variant: String,
	
	@field:SerializedName("status")
	val status: String,
	
	@field:SerializedName("stars_given")
	val starsGiven: Int
)
