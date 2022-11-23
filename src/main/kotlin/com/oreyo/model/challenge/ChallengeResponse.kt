package com.oreyo.model.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeResponse(
	@field:SerializedName("challenge_id")
	val challengeId: String,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("description")
	val description: String,
	
	@field:SerializedName("participants")
	val participants: List<ParticipantResponse>?,
	
	@field:SerializedName("participants_count")
	val participantsCount: Int,
	
	@field:SerializedName("xp_earned")
	val xpEarned: Int,
	
	@field:SerializedName("is_joined")
	val isJoined: Boolean,
)

data class ParticipantResponse(
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("challenge_id")
	val challengeId: String,
	
	@field:SerializedName("avatar")
	val avatar: String,
)
