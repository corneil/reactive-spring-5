package com.github.corneil.nonreactiveweb.service;

import com.github.corneil.model.LocationHistory;
import com.github.corneil.nonreactiveweb.model.ExtendedLocationHistory;
import com.github.corneil.nonreactiveweb.repository.LocationHistoryRepository;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service("locationHistory")
@XSlf4j
public class LocationHistoryService implements LocationHistoryInterface {
	private LocationHistoryRepository historyRepository;

	public LocationHistoryService(LocationHistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	@Override
	public Collection<LocationHistory> findByDates(Date startDate, Date endDate) {
		try {
			log.info(">>findByDates");
			return historyRepository.findByTimestampBetween(startDate, endDate);
		} finally {
			log.info("<<findByDates");
		}
	}

	@Override
	public Collection<ExtendedLocationHistory> findAndConvert(Date startDate, Date endDate) {
		try {
			log.info(">>findAndConvert");

			return historyRepository
				.findByTimestampBetween(startDate, endDate)
				.stream()
				.map(item -> convertToExtended(item))
				.collect(Collectors.toList());
		} finally {
			log.info("<<findAndConvert");
		}
	}

	private static ExtendedLocationHistory convertToExtended(LocationHistory item) {
		final double x = item.getLocation().getX();
		final double y = item.getLocation().getY();
		return new ExtendedLocationHistory(
			item.getId(),
			item.getTimestamp(),
			new GeoJsonPoint(y, x),
			String.format("Location %f, %f", x, y));
	}
}
