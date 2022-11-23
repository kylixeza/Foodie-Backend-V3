package com.oreyo.data.repository.courier

import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.table.*
import com.oreyo.model.admin.AdminBody
import com.oreyo.model.admin.AdminResponse
import com.oreyo.model.history.HistoryResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.review.ReviewResponse
import com.oreyo.util.Mapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CourierRepository(
    private val dbFactory: DatabaseFactory
): ICourierRepository {

    override suspend fun addAdmin(body: AdminBody): Unit = dbFactory.dbQuery {
        AdminTable.insert {
            it[adminId] = body.adminId
            it[address] = body.address
            it[avatar] = body.avatar
            it[email] = body.email
            it[name] = body.name
            it[phoneNumber] = body.phoneNumber
            it[role] = "Courier"
        }
    }

    override suspend fun getDetailAdmin(adminId: String): AdminResponse = dbFactory.dbQuery {
        AdminTable.select {
            AdminTable.adminId eq adminId
        }.mapNotNull {
            AdminResponse(
                adminId = it[AdminTable.adminId],
                address = it[AdminTable.address],
                avatar = it[AdminTable.avatar],
                email = it[AdminTable.email],
                name = it[AdminTable.name],
                phoneNumber = it[AdminTable.phoneNumber],
                role = it[AdminTable.role]
            )
        }.single()
    }

    override suspend fun getCurrentOrder(): List<HistoryResponse> = dbFactory.dbQuery {
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
            HistoryTable.status eq "On Process"
        }.mapNotNull {
            Mapper.mapRowToHistoryResponse(it)
        }
    }

    override suspend fun updateCancelOrder(transactionId: String): Unit = dbFactory.dbQuery {
        HistoryTable.update({ HistoryTable.transactionId eq transactionId }) {
            it[status] = "Cancel"
        }
    }

    override suspend fun updateSuccessOrder(transactionId: String): Unit = dbFactory.dbQuery {
        HistoryTable.update({ HistoryTable.transactionId eq transactionId }) {
            it[status] = "Success"
        }
    }

    override suspend fun getAllMenus(): List<MenuResponse> = dbFactory.dbQuery {
        val hasReviews = ReviewTable.selectAll().mapNotNull {
            it[ReviewTable.menuId]
        }.distinct()

        val result = mutableListOf<MenuResponse>()

        hasReviews.forEach {
            getGeneralMenu().select { MenuTable.menuId eq it }
                .groupBy(MenuTable.menuId)
                .mapNotNull {
                    Mapper.mapRowToMenuResponse(it)
                }.forEach {
                    result.add(it)
                }
        }
        result
    }

    override suspend fun getMenuReviews(menuId: String): List<ReviewResponse> = dbFactory.dbQuery {
        ReviewTable.join(UserTable, JoinType.FULL)
            .select {
                ReviewTable.menuId.eq(menuId)
            }.mapNotNull {
                Mapper.mapRowToReviewResponse(it)
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
}