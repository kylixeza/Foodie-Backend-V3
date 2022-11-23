package com.oreyo.model.challenge

import com.google.gson.annotations.SerializedName
import com.oreyo.model.menu.MenuResponse

data class ChallengeMenuResponse(
	
	@field:SerializedName("challenge_id")
	val challengeId: String,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("type")
	val type: String,
	
	@field:SerializedName("menus")
	val menus: List<MenuResponse>?
)
