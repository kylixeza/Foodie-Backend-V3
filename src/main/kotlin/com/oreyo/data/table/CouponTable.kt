package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object CouponTable: Table() {
	
	override val tableName: String = "coupon"
	
	val couponId = varchar("coupon_id", 128)
	val imageUrl = varchar("image_url", 256)
	
	override val primaryKey: PrimaryKey = PrimaryKey(couponId)
}