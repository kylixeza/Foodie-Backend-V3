package com.oreyo.model.history

import com.google.gson.annotations.SerializedName

data class HistoryUpdateBody(
	
	@SerializedName("transaction_id")
	val transactionId: String,
	
	@SerializedName("status")
	val status: String,
)