package com.oreyo.model.history

import com.google.gson.annotations.SerializedName

data class HistoryUpdateStarsGiven(
	
	@field:SerializedName("transaction_id")
	val transactionId: String,
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("stars_given")
	val starsGiven: Int
)
