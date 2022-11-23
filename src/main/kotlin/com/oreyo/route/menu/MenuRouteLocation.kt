package com.oreyo.route.menu

import io.ktor.server.locations.*

@KtorExperimentalLocationsAPI
sealed class MenuRouteLocation {
	companion object {
		//GET (Include query to search menu)
		const val MENU = "/menu"
		//POST
		const val POST_MENU = "/menu"
		//GET
		const val COUPON = "/coupon"
		//POST
		const val POST_COUPON = "/coupon"
		//Using query to get category
		//GET
		const val CATEGORY = "$MENU/category"
		//GET
		const val DIET = "$MENU/diet"
		//GET
		const val POPULAR = "$MENU/popular"
		//GET
		const val EXCLUSIVE = "$MENU/exclusive"
		//GET
		const val DETAIL_MENU = "$MENU/{menuId}"
		//UPDATE
		const val UPDATE_ORDER = "$MENU/{menuId}/order"
		//GET
		const val INGREDIENT = "$MENU/{menuId}/ingredient"
		//POST
		const val POST_INGREDIENT = "$MENU/{menuId}/ingredient"
		//GET
		const val STEP = "$MENU/{menuId}/step"
		//POST
		const val POST_STEP = "$MENU/{menuId}/step"
		//GET
		const val REVIEW = "$MENU/{menuId}/review"
		//POST
		const val POST_REVIEW = "$MENU/{menuId}/review"
		//GET
		const val VARIANT = "$MENU/{menuId}/variant"
		//POST
		const val POST_VARIANT = "$MENU/{menuId}/variant"
	}
	
	@Location(MENU)
	class MenuGetListRoute
	
	@Location(POST_MENU)
	class MenuPostRoute
	
	@Location(COUPON)
	class CouponGetListRoute
	
	@Location(POST_COUPON)
	class CouponPostRoute
	
	@Location(CATEGORY)
	class CategoryGetListRoute
	
	@Location(DIET)
	class DietGetListRoute
	
	@Location(POPULAR)
	class PopularGetListRoute
	
	@Location(EXCLUSIVE)
	class ExclusiveGetListRoute
	
	@Location(DETAIL_MENU)
	data class MenuGetDetailRoute(val menuId: String)
	
	@Location(UPDATE_ORDER)
	data class MenuUpdateOrderRoute(val menuId: String)
	
	@Location(INGREDIENT)
	data class MenuGetIngredientListRoute(val menuId: String)
	
	@Location(POST_INGREDIENT)
	data class MenuPostIngredientRoute(val menuId: String)
	
	@Location(STEP)
	data class MenuGetStepListRoute(val menuId: String)
	
	@Location(POST_STEP)
	data class MenuPostStepRoute(val menuId: String)
	
	@Location(REVIEW)
	data class MenuGetReviewListRoute(val menuId: String)
	
	@Location(POST_REVIEW)
	data class MenuPostReviewRoute(val menuId: String)
	
	@Location(VARIANT)
	data class MenuGetVariantListRoute(val menuId: String)
	
	@Location(POST_VARIANT)
	data class MenuPostVariantRoute(val menuId: String)
}
