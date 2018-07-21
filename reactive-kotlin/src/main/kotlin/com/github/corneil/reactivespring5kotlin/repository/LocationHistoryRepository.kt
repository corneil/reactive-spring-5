package com.github.corneil.reactivespring5kotlin.repository

import com.github.corneil.reactivespring5kotlin.model.LocationHistory
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.util.*

interface LocationHistoryRepository : ReactiveCrudRepository<LocationHistory, String> {
	fun findByTimestampBetween(startDate: Date, endDate: Date): Flux<LocationHistory>
}
