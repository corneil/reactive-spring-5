package com.github.corneil.reactivespring5kotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document(collection = "location_history")
open class LocationHistory(
	@Id
	val id: String,
	@Indexed
	val timestamp: Date,
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
	val location: GeoJsonPoint)
