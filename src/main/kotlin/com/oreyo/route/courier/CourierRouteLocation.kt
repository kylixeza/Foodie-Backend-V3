package com.oreyo.route.courier

import io.ktor.server.locations.*

@KtorExperimentalLocationsAPI
sealed class CourierRouteLocation {
    companion object {
        const val POST_COURIER = "/courier"
        const val SELECTED_COURIER = "/courier/{adminId}"
        const val GET_CURRENT_ORDER = "/courier/order"
        const val UPDATE_CANCEL_ORDER = "/courier/order/{transactionId}/cancel"
        const val UPDATE_SUCCESS_ORDER = "/courier/order/{transactionId}/success"
        const val GET_MENUS = "/courier/menu"
        const val GET_MENU_REVIEWS = "/courier/menu/{menuId}/review"
    }

    @Location(POST_COURIER)
    class CourierAddRoute

    @Location(SELECTED_COURIER)
    data class CourierGetDetailRoute(val adminId: String)

    @Location(GET_CURRENT_ORDER)
    class OrderGetCurrentRoute

    @Location(UPDATE_CANCEL_ORDER)
    data class OrderUpdateCancelRoute(val transactionId: String)

    @Location(UPDATE_SUCCESS_ORDER)
    data class OrderUpdateSuccessRoute(val transactionId: String)

    @Location(GET_MENUS)
    class MenuGetAllRoute

    @Location(GET_MENU_REVIEWS)
    data class MenuGetReviewsRoute(val menuId: String)

}
