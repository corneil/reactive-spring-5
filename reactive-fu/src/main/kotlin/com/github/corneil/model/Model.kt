package com.github.corneil.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
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

fun convertToExtended(item: LocationHistory): ExtendedLocationHistory {
    val x = item.location.x
    val y = item.location.y
    return ExtendedLocationHistory(item.id,
            item.timestamp,
            GeoJsonPoint(y, x),
            String.format("Location %f, %f", x, y))
}
