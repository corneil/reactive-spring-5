package com.github.corneil.reactivespring5kotlin.service

import com.github.corneil.reactivespring5kotlin.model.ExtendedLocationHistory
import com.github.corneil.reactivespring5kotlin.model.LocationHistory
import com.github.corneil.reactivespring5kotlin.repository.LocationHistoryRepository
import mu.KLogging
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service("locationHistory")
class LocationHistoryService(val historyRepository: LocationHistoryRepository) : LocationHistoryInterface {
	companion object : KLogging()

	override fun findByDates(startDate: Date, endDate: Date): Flux<LocationHistory> {
		try {
			logger.info(">>findByDates")
			return historyRepository.findByTimestampBetween(startDate, endDate)
		} finally {
			logger.info("<<findByDates")
		}
	}

	override fun findAndConvert(startDate: Date, endDate: Date): Flux<ExtendedLocationHistory> {
		try {
			logger.info(">>findAndConvert")
			return historyRepository.findByTimestampBetween(startDate, endDate).flatMap { item -> Mono.just(convertToExtended(item)) }
		} finally {
			logger.info("<<findAndConvert")
		}
	}

	private fun convertToExtended(item: LocationHistory): ExtendedLocationHistory {
		val x = item.location.x
		val y = item.location.y
		return ExtendedLocationHistory(item.id,
			item.timestamp,
			GeoJsonPoint(y, x),
			String.format("Location %f, %f", x, y))
	}
}
