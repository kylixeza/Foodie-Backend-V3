package com.oreyo.route.challenge

import io.ktor.server.locations.*

@KtorExperimentalLocationsAPI
sealed class ChallengeRouteLocation {
	companion object {
		//GET
		const val CHALLENGE = "/challenge/{uid}"
		//POST
		const val POST_CHALLENGE = "/challenge"
		//POST  
		const val JOIN_CHALLENGE = "/challenge/{uid}/{challengeId}/join"
		//GET
		const val JOINED_CHALLENGE = "/challenge/{uid}/joined"
		//POST
		const val POST_CHALLENGE_MENU = "/challenge/menu"
		//GET
		const val GET_DETAIL_CHALLENGE = "/challenge/{challengeId}/detail"
	}
	
	@Location(CHALLENGE)
	data class ChallengeGetAvailableListRoute(val uid: String)
	
	@Location(POST_CHALLENGE)
	class ChallengePostRoute
	
	@Location(JOIN_CHALLENGE)
	data class ChallengeJoinRoute(val uid: String, val challengeId: String)
	
	@Location(POST_CHALLENGE_MENU)
	class ChallengeMenuPostRoute
	
	@Location(JOINED_CHALLENGE)
	data class ChallengeGetJoinedListRoute(val uid: String)
	
	@Location(GET_DETAIL_CHALLENGE)
	data class ChallengeDetailRoute(val challengeId: String)
}
