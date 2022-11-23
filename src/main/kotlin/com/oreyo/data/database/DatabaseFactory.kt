package com.oreyo.data.database

import com.oreyo.data.table.*
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(
	private val dataSource: HikariDataSource
) {
	
	init {
		Database.connect(dataSource)
		transaction {
			val tables = listOf(
				FavoriteTable, IngredientTable, MenuTable, CouponTable, ReviewTable, StepTable,
				HistoryTable, UserTable, VariantTable, VoucherTable, VoucherUserTable, NoteTable,
				ChallengeTable, ChallengeUserTable, ChallengeMenuTable, AdminTable
			)
			tables.forEach { table ->
				SchemaUtils.create(table)
				SchemaUtils.createMissingTablesAndColumns(table)
			}
		}
	}
	
	suspend fun <T> dbQuery(block: () -> T): T =
		withContext(Dispatchers.IO) {
			transaction { block() }
		}
}