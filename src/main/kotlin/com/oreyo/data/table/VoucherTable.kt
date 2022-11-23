package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object VoucherTable: Table() {

    override val tableName: String = "voucher"

    val voucherId = varchar("voucher_id", 128)
    val background = varchar("background", 24)
    val xpCost = integer("xp_cost")
    val validUntil = varchar("valid_until", 24)
    val voucherCategory = varchar("voucher_category", 128)
    val voucherDiscount = integer("voucher_discount")

    override val primaryKey: PrimaryKey = PrimaryKey(voucherId)
}