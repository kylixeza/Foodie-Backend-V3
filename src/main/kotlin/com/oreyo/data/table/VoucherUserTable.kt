package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object VoucherUserTable: Table() {

    override val tableName: String = "voucher_user"

    val voucherId = reference("voucher_id", VoucherTable.voucherId)
    val uid = reference("uid", UserTable.uid)
    val isUsed = bool("is_used").default(false)
}