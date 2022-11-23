package com.oreyo.route.supplier

import io.ktor.server.locations.*

@KtorExperimentalLocationsAPI
sealed class SupplierRouteLocation {
    companion object {
        const val POST_SUPPLIER = "/supplier"
        const val SELECTED_SUPPLIER = "/supplier/{adminId}"
        const val GET_CURRENT_ORDER = "/supplier/order"
        const val UPDATE_CANCEL_ORDER = "/supplier/order/{transactionId}/cancel"
        const val UPDATE_MENU_AVAILABILITY = "/supplier/menu/{menuId}/availability"
        const val GET_ALL_MENUS = "/supplier/menu"
        const val GET_MENU_REVIEWS = "/supplier/menu/{menuId}/review"
    }

    @Location(POST_SUPPLIER)
    class SupplierAddRoute

    @Location(SELECTED_SUPPLIER)
    data class SupplierGetDetailRoute(val adminId: String)

    @Location(GET_CURRENT_ORDER)
    class OrderGetCurrentRoute

    @Location(UPDATE_CANCEL_ORDER)
    data class OrderUpdateCancelRoute(val transactionId: String)

    @Location(UPDATE_MENU_AVAILABILITY)
    data class MenuUpdateAvailabilityRoute(val menuId: String)

    @Location(GET_ALL_MENUS)
    class MenuGetAllRoute

    @Location(GET_MENU_REVIEWS)
    data class MenuGetReviewsRoute(val menuId: String)
}
