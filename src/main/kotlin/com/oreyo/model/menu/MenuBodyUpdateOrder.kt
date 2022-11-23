package com.oreyo.model.menu

import com.google.gson.annotations.SerializedName

data class MenuBodyUpdateOrder(
	@field:SerializedName("ordered")
	val ordered: Int
)
