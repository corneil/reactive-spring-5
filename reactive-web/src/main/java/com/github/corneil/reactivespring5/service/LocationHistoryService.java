package com.github.corneil.reactivespring5.service;

import com.github.corneil.reactivespring5.model.ExtendedLocationHistory;
import com.github.corneil.reactivespring5.model.LocationHistory;
import com.github.corneil.reactivespring5.repository.LocationHistoryRepository;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service("locationHistory")
@XSlf4j
public class LocationHistoryService implements LocationHistoryInterface {
	private LocationHistoryRepository historyRepository;

	public LocationHistoryService(LocationHistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	@Override
	public Flux<LocationHistory> findByDates(Date startDate, Date endDate) {
		try {
			log.info(">>findByDates");
			return historyRepository.findByTimestampBetween(startDate, endDate);
		} finally {
			log.info("<<findByDates");
		}
	}

	@Override
	public Flux<ExtendedLocationHistory> findAndConvert(Date startDate, Date endDate) {
		try {
			log.info(">>findAndConvert");
			return historyRepository.findByTimestampBetween(startDate, endDate)
				.flatMap(item -> Mono.just(convertToExtended(item)));
		} finally {
			log.info("<<findAndConvert");
		}
	}

	private static ExtendedLocationHistory convertToExtended(LocationHistory item) {
		final double x = item.getLocation().getX();
		final double y = item.getLocation().getY();
		return new ExtendedLocationHistory(item.getId(),
			item.getTimestamp(),
			new GeoJsonPoint(y, x),
			String.format("Location %f, %f", x, y));
	}
}
