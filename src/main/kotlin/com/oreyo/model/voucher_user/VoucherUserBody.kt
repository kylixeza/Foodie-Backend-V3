package com.oreyo.model.voucher_user

import com.google.gson.annotations.SerializedName

data class VoucherUserBody(
	@field:SerializedName("voucher_id")
	val voucherId: String,
	
	@field:SerializedName("uid")
	val uid: String
)
