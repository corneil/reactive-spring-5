package com.github.corneil.springfu

import com.github.corneil.model.ExtendedLocationHistory
import com.github.corneil.model.LocationHistory
import com.github.corneil.model.LocationHistoryInterface
import mu.KLogging
import reactor.core.publisher.Flux
import java.util.*


class LocationHistoryService(private val historyRepository: LocationHistoryRepository) : LocationHistoryInterface {

    companion object : KLogging()

    override fun findByDates(startDate: Date, endDate: Date): Flux<LocationHistory> {
        try {
            logger.info(">>findByDates")
            return historyRepository.findByDates(startDate, endDate)
        } finally {
            logger.info("<<findByDates")
        }
    }

    override fun findAndConvert(startDate: Date, endDate: Date): Flux<ExtendedLocationHistory> {
        try {
            logger.info(">>findAndConvert")
            return historyRepository
                    .findByDates(startDate, endDate)
                    .map { convertToExtended(it) }
        } finally {
            logger.info("<<findAndConvert")
        }
    }

}

fun convertToExtended(item: LocationHistory) =
        ExtendedLocationHistory(item.id, item.timestamp, item.location, String.format("Location %f, %f", item.location.x, item.location.y))
