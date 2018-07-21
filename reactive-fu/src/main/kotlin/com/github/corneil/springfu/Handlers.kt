package com.github.corneil.springfu

import com.github.corneil.model.convertToExtended
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class LocationHandler(val locationRepositry: LocationRepository) {
    companion object : KLogging()
    fun load(request:ServerRequest): Mono<ServerResponse> {
        val count = request.queryParam("count").orElse("1000").toInt()
        logger.info { "load:$count" }
        return ServerResponse.ok().body(locationRepositry.load(count))
    }
    fun findLast30Days(request: ServerRequest): Mono<ServerResponse> {
        try {
            logger.info(">>findLast30Days")

            val now = LocalDateTime.now()
            val endDate = dateFrom(now)
            val startDate = dateFrom(now.minusDays(30))
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(locationRepositry.findByDates(startDate, endDate))
        } finally {
            logger.info("<<findLast30Days")
        }
    }


    fun findExtendedLast30Days(request: ServerRequest): Mono<ServerResponse> {
        try {
            logger.info(">>findExtendedLast30Days")

            val now = LocalDateTime.now()
            val endDate = dateFrom(now)
            val startDate = dateFrom(now.minusDays(30))
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(locationRepositry.findByDates(startDate, endDate).map { convertToExtended(it) })
        } finally {
            logger.info("<<findExtendedLast30Days")
        }
    }

    fun dateFrom(now: LocalDateTime): Date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
}
