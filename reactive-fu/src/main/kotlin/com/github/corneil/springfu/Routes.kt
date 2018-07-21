package com.github.corneil.springfu

import org.springframework.web.reactive.function.server.router

fun routes(locHandler: LocationHandler) = router {
    GET("/last30days", locHandler::findLast30Days)
    GET("/extlast30days", locHandler::findExtendedLast30Days)
}
