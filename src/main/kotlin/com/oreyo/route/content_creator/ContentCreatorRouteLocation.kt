package com.oreyo.route.content_creator

import io.ktor.server.locations.*

@KtorExperimentalLocationsAPI
sealed class ContentCreatorRouteLocation {
    companion object {
        const val POST_CONTENT_CREATOR = "/content_creator"
        const val SELECTED_CONTENT_CREATOR = "/content_creator/{adminId}"
        const val GET_ALL_MENUS = "/content_creator/menu"
        const val GET_DETAIL_MENU = "/content_creator/menu/{menuId}"
        const val UPDATE_VIDEO_URL = "/content_creator/menu/{menuId}/video"
        const val DELETE_VIDEO = "/content_creator/menu/{menuId}/video"
    }

    @Location(POST_CONTENT_CREATOR)
    class ContentCreatorAddRoute

    @Location(SELECTED_CONTENT_CREATOR)
    data class ContentCreatorGetDetailRoute(val adminId: String)

    @Location(GET_ALL_MENUS)
    class MenuGetAllRoute

    @Location(GET_DETAIL_MENU)
    data class MenuGetDetailRoute(val menuId: String)

    @Location(UPDATE_VIDEO_URL)
    data class MenuUpdateVideoUrlRoute(val menuId: String)

    @Location(DELETE_VIDEO)
    data class MenuDeleteVideoRoute(val menuId: String)

}
