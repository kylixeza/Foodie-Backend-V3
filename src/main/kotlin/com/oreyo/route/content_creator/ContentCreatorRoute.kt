package com.oreyo.route.content_creator

import com.oreyo.data.repository.content_creator.IContentCreatorRepository
import com.oreyo.model.admin.AdminBody
import com.oreyo.model.menu.VideoBody
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
class ContentCreatorRoute(
    private val repository: IContentCreatorRepository
) {

    private fun Route.addContentCreator() {
        post<ContentCreatorRouteLocation.ContentCreatorAddRoute> {
            val body = try {
                call.receive<AdminBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@post
            }
            call.generalSuccess { repository.addAdmin(body) }
        }
    }

    private fun Route.getContentCreatorDetail() {
        get<ContentCreatorRouteLocation.ContentCreatorGetDetailRoute> {
            val adminId = try {
                call.parameters["adminId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            } ?: ""
            call.generalSuccess { repository.getDetailAdmin(adminId) }
        }
    }

    private fun Route.getAllMenus() {
        get<ContentCreatorRouteLocation.MenuGetAllRoute> {
            call.generalListSuccess { repository.getAllMenus() }
        }
    }

    private fun Route.getDetailMenu() {
        get<ContentCreatorRouteLocation.MenuGetDetailRoute> {
            val menuId = try {
                call.parameters["menuId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@get
            } ?: ""
            call.generalSuccess { repository.getDetailMenu(menuId) }
        }
    }

    private fun Route.updateVideoUrl() {
        put<ContentCreatorRouteLocation.MenuUpdateVideoUrlRoute> {
            val menuId = try {
                call.parameters["menuId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            } ?: ""

            val body = try {
                call.receive<VideoBody>()
            } catch (e: Exception) {
                call.generalException(e)
                return@put
            }
            call.generalSuccess { repository.updateVideoUrl(menuId, body) }
        }
    }

    private fun Route.deleteVideo() {
        delete<ContentCreatorRouteLocation.MenuDeleteVideoRoute> {
            val menuId = try {
                call.parameters["menuId"]
            } catch (e: Exception) {
                call.generalException(e)
                return@delete
            } ?: ""
            call.generalSuccess { repository.deleteVideo(menuId) }
        }
    }

    fun Route.initContentCreatorRoute() {
        addContentCreator()
        getContentCreatorDetail()
        getAllMenus()
        getDetailMenu()
        updateVideoUrl()
        deleteVideo()
    }
}