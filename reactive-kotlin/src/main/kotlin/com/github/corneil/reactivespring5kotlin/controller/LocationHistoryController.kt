package com.github.corneil.reactivespring5kotlin.controller

import com.github.corneil.reactivespring5kotlin.model.ExtendedLocationHistory
import com.github.corneil.reactivespring5kotlin.model.LocationHistory
import com.github.corneil.reactivespring5kotlin.service.LocationHistoryInterface
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RestController
class LocationHistoryController(private val locationHistoryInterface: LocationHistoryInterface) {
	companion object : KLogging()

	@GetMapping(path = ["/last30days"])
	fun findLast30Days(): Flux<LocationHistory> {
		try {
			logger.info(">>findLast30Days")

			val endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
			val startDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant())
			return locationHistoryInterface.findByDates(startDate, endDate)
		} finally {
			logger.info("<<findLast30Days")
		}
	}

	@GetMapping(path = ["/extlast30days"])
	fun findExtendedLast30Days(): Flux<ExtendedLocationHistory> {
		try {
			logger.info(">>findExtendedLast30Days")

			val endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
			val startDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant())
			return locationHistoryInterface.findAndConvert(startDate, endDate)
		} finally {
			logger.info("<<findExtendedLast30Days")
		}
	}
}
