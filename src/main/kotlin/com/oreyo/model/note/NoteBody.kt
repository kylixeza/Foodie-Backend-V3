package com.oreyo.model.note

import com.google.gson.annotations.SerializedName

data class NoteBody (
	
	@field:SerializedName("category")
	val category: String,
	
	@field:SerializedName("date")
	val date: String,
	
	@field:SerializedName("time")
	val time: String,
	
	@field:SerializedName("food")
	val food: String,
	
	@field:SerializedName("information")
	val information: String,
	
	@field:SerializedName("calories")
	val calories: Int,
	
	@field:SerializedName("portion")
	val portion: Double
)