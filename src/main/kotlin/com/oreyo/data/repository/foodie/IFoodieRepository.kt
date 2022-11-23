package com.oreyo.data.repository.foodie

import com.oreyo.model.challenge.ChallengeBody
import com.oreyo.model.challenge.ChallengeMenuBody
import com.oreyo.model.challenge.ChallengeMenuResponse
import com.oreyo.model.challenge.ChallengeResponse
import com.oreyo.model.coupon.CouponBody
import com.oreyo.model.coupon.CouponResponse
import com.oreyo.model.history.*
import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.ingredient.IngredientResponse
import com.oreyo.model.menu.MenuBody
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.note.NoteBody
import com.oreyo.model.note.NoteResponse
import com.oreyo.model.review.ReviewResponse
import com.oreyo.model.step.StepBody
import com.oreyo.model.step.StepResponse
import com.oreyo.model.leaderboard.LeaderboardResponse
import com.oreyo.ml.prediction.CaloriePredictionBody
import com.oreyo.ml.prediction.CaloriePredictionResponse
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.user.UserBody
import com.oreyo.model.user.UserResponse
import com.oreyo.model.variant.VariantBody
import com.oreyo.model.variant.VariantResponse
import com.oreyo.model.voucher.VoucherBody
import com.oreyo.model.voucher.VoucherResponse
import com.oreyo.model.voucher_user.VoucherUserResponse

interface IFoodieRepository {
	suspend fun addUser(body: UserBody) //clear
	suspend fun getDetailUser(uid: String): UserResponse //clear
	suspend fun updateUser(uid: String, body: UserBody) //clear
	suspend fun getLeaderboard(): List<LeaderboardResponse>  //clear
	suspend fun getUserRank(uid: String): LeaderboardResponse //clear
	suspend fun isFavorite(uid: String, menuId: String): Boolean //clear
	suspend fun addFavorite(uid: String, menuId: String) //clear
	suspend fun deleteFavorite(uid: String, menuId: String) //tested
	suspend fun getAllFavoritesByUser(uid: String): List<MenuResponse> //clear
	suspend fun addNewCoupon(body: CouponBody) //clear
	suspend fun getAllCoupons(): List<CouponResponse> //clear
	suspend fun addNewMenu(body: MenuBody) //clear
	suspend fun getAllMenus(): List<MenuResponse> //clear (note: add default value at data class in front-end side)
	suspend fun getCategoryMenus(category: String): List<MenuResponse?> //clear
	suspend fun getDietMenus(): List<MenuResponse> //clear
	suspend fun getPopularMenus(): List<MenuResponse> //clear
	suspend fun getExclusiveMenus(): List<MenuResponse> //clear
	suspend fun getDetailMenu(menuId: String): MenuResponse //clear
	suspend fun updateMenuOrder(menuId: String) //clear
	suspend fun searchMenu(query: String): List<MenuResponse> //clear
	suspend fun addNewIngredient(menuId: String, body: IngredientBody) //clear
	suspend fun getIngredients(menuId: String): List<IngredientResponse> //clear
	suspend fun addNewStep(menuId: String, body: StepBody) //clear
	suspend fun getSteps(menuId: String): List<StepResponse> //clear
	suspend fun addNewReview(menuId: String, body: ReviewBody) //clear
	suspend fun getReviews(menuId: String): List<ReviewResponse> //clear
	suspend fun addNewVariant(menuId: String, body: VariantBody) //clear
	suspend fun getAllVariants(menuId: String): List<VariantResponse> //clear
	suspend fun getAllLastTransaction(uid: String): List<TransactionResponse> //clear
	suspend fun addNewHistory(uid: String, body: HistoryBody) //clear
	suspend fun updateHistoryStatus(body: HistoryUpdateBody) //clear
	suspend fun updateHistoryStarsGiven(uid: String, body: HistoryUpdateStarsGiven) //clear
	suspend fun getAllHistoryByUser(uid: String): List<HistoryResponse> //clear
	suspend fun addNewVoucher(body: VoucherBody) //clear
	suspend fun getAvailableVoucher(uid: String): List<VoucherResponse> //clear
	suspend fun claimVoucher(uid: String, voucherId: String) //clear
	suspend fun getVoucherUser(uid: String): List<VoucherUserResponse> //clear
	suspend fun getDetailVoucher(voucherId: String): VoucherResponse //clear
	suspend fun updateUsedVoucher(uid: String, voucherId: String)
	suspend fun getCaloriesPrediction(body: CaloriePredictionBody): CaloriePredictionResponse //clear
	suspend fun addNewNote(uid: String, body: NoteBody) //clear
	suspend fun getAllNoteByUser(uid: String): List<NoteResponse> //clear
	suspend fun addNewChallenge(body: ChallengeBody) //clear
	suspend fun joinChallenge(challengeId: String, uid: String) //clear
	suspend fun getAllAvailableChallenge(uid: String): List<ChallengeResponse?> //clear
	suspend fun getJoinedChallenges(uid: String): List<ChallengeResponse>
	suspend fun addNewChallengeMenu(body: ChallengeMenuBody) //clear
	suspend fun getDetailChallenge(challengeId: String): List<ChallengeMenuResponse?> //clear
}