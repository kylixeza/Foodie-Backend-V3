package com.oreyo.data.repository.foodie

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.table.*
import com.oreyo.dataset.CaloriePredictionDataset
import com.oreyo.dataset.ChallengeClusteringDataset
import com.oreyo.model.challenge.ChallengeBody
import com.oreyo.model.challenge.ChallengeMenuBody
import com.oreyo.model.challenge.ChallengeMenuResponse
import com.oreyo.model.coupon.CouponBody
import com.oreyo.model.history.HistoryBody
import com.oreyo.model.history.HistoryUpdateBody
import com.oreyo.model.history.HistoryUpdateStarsGiven
import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.leaderboard.LeaderboardResponse
import com.oreyo.model.menu.MenuBody
import com.oreyo.model.note.NoteBody
import com.oreyo.ml.prediction.CaloriePredictionBody
import com.oreyo.ml.prediction.CaloriePredictionResponse
import com.oreyo.model.challenge.ChallengeResponse
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.step.StepBody
import com.oreyo.model.user.UserBody
import com.oreyo.model.variant.VariantBody
import com.oreyo.model.voucher.VoucherBody
import com.oreyo.util.Mapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.minus
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FoodieRepository(
	private val dbFactory: DatabaseFactory
): IFoodieRepository {
	
	override suspend fun addUser(body: UserBody) {
		//INSERT INTO user VALUES ('${body.uid}', '${body.address}', ...)
		dbFactory.dbQuery {
			UserTable.insert { table ->
				table[uid] = body.uid
				table[address] = body.address
				table[avatar] = body.avatar
				table[foodieWallet] = 0
				table[email] = body.email
				table[name] = body.name
				table[phoneNumber] = body.phoneNumber
				table[xp] = 0
			}
		}
	}
	
	override suspend fun getDetailUser(uid: String) = dbFactory.dbQuery {
		UserTable.select {
			UserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToUserResponse(it)
		}
	}.first()
	
	override suspend fun updateUser(uid: String, body: UserBody) {
		dbFactory.dbQuery {
			UserTable.update(
				where = {UserTable.uid.eq(uid)}
			) { table ->
				table[address] = body.address
				table[avatar] = body.avatar
				table[foodieWallet] = body.foodieWallet
				table[email] = body.email
				table[name] = body.name
				table[phoneNumber] = body.phoneNumber
				table[xp] = body.xp
			}
		}
	}
	
	override suspend fun getLeaderboard() = dbFactory.dbQuery {

		val leaderboard = UserTable.selectAll()
				.orderBy(UserTable.xp, SortOrder.DESC)
				.limit(10)
				.mapNotNull { Mapper.mapRowToUserResponse(it) }


		val leaderboardSize = leaderboard.size

		val list = mutableListOf<LeaderboardResponse>()

		for(rank in 0 until leaderboardSize) {
			val user = leaderboard[rank]
			list.add(
				LeaderboardResponse(
					user.uid,
					user.name,
					user.avatar,
					user.xp,
					rank + 1
				)
			)
		}
		return@dbQuery list
	}

	override suspend fun getUserRank(uid: String): LeaderboardResponse {
		val leaderboard = dbFactory.dbQuery {
			UserTable.selectAll()
				.orderBy(UserTable.xp, SortOrder.DESC)
				.mapNotNull { Mapper.mapRowToUserResponse(it) }
		}

		val selectedUser = dbFactory.dbQuery {
			UserTable.select { UserTable.uid.eq(uid) }.firstNotNullOf { Mapper.mapRowToUserResponse(it) }
		}

		val rank = leaderboard.indexOf(selectedUser) + 1

		return LeaderboardResponse(
			selectedUser.uid,
			selectedUser.name,
			selectedUser.avatar,
			selectedUser.xp,
			rank
		)
	}
	
	override suspend fun isFavorite(uid: String, menuId: String): Boolean = dbFactory.dbQuery {
		FavoriteTable.select {
			(FavoriteTable.uid.eq(uid) and FavoriteTable.menuId.eq(menuId))
		}.mapNotNull {
			Pair(it[FavoriteTable.uid], it[FavoriteTable.menuId])
		}.isNotEmpty()
	}
	
	override suspend fun addFavorite(uid: String, menuId: String) {
		dbFactory.dbQuery {
				FavoriteTable.insert { table ->
				table[FavoriteTable.uid] = uid
				table[FavoriteTable.menuId] = menuId
			}
		}
	}
	
	override suspend fun deleteFavorite(uid: String, menuId: String) {
		dbFactory.dbQuery {
			FavoriteTable.deleteWhere {
				FavoriteTable.uid.eq(uid) and FavoriteTable.menuId.eq(menuId)
			}
		}
	}
	
	override suspend fun getAllFavoritesByUser(uid: String) = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}.join(FavoriteTable, JoinType.INNER) {
			FavoriteTable.menuId.eq(MenuTable.menuId)
		}.slice(
			MenuTable.menuId,
			MenuTable.benefit,
			MenuTable.description,
			MenuTable.difficulty,
			MenuTable.calories,
			MenuTable.cookTime,
			MenuTable.estimatedTime,
			MenuTable.image,
			MenuTable.ordered,
			MenuTable.price,
			Avg(ReviewTable.rating, 1).alias("rating"),
			MenuTable.title,
			MenuTable.category,
			MenuTable.videoUrl,
			MenuTable.xpGained,
			MenuTable.isAvailable
		).select {
			FavoriteTable.uid.eq(uid)
		}.groupBy(MenuTable.menuId).mapNotNull {
			Mapper.mapRowToMenuResponse(it)
		}
	}
	
	override suspend fun addNewCoupon(body: CouponBody) {
		dbFactory.dbQuery {
			CouponTable.insert {
				it[couponId] = "COUPON${NanoIdUtils.randomNanoId()}"
				it[imageUrl] = body.imageUrl
			}
		}
	}
	
	override suspend fun getAllCoupons() = dbFactory.dbQuery {
		CouponTable.selectAll().mapNotNull { Mapper.mapRowToCouponResponse(it) }
	}
	
	override suspend fun addNewMenu(body: MenuBody) {
		dbFactory.dbQuery {
			MenuTable.insert { table ->
				table[menuId] = "MENU${NanoIdUtils.randomNanoId()}"
				table[benefit] = body.benefit
				table[category] = body.category
				table[description] = body.description
				table[difficulty] = body.difficulty
				table[calories] = body.calories
				table[cookTime] = body.cookTime
				table[estimatedTime] = body.estimatedTime
				table[image] = body.image
				table[ordered] = 0
				table[price] = body.price
				table[title] = body.title
				table[videoUrl] = body.videoUrl
				table[xpGained] = body.xpGained
			}
		}
	}
	
	override suspend fun getAllMenus() = dbFactory.dbQuery {
		
		getGeneralMenu()
			.selectAll()
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getCategoryMenus(category: String) = dbFactory.dbQuery {
		getGeneralMenu()
			.select {
				MenuTable.category.eq(category)
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getDietMenus() = dbFactory.dbQuery {
		getGeneralMenu()
			.select {
				MenuTable.category.eq("Vegetable")
			}
			.groupBy(MenuTable.menuId)
			.orderBy(MenuTable.calories, SortOrder.ASC)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getPopularMenus() = dbFactory.dbQuery {
		getGeneralMenu()
			.selectAll()
			.groupBy(MenuTable.menuId)
			.orderBy(MenuTable.ordered, SortOrder.DESC)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getExclusiveMenus() = dbFactory.dbQuery {
		getGeneralMenu()
			.select { MenuTable.isExclusive.eq(true) }
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}.shuffled()
	}
	
	override suspend fun getDetailMenu(menuId: String) = dbFactory.dbQuery {
		getGeneralMenu().select {
				MenuTable.menuId.eq(menuId)
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}.first()
	
	override suspend fun updateMenuOrder(menuId: String) {
		dbFactory.dbQuery {
			val totalOrderNow = MenuTable
				.select(MenuTable.menuId.eq(menuId))
				.firstNotNullOf { Mapper.mapMenuRowToInt(it) }
			
			MenuTable.update(
				where = {MenuTable.menuId.eq(menuId)}
			) { table ->
				table[ordered] = totalOrderNow.plus(1)
			}
		}
	}
	
	override suspend fun searchMenu(query: String) = dbFactory.dbQuery {
		getGeneralMenu().select {
				LowerCase(MenuTable.title).like("%$query%".lowercase(Locale.getDefault()))
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	private fun getGeneralMenu(): FieldSet {
		return MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl,
				MenuTable.xpGained,
				MenuTable.isAvailable
			)
	}
	
	override suspend fun addNewIngredient(menuId: String, body: IngredientBody) {
		dbFactory.dbQuery {
			IngredientTable.insert {
				it[IngredientTable.menuId] = menuId
				it[ingredient] = body.ingredient
			}
		}
	}
	
	override suspend fun getIngredients(menuId: String) = dbFactory.dbQuery {
		IngredientTable.select {
			IngredientTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToIngredientResponse(it) }
	}
	
	override suspend fun addNewStep(menuId: String, body: StepBody) {
		dbFactory.dbQuery {
			StepTable.insert {
				it[StepTable.menuId] = menuId
				it[step] = body.step
			}
		}
	}
	
	override suspend fun getSteps(menuId: String) = dbFactory.dbQuery {
		StepTable.select {
			StepTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToStepResponse(it) }
	}
	
	override suspend fun addNewReview(menuId: String, body: ReviewBody) {
		dbFactory.dbQuery {
			ReviewTable.insert {
				it[uid] = body.uid
				it[this.menuId] = menuId
				it[rating] = body.rating
			}
		}
	}
	
	override suspend fun getReviews(menuId: String) = dbFactory.dbQuery {
		ReviewTable.join(UserTable, JoinType.FULL)
			.select {
			ReviewTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToReviewResponse(it)
		}
	}
	
	override suspend fun addNewVariant(menuId: String, body: VariantBody) {
		dbFactory.dbQuery {
			VariantTable.insert {
				it[this.menuId] = menuId
				it[composition] = body.composition
				it[image] = body.image
				it[price] = body.price
				it[variant] = body.variant
			}
		}
	}
	
	override suspend fun getAllVariants(menuId: String) = dbFactory.dbQuery {
		VariantTable.select {
			VariantTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToVariantResponse(it)
		}
	}
	
	override suspend fun getAllLastTransaction(uid: String) = dbFactory.dbQuery {
		HistoryTable.join(VariantTable, JoinType.INNER) {
			HistoryTable.menuId.eq(VariantTable.menuId) and HistoryTable.variant.eq(VariantTable.variant)
		}.select {
			HistoryTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToTransactionResponse(it)
		}
	}
	
	override suspend fun addNewHistory(uid: String, body: HistoryBody) {
		dbFactory.dbQuery {
			val dateObj = Date()
			val df: DateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm")
			df.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
			val dateCreated = df.format(dateObj)
			
			HistoryTable.insert {
				it[UserTable.uid] = uid
				it[menuId] = body.menuId
				it[transactionId] = "TRANSACTION${NanoIdUtils.randomNanoId()}"
				it[timeStamp] = dateCreated
				it[variant] = body.variant
				it[status] = body.status
				it[starsGiven] = 0
			}
		}
	}
	
	override suspend fun updateHistoryStatus(body: HistoryUpdateBody) {
		dbFactory.dbQuery {
			HistoryTable.update(where = {HistoryTable.transactionId.eq(body.transactionId)}) {
				it[status] = body.status
			}
		}
	}
	
	override suspend fun updateHistoryStarsGiven(uid: String, body: HistoryUpdateStarsGiven) {
		dbFactory.dbQuery {
			HistoryTable.update(where = {HistoryTable.transactionId.eq(body.transactionId)}) {
				it[starsGiven] = body.starsGiven
			}
			
			ReviewTable.insert {
				it[ReviewTable.uid] = uid
				it[menuId] = body.menuId
				it[rating] = body.starsGiven.toDouble()
			}
		}
	}
	
	override suspend fun getAllHistoryByUser(uid: String) = dbFactory.dbQuery {
			HistoryTable.join(MenuTable, JoinType.INNER) {
				HistoryTable.menuId.eq(MenuTable.menuId)
			}.join(VariantTable, JoinType.INNER) {
				VariantTable.variant.eq(HistoryTable.variant)
			}.slice(
				HistoryTable.transactionId,
				MenuTable.menuId,
				MenuTable.image,
				MenuTable.title,
				HistoryTable.timeStamp,
				VariantTable.variant,
				HistoryTable.status,
				HistoryTable.starsGiven
			).select {
				HistoryTable.uid.eq(uid)
			}.mapNotNull {
				Mapper.mapRowToHistoryResponse(it)
			}
		}
	
	
	override suspend fun addNewVoucher(body: VoucherBody) {
		dbFactory.dbQuery {
			VoucherTable.insert {
				it[voucherId] = "VOUCHER${NanoIdUtils.randomNanoId()}"
				it[background] = body.background
				it[xpCost] = body.xpCost
				it[validUntil] = body.validUntil
				it[voucherCategory] = body.voucherCategory
				it[voucherDiscount] = body.voucherDiscount
			}
		}
	}
	
	override suspend fun getAvailableVoucher(uid: String) = dbFactory.dbQuery {
		VoucherTable.join(VoucherUserTable, JoinType.LEFT) {
			VoucherTable.voucherId.eq(VoucherUserTable.voucherId).and(VoucherUserTable.uid.eq(uid))
		}.select {
			VoucherUserTable.uid.isNull()
		}.map {
			Mapper.mapRowToVoucherResponse(it)
		}
	}

	override suspend fun claimVoucher(uid: String, voucherId: String) {
		dbFactory.dbQuery {
			VoucherUserTable.insert {
				it[this.uid] = uid
				it[this.voucherId] = voucherId
				it[this.isUsed] = false
			}
			val xp = VoucherTable
				.select { VoucherTable.voucherId.eq(voucherId) }.firstNotNullOf { it[VoucherTable.xpCost] }

			UserTable.update(where = {UserTable.uid.eq(uid)}) {
				it[this.xp] = this.xp.minus(xp)
			}
		}
	}
	
	override suspend fun getVoucherUser(uid: String) = dbFactory.dbQuery {
		VoucherUserTable.join(VoucherTable, JoinType.INNER) {
			VoucherUserTable.voucherId.eq(VoucherTable.voucherId)
		}.select {
			(VoucherUserTable.uid.eq(uid)) and (VoucherUserTable.isUsed.eq(false))
		}.mapNotNull {
			Mapper.mapRowToVoucherUserResponse(it)
		}
	}
	
	override suspend fun getDetailVoucher(voucherId: String) = dbFactory.dbQuery {
		VoucherTable.select {
			VoucherTable.voucherId.eq(voucherId)
		}.mapNotNull {
			Mapper.mapRowToVoucherResponse(it)
		}
	}.first()
	
	override suspend fun updateUsedVoucher(uid: String, voucherId: String): Unit = dbFactory.dbQuery {
		VoucherUserTable.update(
			where = { (VoucherUserTable.uid.eq(uid)) and (VoucherUserTable.voucherId.eq(voucherId)) }
		) {
			it[this.isUsed] = true
		}
	}
	
	override suspend fun getCaloriesPrediction(body: CaloriePredictionBody): CaloriePredictionResponse {
		val caloriePrediction = CaloriePredictionDataset()
		caloriePrediction.predict(listOf(body.food, body.category))
		return CaloriePredictionResponse(
			body.food,
			caloriePrediction.predictionResult.first,
			caloriePrediction.predictionResult.second
		)
	}
	
	override suspend fun addNewNote(uid: String, body: NoteBody) {
		dbFactory.dbQuery {
			NoteTable.insert { table ->
				table[NoteTable.uid] = uid
				table[noteId] = "NOTE${NanoIdUtils.randomNanoId()}"
				table[category] = body.category
				table[calories] = body.calories.times(body.portion)
				table[date] = body.date
				table[food] = body.food
				table[information] = body.information
				table[portion] = body.portion
				table[time] = body.time
			}
		}
	}
	
	override suspend fun getAllNoteByUser(uid: String) = dbFactory.dbQuery {
		NoteTable.select {
			NoteTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToNoteResponse(it)
		}
	}
	
	override suspend fun addNewChallenge(body: ChallengeBody) {
		dbFactory.dbQuery {
			ChallengeTable.insert {
				it[challengeId] = "CHALLENGE${NanoIdUtils.randomNanoId()}"
				it[title] = body.title
				it[description] = body.description
				it[participant] = 0
				it[xpEarned] = body.xpEarned
				it[calorieAverageRecommendation] = body.calorieAverageRecommendation
				it[caloriePerDayRecommendation] = body.caloriePerDayRecommendation
			}
		}
	}
	
	
	override suspend fun joinChallenge(challengeId: String, uid: String) {
		dbFactory.dbQuery {
			ChallengeUserTable.insert {
				it[this.challengeId] = challengeId
				it[this.uid] = uid
			}
			
			val xpEarned = ChallengeTable
				.select {
					ChallengeTable.challengeId.eq(challengeId)
				}.firstNotNullOf {
					it[ChallengeTable.xpEarned]
				}
			
			UserTable.update(where = {UserTable.uid.eq(uid)}) {
				it[this.xp] = this.xp.plus(xpEarned)
			}
			
			ChallengeTable.update(where = {ChallengeTable.challengeId.eq(challengeId)}) {
				it[this.participant] = this.participant.plus(1)
			}
		}
	}
	
	override suspend fun getAllAvailableChallenge(uid: String) = dbFactory.dbQuery {
		val participants = ChallengeUserTable.join(UserTable, JoinType.INNER) {
			ChallengeUserTable.uid.eq(UserTable.uid)
		}.slice(
			ChallengeUserTable.uid,
			ChallengeUserTable.challengeId,
			UserTable.avatar
		).selectAll().mapNotNull {
			Mapper.mapRowToParticipantResponse(it)
		}.groupBy {
			it.challengeId
		}
		
		val notes = NoteTable
			.select { NoteTable.uid.eq(uid) }
			.map { Triple(
				it[NoteTable.uid],
				it[NoteTable.calories],
				it[NoteTable.date]
			)}
		
		val fieldSet = ChallengeTable.join(ChallengeUserTable, JoinType.LEFT) {
			ChallengeTable.challengeId.eq(ChallengeUserTable.challengeId) and ChallengeUserTable.uid.eq(uid)
		}.slice(
			ChallengeTable.challengeId,
			ChallengeTable.title,
			ChallengeTable.description,
			ChallengeTable.participant,
			ChallengeTable.xpEarned,
			ChallengeUserTable.uid
		)
		
		if(notes.isEmpty()) {
			fieldSet.selectAll().map {
				Mapper.mapRowToChallengeResponse(it, participants)
			}
		} else {
			val challenges = ChallengeTable.selectAll().map {
				Triple(
					it[ChallengeTable.challengeId],
					it[ChallengeTable.calorieAverageRecommendation],
					it[ChallengeTable.caloriePerDayRecommendation]
				)
			}
			
			val clusteringDataset = ChallengeClusteringDataset()
			
			val dataset = clusteringDataset.createDataset(
				uid,
				challenges,
				notes
			)
			
			val model = clusteringDataset.createModel(dataset)
			
			val result = mutableListOf<ChallengeResponse?>()
			model.forEach { centroid ->
				if(centroid.points.any { it.uid != null }) {
					centroid.points.filter {
						it.challengeId != null
					}.forEach {
						fieldSet.select {
							ChallengeTable.challengeId.eq(it.challengeId!!)
						}.map {
							result.add(Mapper.mapRowToChallengeResponse(it, participants))
						}
					}
				}
			}
			result
		}
	}
	
	override suspend fun getJoinedChallenges(uid: String): List<ChallengeResponse> = dbFactory.dbQuery {
		val participants = ChallengeUserTable.join(UserTable, JoinType.INNER) {
			ChallengeUserTable.uid.eq(UserTable.uid)
		}.slice(
			ChallengeUserTable.uid,
			ChallengeUserTable.challengeId,
			UserTable.avatar
		).selectAll().mapNotNull {
			Mapper.mapRowToParticipantResponse(it)
		}.groupBy {
			it.challengeId
		}
		
		ChallengeTable.join(ChallengeUserTable, JoinType.LEFT) {
			ChallengeTable.challengeId.eq(ChallengeUserTable.challengeId) and ChallengeUserTable.uid.eq(uid)
		}.slice(
			ChallengeTable.challengeId,
			ChallengeTable.title,
			ChallengeTable.description,
			ChallengeTable.participant,
			ChallengeTable.xpEarned,
			ChallengeUserTable.uid
		).selectAll().mapNotNull {
			Mapper.mapRowToChallengeResponse(it, participants)
		}.filter { it.isJoined }
	}
	
	override suspend fun addNewChallengeMenu(body: ChallengeMenuBody) {
		dbFactory.dbQuery {
			ChallengeMenuTable.insert {
				it[challengeId] = body.challengeId
				it[menuId] = body.menuId
				it[type] = body.type
			}
		}
	}
	
	override suspend fun getDetailChallenge(challengeId: String) = dbFactory.dbQuery {
		
		val type = listOf("Breakfast", "Lunch", "Dinner")
		val result = mutableListOf<ChallengeMenuResponse>()
		
		val challengeTitle = ChallengeTable.select {
			ChallengeTable.challengeId.eq(challengeId)
		}.firstNotNullOf {
			it[ChallengeTable.title]
		}
		
		val menus = getGeneralMenu()
			.selectAll()
			.groupBy(MenuTable.menuId)
			.mapNotNull { Mapper.mapRowToMenuResponse(it) }
		
		val typedMenus = ChallengeMenuTable.join(MenuTable, JoinType.INNER) {
			ChallengeMenuTable.menuId.eq(MenuTable.menuId)
		}.select { ChallengeMenuTable.challengeId eq challengeId }.map {
			Pair(it[ChallengeMenuTable.type], it[MenuTable.menuId])
		}.groupBy { it.first }
		
		type.forEach {
			result.add(ChallengeMenuResponse(
				challengeId,
				challengeTitle,
				it,
				typedMenus[it]?.map { pair ->
					menus.first { menu -> menu.menuId == pair.second }
				}
			))
		}
		
		return@dbQuery result
	}
}