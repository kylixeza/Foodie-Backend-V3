package com.oreyo.data.repository

import com.oreyo.model.admin.AdminBody
import com.oreyo.model.admin.AdminResponse

interface IAdminRepository {
    suspend fun addAdmin(body: AdminBody) //clear
    suspend fun getDetailAdmin(adminId: String): AdminResponse
}