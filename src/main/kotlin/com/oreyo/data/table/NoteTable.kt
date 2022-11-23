package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object NoteTable: Table() {
	
	override val tableName: String = "note"
	
	val uid = reference("uid", UserTable.uid)
	val noteId = varchar("note_id", 128)
	val category = varchar("category", 48)
	val calories = double("calories")
	val date = varchar("date", 48)
	val food = varchar("food", 128)
	val information = varchar("information", 256)
	val portion = double("portion")
	val time = varchar("time", 24)
	
	override val primaryKey: PrimaryKey = PrimaryKey(noteId)
}