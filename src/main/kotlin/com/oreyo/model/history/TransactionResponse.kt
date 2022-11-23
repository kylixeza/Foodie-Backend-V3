package com.oreyo.model.history

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
	
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("transaction_id")
	val transactionId: String,
	
	@field:SerializedName("variant")
	val variant: String,
	
	@field:SerializedName("time_stamp")
	val timeStamp: String,
	
	@field:SerializedName("price")
	val price: Int
)
