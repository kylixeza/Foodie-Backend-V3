package com.oreyo

import com.oreyo.di.databaseModule
import com.oreyo.di.repositoryModule
import com.oreyo.di.routeModule
import com.oreyo.plugins.configureRouting
import com.oreyo.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.netty.*
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

@KtorExperimentalLocationsAPI
fun main() {
	embeddedServer(
		Netty,
		port = System.getenv("PORT").toInt()) {
		install(Koin) {
			slf4jLogger(Level.ERROR)
			modules(listOf(databaseModule, repositoryModule, routeModule))
		}
		install(Locations)
		configureRouting()
		configureSerialization()
	}.start(wait = true)
}
