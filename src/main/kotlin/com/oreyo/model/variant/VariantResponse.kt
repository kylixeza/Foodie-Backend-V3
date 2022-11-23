package com.oreyo.model.variant

import com.google.gson.annotations.SerializedName

data class VariantResponse(
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("composition")
	val composition: String,
	
	@field:SerializedName("image")
	val image: String,
	
	@field:SerializedName("price")
	val price: Int,
	
	@field:SerializedName("variant")
	val variant: String,
)
