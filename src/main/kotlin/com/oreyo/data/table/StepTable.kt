package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object StepTable: Table() {

    override val tableName: String = "step"

    val menuId = reference("menu_id", MenuTable.menuId)
    val step = varchar("step", 256)
}