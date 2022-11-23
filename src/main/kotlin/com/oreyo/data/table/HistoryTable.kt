package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object HistoryTable: Table() {
	
	override val tableName: String = "history"
	
	val uid = reference("uid", UserTable.uid)
	val menuId = reference("menu_id", MenuTable.menuId)
	val transactionId = varchar("transaction_id", 128)
	val timeStamp = varchar("time_stamp", 24)
	val variant = varchar("variant", 64)
	val status = varchar("status", 18)
	val starsGiven = integer("stars")
	
	override val primaryKey: PrimaryKey = PrimaryKey(transactionId)
}