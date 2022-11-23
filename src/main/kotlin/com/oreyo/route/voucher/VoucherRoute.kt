package com.oreyo.route.voucher

import com.oreyo.data.repository.foodie.IFoodieRepository
import com.oreyo.model.voucher.VoucherBody
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.locations.put
import io.ktor.server.request.*
import io.ktor.server.routing.*

@KtorExperimentalLocationsAPI
class VoucherRoute(
	private val repository: IFoodieRepository
) {
	
	private fun Route.getAvailableVoucher() {
		get<VoucherRouteLocation.VoucherAvailableGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAvailableVoucher(uid!!) }
		}
	}
	
	private fun Route.getOwnVoucherByUser() {
		get<VoucherRouteLocation.VoucherOwnGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getVoucherUser(uid!!) }
		}
	}
	
	private fun Route.claimVoucher() {
		post<VoucherRouteLocation.VoucherClaimRoute> {
			val voucherId = try {
				call.parameters["voucherId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}

			//parameter uid
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			call.generalSuccess { repository.claimVoucher(uid!!, voucherId!!) }
		}
	}
	
	private fun Route.getDetailVoucher() {
		get<VoucherRouteLocation.VoucherGetDetailRoute> {
			val voucherId = try {
				call.parameters["voucherId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalSuccess { repository.getDetailVoucher(voucherId!!) }
		}
	}
	
	private fun Route.useVoucher() {
		put<VoucherRouteLocation.VoucherUseRoute> {
			val voucherId = try {
				call.parameters["voucherId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			} ?: ""
			
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			} ?: ""
			
			call.generalSuccess { repository.updateUsedVoucher(uid, voucherId) }
		}
	}
	
	private fun Route.postVoucher() {
		post<VoucherRouteLocation.VoucherPostRoute> {
			val body = try {
				call.receive<VoucherBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewVoucher(body) }
		}
	}
	
	fun initVoucherRoute(route: Route) {
		route.apply {
			getAvailableVoucher()
			getOwnVoucherByUser()
			claimVoucher()
			getDetailVoucher()
			useVoucher()
			postVoucher()
		}
	}
}