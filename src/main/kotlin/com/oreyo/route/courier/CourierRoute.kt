package com.oreyo.route.courier

import com.oreyo.data.repository.courier.ICourierRepository
import com.oreyo.model.admin.AdminBody
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
class CourierRoute(
    private val courierRepository: ICourierRepository
) {
    //create functions based on the CourierRouteLocation
    private fun Route.addCourier() {
        post<CourierRouteLocation.CourierAddRoute> {
            val body = try {
                call.receive<AdminBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }
            call.generalSuccess { courierRepository.addAdmin(body) }
        }
    }

    private fun Route.getCourierDetail() {
        get<CourierRouteLocation.CourierGetDetailRoute> {
            val adminId = try {
                call.parameters["adminId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            } ?: ""
            call.generalSuccess { courierRepository.getDetailAdmin(adminId) }
        }
    }

    private fun Route.getCurrentOrder() {
        get<CourierRouteLocation.OrderGetCurrentRoute> {
            call.generalListSuccess { courierRepository.getCurrentOrder() }
        }
    }

    private fun Route.updateCancelOder() {
        put<CourierRouteLocation.OrderUpdateCancelRoute> {
            val orderId = try {
                call.parameters["transactionId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            } ?: ""
            call.generalSuccess { courierRepository.updateCancelOrder(orderId) }
        }
    }

    private fun Route.updateSuccessOrder() {
        put<CourierRouteLocation.OrderUpdateSuccessRoute> {
            val orderId = try {
                call.parameters["transactionId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            } ?: ""
            call.generalSuccess { courierRepository.updateSuccessOrder(orderId) }
        }
    }

    private fun Route.getAllMenus() {
        get<CourierRouteLocation.MenuGetAllRoute> {
            call.generalListSuccess { courierRepository.getAllMenus() }
        }
    }

    private fun Route.getMenuReviews() {
        get<CourierRouteLocation.MenuGetReviewsRoute> {
            val menuId = try {
                call.parameters["menuId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            } ?: ""
            call.generalListSuccess { courierRepository.getMenuReviews(menuId) }
        }
    }

    fun Route.initCourierRoute() {
        addCourier()
        getCourierDetail()
        getCurrentOrder()
        updateCancelOder()
        updateSuccessOrder()
        getAllMenus()
        getMenuReviews()
    }
}