package com.github.corneil.springfu

import com.github.corneil.model.LocationHistory
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class LocationRepository(private val template: ReactiveMongoTemplate) {

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
}
