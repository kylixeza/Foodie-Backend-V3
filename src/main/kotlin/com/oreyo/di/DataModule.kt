package com.oreyo.di

import com.oreyo.data.repository.foodie.FoodieRepository
import com.oreyo.data.repository.foodie.IFoodieRepository
import com.oreyo.data.repository.supplier.ISupplierRepository
import com.oreyo.data.repository.supplier.SupplierRepository
import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.repository.content_creator.ContentCreatorRepository
import com.oreyo.data.repository.content_creator.IContentCreatorRepository
import com.oreyo.data.repository.courier.CourierRepository
import com.oreyo.data.repository.courier.ICourierRepository
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.koin.dsl.module
import java.net.URI

val databaseModule = module {
	single {
		DatabaseFactory(get())
	}
	
	factory {
		val config = HikariConfig()
		config.apply {
			driverClassName = System.getenv("JDBC_DRIVER")
			maximumPoolSize = 6
			isAutoCommit = false
			transactionIsolation = "TRANSACTION_REPEATABLE_READ"
			
			jdbcUrl = if(System.getenv("ENV") == "DEV") {
				System.getenv("DATABASE_URL")
			} else {
				val uri = URI(System.getenv("DATABASE_URL"))
				val username = uri.userInfo.split(":").toTypedArray()[0]
				val password = uri.userInfo.split(":").toTypedArray()[1]
				val sslLocation = "/src/main/resources/prod-ca-2021.crt"
				if(System.getenv("HAS_SSL") == "true") {
					"jdbc:postgresql://${uri.host}:${uri.port}${uri.path}?sslmode=verify-ca&sslcert=$sslLocation&user=$username&password=$password"
				} else {
					"jdbc:postgresql://${uri.host}:${uri.port}${uri.path}?sslmode=require&user=$username&password=$password"
				}
			}
			
			validate()
		}
		HikariDataSource(config)
	}
}

val repositoryModule = module {
	single<IFoodieRepository> {
		FoodieRepository(get())
	}

	single<ISupplierRepository> {
		SupplierRepository(get())
	}

	single<ICourierRepository> {
		CourierRepository(get())
	}

	single<IContentCreatorRepository> {
		ContentCreatorRepository(get())
	}
}