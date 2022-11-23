package com.oreyo.model.step

import com.google.gson.annotations.SerializedName

data class StepResponse(
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("step")
	val step: String
)
