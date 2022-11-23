package com.oreyo.route.supplier

import com.oreyo.data.repository.supplier.ISupplierRepository
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
class SupplierRoute(
    private val repository: ISupplierRepository
) {

    private fun Route.addSupplier() {
        post<SupplierRouteLocation.SupplierAddRoute> {
            val body = try {
                call.receive<AdminBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }
            call.generalSuccess { repository.addAdmin(body) }
        }
    }

    private fun Route.getSupplierDetail() {
        get<SupplierRouteLocation.SupplierGetDetailRoute> {
            val adminId = try {
                call.parameters["adminId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            } ?: ""
            call.generalSuccess { repository.getDetailAdmin(adminId) }
        }
    }

    private fun Route.getCurrentOrder() {
        get<SupplierRouteLocation.OrderGetCurrentRoute> {
            call.generalListSuccess { repository.getCurrentOrder() }
        }
    }

    private fun Route.updateCancelOrder() {
        put<SupplierRouteLocation.OrderUpdateCancelRoute> {
            val transactionId = try {
                call.parameters["transactionId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            } ?: ""

            call.generalSuccess { repository.updateCancelOrder(transactionId) }
        }
    }

    private fun Route.updateMenuAvailability() {
        put<SupplierRouteLocation.MenuUpdateAvailabilityRoute> {
            val menuId = try {
                call.parameters["menuId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            } ?: ""

            call.generalSuccess { repository.updateMenuAvailability(menuId) }
        }
    }

    private fun Route.getAllMenus() {
        get<SupplierRouteLocation.MenuGetAllRoute> {
            call.generalListSuccess { repository.getAllMenus() }
        }
    }

    private fun Route.getMenuReviews() {
        get<SupplierRouteLocation.MenuGetReviewsRoute> {

            //parameter menuId
            val menuId = try {
                call.parameters["menuId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            } ?: ""

            call.generalListSuccess { repository.getMenuReviews(menuId) }
        }
    }

    fun Route.initSupplierRoute() {
        addSupplier()
        getSupplierDetail()
        getCurrentOrder()
        updateCancelOrder()
        updateMenuAvailability()
        getAllMenus()
        getMenuReviews()
    }

}