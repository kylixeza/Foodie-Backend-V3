package com.oreyo.model.menu

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MenuResponse(
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("benefit")
	val benefit: String,
	
	@field:SerializedName("description")
	val description: String,
	
	@field:SerializedName("difficulty")
	val difficulty: String,
	
	@field:SerializedName("calories")
	val calories: Int,
	
	@field:SerializedName("cook_time")
	val cookTime: Int,
	
	@field:SerializedName("estimated_time")
	val estimatedTime: String,
	
	@field:SerializedName("image")
	val image: String,
	
	@field:SerializedName("ordered")
	val ordered: Int,
	
	@field:SerializedName("price")
	val price: Int,
	
	@field:SerializedName("rating")
	val rating: BigDecimal?,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("type")
	val type: String,
	
	@field:SerializedName("video_url")
	val videoUrl: String,
	
	@field:SerializedName("xp_gained")
	val xpGained: Int,

	@field:SerializedName("is_available")
	val isAvailable: Boolean
)
