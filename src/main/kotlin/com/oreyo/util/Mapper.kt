package com.oreyo.util

import com.oreyo.data.table.*
import com.oreyo.model.challenge.ChallengeResponse
import com.oreyo.model.challenge.ParticipantResponse
import com.oreyo.model.coupon.CouponResponse
import com.oreyo.model.history.HistoryResponse
import com.oreyo.model.ingredient.IngredientResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.note.NoteResponse
import com.oreyo.model.review.ReviewResponse
import com.oreyo.model.step.StepResponse
import com.oreyo.model.history.TransactionResponse
import com.oreyo.ml.prediction.CaloriePredictionResponse
import com.oreyo.model.admin.AdminResponse
import com.oreyo.model.user.UserResponse
import com.oreyo.model.variant.VariantResponse
import com.oreyo.model.voucher.VoucherResponse
import com.oreyo.model.voucher_user.VoucherUserResponse
import org.jetbrains.exposed.sql.*

object Mapper {
	
	fun mapRowToUserResponse(row: ResultRow?): UserResponse? {
		if (row == null)
			return null
		
		return UserResponse(
			uid = row[UserTable.uid],
			address = row[UserTable.address],
			avatar = row[UserTable.avatar],
			foodieWallet = row[UserTable.foodieWallet],
			email = row[UserTable.email],
			name = row[UserTable.name],
			phoneNumber = row[UserTable.phoneNumber],
			xp = row[UserTable.xp]
		)
	}
	
	fun mapRowToMenuResponse(row: ResultRow?): MenuResponse? {
		if (row == null)
			return null
		
		return MenuResponse(
			menuId = row[MenuTable.menuId],
			benefit = row[MenuTable.benefit],
			description = row[MenuTable.description],
			difficulty = row[MenuTable.difficulty],
			calories = row[MenuTable.calories],
			cookTime = row[MenuTable.cookTime],
			estimatedTime = row[MenuTable.estimatedTime],
			image = row[MenuTable.image],
			ordered = row[MenuTable.ordered],
			price = row[MenuTable.price],
			rating = row[Avg(ReviewTable.rating, 1).alias("rating")],
			title = row[MenuTable.title],
			type = row[MenuTable.category],
			videoUrl = row[MenuTable.videoUrl],
			xpGained = row[MenuTable.xpGained],
			isAvailable = row[MenuTable.isAvailable]
		)
	}
	
	fun mapRowToIngredientResponse(row: ResultRow?): IngredientResponse? {
		if (row == null)
			return null
		
		return IngredientResponse(
			menuId = row[IngredientTable.menuId],
			ingredient = row[IngredientTable.ingredient]
		)
	}
	
	fun mapRowToStepResponse(row: ResultRow?): StepResponse? {
		if (row == null)
			return null
		
		return StepResponse(
			menuId = row[StepTable.menuId],
			step = row[StepTable.step]
		)
	}
	
	fun mapRowToReviewResponse(row: ResultRow?): ReviewResponse? {
		if (row == null)
			return null
		
		return ReviewResponse(
			menuId = row[ReviewTable.menuId],
			name = row[UserTable.name],
			avatar = row[UserTable.avatar],
			rating = row[ReviewTable.rating],
		)
	}
	
	fun mapRowToVariantResponse(row: ResultRow?): VariantResponse? {
		return if(row == null)
			null
		else {
			VariantResponse(
				menuId = row[VariantTable.menuId],
				composition = row[VariantTable.composition],
				image = row[VariantTable.image],
				price = row[VariantTable.price],
				variant = row[VariantTable.variant]
			)
		}
	}
	
	fun mapRowToTransactionResponse(row: ResultRow?): TransactionResponse? {
		if (row == null)
			return null
		
		return TransactionResponse(
			uid = row[HistoryTable.uid],
			transactionId = row[HistoryTable.transactionId],
			variant = row[VariantTable.variant],
			timeStamp = row[HistoryTable.timeStamp],
			price = row[VariantTable.price]
		)
	}
	
	fun mapRowToHistoryResponse(row: ResultRow?): HistoryResponse? {
		if (row == null) {
			return null
		}
		
		return HistoryResponse(
			transactionId = row[HistoryTable.transactionId],
			menuId = row[MenuTable.menuId],
			timeStamp = row[HistoryTable.timeStamp],
			title = row[MenuTable.title],
			image = row[MenuTable.image],
			variant = row[VariantTable.variant],
			status = row[HistoryTable.status],
			starsGiven = row[HistoryTable.starsGiven]
		)
	}
	
	fun mapRowToVoucherResponse(row: ResultRow): VoucherResponse {
		return VoucherResponse(
			voucherId = row[VoucherTable.voucherId],
			background = row[VoucherTable.background],
			coinCost = row[VoucherTable.xpCost],
			validUntil = row[VoucherTable.validUntil],
			voucherCategory = row[VoucherTable.voucherCategory],
			voucherDiscount = row[VoucherTable.voucherDiscount]
		)
	}
	
	fun mapRowToVoucherUserResponse(row: ResultRow): VoucherUserResponse {
		return VoucherUserResponse(
			voucherId = row[VoucherUserTable.voucherId],
			background = row[VoucherTable.background],
			coinCost = row[VoucherTable.xpCost],
			validUntil = row[VoucherTable.validUntil],
			voucherCategory = row[VoucherTable.voucherCategory],
			voucherDiscount = row[VoucherTable.voucherDiscount]
		)
	}
	
	fun mapMenuRowToInt(row: ResultRow): Int = row[MenuTable.ordered]
	
	fun mapRowToNoteResponse(row: ResultRow?): NoteResponse? {
		if (row == null)
			return null
		
		return NoteResponse(
			uid = row[NoteTable.uid],
			noteId = row[NoteTable.noteId],
			category = row[NoteTable.category],
			calories = row[NoteTable.calories],
			date = row[NoteTable.date],
			food = row[NoteTable.food],
			information = row[NoteTable.information],
			portion = row[NoteTable.portion],
			time = row[NoteTable.time]
		)
	}
	
	fun mapRowToCouponResponse(row: ResultRow?): CouponResponse? {
		if (row == null)
			return null
		
		return CouponResponse(
			couponId = row[CouponTable.couponId],
			imageUrl = row[CouponTable.imageUrl]
		)
	}
	
	fun mapRowToChallengeResponse(row: ResultRow?, participants: Map<String, List<ParticipantResponse>>): ChallengeResponse? {
		if (row == null)
			return null
		
		return ChallengeResponse(
			challengeId = row[ChallengeTable.challengeId],
			title = row[ChallengeTable.title],
			description = row[ChallengeTable.description],
			participants = participants[row[ChallengeTable.challengeId]],
			participantsCount = row[ChallengeTable.participant],
			xpEarned = row[ChallengeTable.xpEarned],
			isJoined = !row[ChallengeUserTable.uid].isNullOrEmpty()
		)
	}
	
	fun mapRowToParticipantResponse(row: ResultRow?): ParticipantResponse {
		return ParticipantResponse(
			uid = row?.get(ChallengeUserTable.uid).toString(),
			challengeId = row?.get(ChallengeUserTable.challengeId).toString(),
			avatar = row?.get(UserTable.avatar).toString()
		)
	}
	
	fun mapRowToAdminResponse(row: ResultRow?): AdminResponse? {
		if (row == null)
			return null

		return AdminResponse(
			adminId = row[AdminTable.adminId],
			address = row[AdminTable.address],
			email = row[AdminTable.email],
			name = row[AdminTable.name],
			avatar = row[AdminTable.avatar],
			phoneNumber = row[AdminTable.phoneNumber],
			role = row[AdminTable.role]
		)
	}
}