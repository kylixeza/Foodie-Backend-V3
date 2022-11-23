package com.oreyo.route.voucher

import com.oreyo.route.user.UserRouteLocation.Companion.SELECTED_USER
import io.ktor.server.locations.*


@KtorExperimentalLocationsAPI
sealed class VoucherRouteLocation {
	companion object {
		//GET
		const val AVAILABLE_VOUCHER = "${SELECTED_USER}/voucher/available"
		//GET
		const val OWN_VOUCHER = "${SELECTED_USER}/voucher/own"
		//POST
		const val CLAIM_VOUCHER = "${SELECTED_USER}/voucher/{voucherId}/claim"
		//GET
		const val DETAIL_VOUCHER = "/voucher/{voucherId}"
		//PUT
		const val USE_VOUCHER = "${SELECTED_USER}/voucher/{voucherId}/use"
		//POST
		const val POST_VOUCHER = "/voucher"
	}

	@Location(AVAILABLE_VOUCHER)
	data class VoucherAvailableGetListRoute(val uid: String)
	
	@Location(OWN_VOUCHER)
	data class VoucherOwnGetListRoute(val uid: String)
	
	@Location(CLAIM_VOUCHER)
	data class VoucherClaimRoute(val uid: String, val voucherId: String)
	
	@Location(DETAIL_VOUCHER)
	data class VoucherGetDetailRoute(val voucherId: String)
	
	@Location(USE_VOUCHER)
	data class VoucherUseRoute(val uid: String, val voucherId: String)
	
	@Location(POST_VOUCHER)
	class VoucherPostRoute
}
