package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object FavoriteTable: Table() {

    override val tableName: String = "favorite"

    val uid = reference("uid", UserTable.uid)
    val menuId = reference("menu_id", MenuTable.menuId)
}