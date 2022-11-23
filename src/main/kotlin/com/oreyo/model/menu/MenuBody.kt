package com.oreyo.model.menu

import com.google.gson.annotations.SerializedName

data class MenuBody(
	
	@field:SerializedName("benefit")
	val benefit: String,
	
	@field:SerializedName("category")
	val category: String,
	
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
	
	@field:SerializedName("price")
	val price: Int,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("video_url")
	val videoUrl: String,
	
	@field:SerializedName("xp_gained")
	val xpGained: Int
)
