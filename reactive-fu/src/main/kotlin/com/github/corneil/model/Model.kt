package com.github.corneil.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import reactor.core.publisher.Flux
import java.util.*

@Document(collection = "location_history")
open class LocationHistory(
        @Id
        val id: String,
        @Indexed
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val timestamp: Date,
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
        val location: GeoJsonPoint)

class ExtendedLocationHistory : LocationHistory {
    private val description: String

    constructor(id: String, timestamp: Date, location: GeoJsonPoint, description: String) : super(id, timestamp, location) {
        this.description = description
    }
}

interface LocationHistoryInterface {
    fun findByDates(startDate: Date, endDate: Date): Flux<LocationHistory>

    fun findAndConvert(startDate: Date, endDate: Date): Flux<ExtendedLocationHistory>
}
