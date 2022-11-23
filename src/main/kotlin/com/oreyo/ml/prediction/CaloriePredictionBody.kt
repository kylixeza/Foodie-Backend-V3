package com.oreyo.ml.prediction

import com.google.gson.annotations.SerializedName

data class CaloriePredictionBody(
	@field:SerializedName("food")
	val food: String,
	
	@field:SerializedName("category")
	val category: String
)
