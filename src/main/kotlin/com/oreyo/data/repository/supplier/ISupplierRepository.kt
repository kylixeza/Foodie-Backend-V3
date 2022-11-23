package com.oreyo.data.repository.supplier

import com.oreyo.data.repository.IAdminRepository
import com.oreyo.model.history.HistoryResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.review.ReviewResponse

interface ISupplierRepository: IAdminRepository {
    suspend fun getCurrentOrder(): List<HistoryResponse> //clear
    suspend fun updateCancelOrder(transactionId: String) //clear
    suspend fun updateMenuAvailability(menuId: String) //clear
    suspend fun getAllMenus(): List<MenuResponse> //clear
    suspend fun getMenuReviews(menuId: String): List<ReviewResponse> //clear
}