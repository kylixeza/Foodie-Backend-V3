package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object ReviewTable: Table() {

    override val tableName: String = "review"

    val uid = reference("uid", UserTable.uid)
    val menuId = reference("menuId", MenuTable.menuId)
    val rating = double("rating")

}