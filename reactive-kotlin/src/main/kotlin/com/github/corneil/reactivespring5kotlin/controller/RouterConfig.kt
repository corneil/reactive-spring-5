package com.github.corneil.reactivespring5kotlin.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig {
    @Bean
    fun routerFunction(locationHistoryHandler: LocationHistoryHandler): RouterFunction<ServerResponse> = router {
        GET("/last30days", locationHistoryHandler::findLast30Days)
        GET("/extlast30days", locationHistoryHandler::findExtendedLast30Days)
    }
}
