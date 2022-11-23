package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    override val tableName: String = "user"

    val uid = varchar("uid", 128)
    val address = varchar("address", 256)
    val avatar = varchar("avatar", 512)
    val foodieWallet = integer("foodie_wallet")
    val email = varchar("email", 64)
    val name = varchar("name", 64)
    val phoneNumber = varchar("phone_number", 64)
    val xp = integer("xp")

    override val primaryKey: PrimaryKey = PrimaryKey(uid)
}