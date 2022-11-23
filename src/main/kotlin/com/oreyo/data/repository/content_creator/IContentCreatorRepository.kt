package com.oreyo.data.repository.content_creator

import com.oreyo.data.repository.IAdminRepository
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.menu.VideoBody

interface IContentCreatorRepository: IAdminRepository {
    suspend fun getAllMenus(): List<MenuResponse>
    suspend fun getDetailMenu(menuId: String): MenuResponse
    suspend fun updateVideoUrl(menuId: String, body: VideoBody)
    suspend fun deleteVideo(menuId: String)
}