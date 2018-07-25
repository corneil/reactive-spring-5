package com.github.corneil.reactivespring5kotlin.controller

import com.github.corneil.reactivespring5kotlin.service.LocationHistoryInterface
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class LocationHistoryHandler(val locationHistoryInterface: LocationHistoryInterface) {
    companion object : KLogging()

    fun findLast30Days(request: ServerRequest): Mono<ServerResponse> {
        try {
            logger.info(">>findLast30Days")

            val endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
            val startDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant())
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(locationHistoryInterface.findByDates(startDate, endDate))
        } finally {
            logger.info("<<findLast30Days")
        }
    }

    fun findExtendedLast30Days(request: ServerRequest): Mono<ServerResponse> {
        try {
            logger.info(">>findExtendedLast30Days")

            val endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
            val startDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant())
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(locationHistoryInterface.findAndConvert(startDate, endDate))
        } finally {
            logger.info("<<findExtendedLast30Days")
        }
    }
}
