package com.oreyo.ml.prediction

import com.google.gson.annotations.SerializedName

data class CaloriePredictionResponse(
	
	@field:SerializedName("food")
	val food: String,
	
	@field:SerializedName("calories")
	val calories: Int,
	
	@field:SerializedName("accuracy")
	val accuracy: Double
)
