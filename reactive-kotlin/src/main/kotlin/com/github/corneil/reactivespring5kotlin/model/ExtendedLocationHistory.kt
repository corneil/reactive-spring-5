package com.github.corneil.reactivespring5kotlin.model

import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.util.*

class ExtendedLocationHistory : LocationHistory {
	private val description: String

	constructor(id: String, timestamp: Date, location: GeoJsonPoint, description: String) : super(id, timestamp, location) {
		this.description = description
	}
}
