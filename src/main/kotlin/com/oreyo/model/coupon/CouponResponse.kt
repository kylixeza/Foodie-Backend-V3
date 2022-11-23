package com.oreyo.model.coupon

import com.google.gson.annotations.SerializedName

data class CouponResponse(
	@field:SerializedName("coupon_id")
	val couponId: String,
	
	@field:SerializedName("image_url")
	val imageUrl: String
)
