package com.oreyo.route.menu

import com.oreyo.data.repository.foodie.IFoodieRepository
import com.oreyo.model.coupon.CouponBody
import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.menu.MenuBody
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.step.StepBody
import com.oreyo.model.variant.VariantBody
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.locations.put
import io.ktor.server.request.*
import io.ktor.server.routing.*

@KtorExperimentalLocationsAPI
class MenuRoute(
	private val repository: IFoodieRepository
) {
	private fun Route.getAllMenus() {
		get<MenuRouteLocation.MenuGetListRoute> {
			val query = try {
				call.request.queryParameters["query"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			if (query != null)
				call.generalListSuccess { repository.searchMenu(query) }
			else
				call.generalListSuccess { repository.getAllMenus() }
		}
	}
	
	private fun Route.postMenu() {
		post<MenuRouteLocation.MenuPostRoute> {
			val body = try {
				call.receive<MenuBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewMenu(body) }
		}
	}
	
	private fun Route.getAllCoupons()  {
		get<MenuRouteLocation.CouponGetListRoute> {
			call.generalListSuccess { repository.getAllCoupons() }
		}
	}
	
	private fun Route.postCoupon() {
		post<MenuRouteLocation.CouponPostRoute> {
			val body = try {
				call.receive<CouponBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewCoupon(body) }
		}
	}
	
	private fun Route.getCategoryMenus() {
		get<MenuRouteLocation.CategoryGetListRoute> {
			val category = try {
				call.request.queryParameters["category"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			if (category != null)
				call.generalListSuccess { repository.getCategoryMenus(category) }
		}
	}
	
	private fun Route.getDietMenus() {
		get<MenuRouteLocation.DietGetListRoute> {
			call.generalListSuccess { repository.getDietMenus() }
		}
	}
	
	private fun Route.getPopularMenus() {
		get<MenuRouteLocation.PopularGetListRoute> {
			call.generalListSuccess { repository.getPopularMenus() }
		}
	}
	
	private fun Route.getExclusiveMenus() {
		get<MenuRouteLocation.ExclusiveGetListRoute> {
			call.generalListSuccess { repository.getExclusiveMenus() }
		}
	}
	
	private fun Route.getDetailMenu() {
		get<MenuRouteLocation.MenuGetDetailRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalSuccess { repository.getDetailMenu(menuId!!) }
		}
	}
	
	private fun Route.updateOrderMenu() {
		put<MenuRouteLocation.MenuUpdateOrderRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			call.generalSuccess { repository.updateMenuOrder(menuId!!) }
		}
	}
	
	private fun Route.getIngredients() {
		get<MenuRouteLocation.MenuGetIngredientListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getIngredients(menuId!!) }
		}
	}
	
	private fun Route.postIngredient() {
		post<MenuRouteLocation.MenuPostIngredientRoute> {
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			val body = try {
				call.receive<IngredientBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewIngredient(menuId!!, body) }
		}
	}
	
	private fun Route.getSteps() {
		get<MenuRouteLocation.MenuGetStepListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getSteps(menuId!!) }
		}
	}
	
	private fun Route.postStep() {
		post<MenuRouteLocation.MenuPostStepRoute> {
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			
			val body = try {
				call.receive<StepBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewStep(menuId!!, body) }
		}
	}
	
	private fun Route.postReview() {
		post<MenuRouteLocation.MenuPostReviewRoute> {
			val body = try {
				call.receive<ReviewBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			call.generalSuccess { repository.addNewReview(menuId!!, body) }
		}
	}
	
	private fun Route.getReviews() {
		get<MenuRouteLocation.MenuGetReviewListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getReviews(menuId!!) }
		}
	}
	
	private fun Route.getVariants() {
		get<MenuRouteLocation.MenuGetVariantListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAllVariants(menuId!!) }
		}
	}
	
	private fun Route.postVariant() {
		post<MenuRouteLocation.MenuPostVariantRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			val body = try {
				call.receive<VariantBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewVariant(menuId!!, body) }
		}
	}
	
	fun initMenuRoute(route: Route) {
		route.apply {
			getAllMenus()
			postMenu()
			getAllCoupons()
			postCoupon()
			getCategoryMenus()
			getDietMenus()
			getPopularMenus()
			getExclusiveMenus()
			getDetailMenu()
			updateOrderMenu()
			getIngredients()
			postIngredient()
			getSteps()
			postStep()
			postReview()
			getReviews()
			getVariants()
			postVariant()
		}
	}
}