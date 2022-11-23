package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object ChallengeMenuTable: Table() {
	
	override val tableName: String = "challenge_menu"
	
	val challengeId = reference("challenge_id", ChallengeTable.challengeId)
	val menuId = reference("menu_id", MenuTable.menuId)
	//breakfast, lunch, etc.
	val type = varchar("type", 12)
}