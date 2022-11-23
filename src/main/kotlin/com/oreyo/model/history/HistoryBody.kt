package com.oreyo.model.history

import com.google.gson.annotations.SerializedName

data class HistoryBody(
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("variant")
	val variant: String,
	
	@field:SerializedName("status")
	val status: String
)
