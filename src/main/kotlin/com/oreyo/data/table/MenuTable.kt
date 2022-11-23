package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object MenuTable: Table() {

    override val tableName: String = "menu"

    val menuId = varchar("menu_id", 128)
    val benefit = varchar("benefit", 1024)
    val category = varchar("type", 36)
    val description = varchar("description", 1024)
    val difficulty = varchar("difficulty", 24)
    val calories = integer("calories")
    val cookTime = integer("cook_time")
    val estimatedTime = varchar("estimated_time", 36)
    val image = varchar("image", 256)
    val ordered = integer("ordered")
    val price = integer("price")
    //val rating = double("rating")
    val title = varchar("title", 128)
    val videoUrl = varchar("video_url", 256)
    val xpGained = integer("xp").default(0)
    val isExclusive = bool("is_exclusive").default(false)
    val isAvailable = bool("is_available").default(false)

    override val primaryKey: PrimaryKey = PrimaryKey(menuId)
}