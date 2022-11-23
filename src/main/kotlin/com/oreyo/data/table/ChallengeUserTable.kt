package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object ChallengeUserTable: Table() {
	
	override val tableName: String = "challenge_user"
	
	val challengeId = reference("challenge_id", ChallengeTable.challengeId)
	val uid = reference("uid", UserTable.uid)
}