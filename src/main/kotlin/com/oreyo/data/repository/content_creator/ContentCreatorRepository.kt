package com.oreyo.data.repository.content_creator

import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.table.AdminTable
import com.oreyo.data.table.MenuTable
import com.oreyo.data.table.ReviewTable
import com.oreyo.model.admin.AdminBody
import com.oreyo.model.admin.AdminResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.menu.VideoBody
import com.oreyo.util.Mapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ContentCreatorRepository(
    private val dbFactory: DatabaseFactory
): IContentCreatorRepository {
    override suspend fun addAdmin(body: AdminBody): Unit = dbFactory.dbQuery {
        AdminTable.insert {
            it[adminId] = body.adminId
            it[address] = body.address
            it[avatar] = body.avatar
            it[email] = body.email
            it[name] = body.name
            it[phoneNumber] = body.phoneNumber
            it[role] = "ContentCreator"
        }
    }

    override suspend fun getDetailAdmin(adminId: String): AdminResponse = dbFactory.dbQuery {
        AdminTable.select {
            AdminTable.adminId eq adminId
        }.mapNotNull {
            Mapper.mapRowToAdminResponse(it)
        }.single()
    }

    override suspend fun getAllMenus(): List<MenuResponse> = dbFactory.dbQuery {
        getGeneralMenu().selectAll()
            .groupBy(MenuTable.menuId)
            .mapNotNull {
                Mapper.mapRowToMenuResponse(it)
            }
    }

    override suspend fun getDetailMenu(menuId: String): MenuResponse = dbFactory.dbQuery {
        getGeneralMenu().select { MenuTable.menuId eq menuId }
            .groupBy(MenuTable.menuId)
            .mapNotNull {
                Mapper.mapRowToMenuResponse(it)
            }.single()
    }

    override suspend fun updateVideoUrl(menuId: String, body: VideoBody): Unit = dbFactory.dbQuery {
        MenuTable.update({ MenuTable.menuId eq menuId }) {
            it[videoUrl] = body.videoUrl
        }
    }

    override suspend fun deleteVideo(menuId: String): Unit = dbFactory.dbQuery {
        MenuTable.update({ MenuTable.menuId eq menuId }) {
            it[videoUrl] = ""
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