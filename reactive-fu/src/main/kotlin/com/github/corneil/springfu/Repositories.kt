package com.github.corneil.springfu

import com.github.corneil.model.LocationHistory
import mu.KLogging
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class LocationRepository(private val template: ReactiveMongoTemplate) {
    companion object : KLogging()

    fun count() = template.count<LocationHistory>()

    fun findAll() = template.findAll<LocationHistory>()

    fun findOne(id: String) = template.findById<LocationHistory>(id)

    fun deleteAll() = template.remove<LocationHistory>()

    fun save(item: LocationHistory) = template.save(item)

    fun findByDates(startDate: Date?, endDate: Date?): Flux<LocationHistory> {
        val query = Query()
        if (startDate != null && endDate != null) {
            query.addCriteria(Criteria.where("timestamp").gte(startDate).lte(endDate))
        } else if (endDate != null) {
            query.addCriteria(Criteria.where("timestamp").lte(endDate))
        } else if (startDate != null) {
            query.addCriteria(Criteria.where("timestamp").gte(startDate))
        }
        return template.find(query, LocationHistory::class.java)
    }

    fun load(loadCount: Int): Mono<String> {

        logger.info("Inserting records")
        val random = Random(System.currentTimeMillis())
        val now = LocalDateTime.now()
        var lastX = random.nextGaussian() * 360.0 - 180.0
        var lastY = random.nextGaussian() * 180.0 - 90.0
        val startTime = now.minusDays(random.nextLong() % 30L)
        return Flux.range(1, loadCount)
                .doOnComplete { logger.info { "Load completed" } }
                .map { i ->
                    if (random.nextBoolean()) {
                        lastX += random.nextGaussian()
                    } else {
                        lastX -= random.nextGaussian()
                    }
                    if (random.nextBoolean()) {
                        lastY += random.nextGaussian()
                    } else {
                        lastY -= random.nextGaussian()
                    }
                    if (lastX >= 180.0 || lastX <= -180.0) {
                        lastX = lastX % 180.0 * -1.0
                    }
                    if (lastY >= 90.0 || lastY <= -90.0) {
                        lastY = lastY % 90.0 * -1.0
                    }
                    val timestamp = Date.from(startTime.plusMinutes(i.toLong()).toInstant(ZoneOffset.UTC))
                    val locationHistory = LocationHistory(UUID.randomUUID().toString(), timestamp, GeoJsonPoint(lastX, lastY))
                    save(locationHistory).subscribe()
                }.collectList().map { "Loaded:${it.size}" }

    }
}
