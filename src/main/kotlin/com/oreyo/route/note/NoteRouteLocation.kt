package com.oreyo.route.note

import com.oreyo.route.user.UserRouteLocation.Companion.SELECTED_USER
import io.ktor.server.locations.*

@KtorExperimentalLocationsAPI
sealed class NoteRouteLocation {
	companion object {
		const val NOTE = "$SELECTED_USER/note"
		//POST
		const val CALORIES_PREDICTION = "/calories/predict"
		//POST
		const val ADD_NOTE = NOTE
		//GET
		const val GET_NOTE = NOTE
	}
	
	@Location(CALORIES_PREDICTION)
	class CaloriesPredictionPostRoute
	
	@Location(ADD_NOTE)
	data class NotePostRoute(val uid: String)
	
	@Location(GET_NOTE)
	data class NoteGetListRoute(val uid: String)
}
