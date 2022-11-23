package com.oreyo.data.repository.courier

import com.oreyo.data.repository.IAdminRepository
import com.oreyo.model.history.HistoryResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.review.ReviewResponse

interface ICourierRepository: IAdminRepository {
    suspend fun getCurrentOrder(): List<HistoryResponse> //clear
    suspend fun updateCancelOrder(transactionId: String)
    suspend fun updateSuccessOrder(transactionId: String)
    suspend fun getAllMenus(): List<MenuResponse>
    suspend fun getMenuReviews(menuId: String): List<ReviewResponse>
}
