package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object AdminTable: Table() {

    override val tableName: String = "admin"

    val adminId = varchar("admin_id", 128)
    val address = varchar("address", 256)
    val avatar = varchar("avatar", 512)
    val email = varchar("email", 64)
    val name = varchar("name", 64)
    val phoneNumber = varchar("phone_number", 64)
    //role: Supplier, Courier, ContentCreator
    val role = varchar("role", 64)

}