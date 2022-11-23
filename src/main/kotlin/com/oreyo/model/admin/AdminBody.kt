package com.oreyo.model.admin

import com.google.gson.annotations.SerializedName

data class AdminBody(
    @field:SerializedName("admin_id")
    val adminId: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("avatar")
    val avatar: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,
)
