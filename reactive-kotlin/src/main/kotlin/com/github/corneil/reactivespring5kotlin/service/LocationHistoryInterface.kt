package com.github.corneil.reactivespring5kotlin.service

import com.github.corneil.reactivespring5kotlin.model.ExtendedLocationHistory
import com.github.corneil.reactivespring5kotlin.model.LocationHistory
import reactor.core.publisher.Flux

import java.util.Date

interface LocationHistoryInterface {
    fun findByDates(startDate: Date, endDate: Date): Flux<LocationHistory>
    fun findAndConvert(startDate: Date, endDate: Date): Flux<ExtendedLocationHistory>
}
