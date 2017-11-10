package com.github.corneil.nonreactiveweb.controller;

import com.github.corneil.nonreactiveweb.model.ExtendedLocationHistory;
import com.github.corneil.nonreactiveweb.model.LocationHistory;
import com.github.corneil.nonreactiveweb.service.LocationHistoryInterface;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@RestController
@XSlf4j
public class LocationHistoryController {
	private LocationHistoryInterface locationHistoryInterface;

	public LocationHistoryController(LocationHistoryInterface locationHistoryInterface) {
		this.locationHistoryInterface = locationHistoryInterface;
	}

	@GetMapping(path = "/last30days")
	public Collection<LocationHistory> findLast30Days() {
		try {
			log.info(">>findLast30Days");
			Date endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
			Date startDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant());
			return locationHistoryInterface.findByDates(startDate, endDate);
		} finally {
			log.info("<<findLast30Days");
		}
	}

	@GetMapping(path = "/extlast30days")
	public Collection<ExtendedLocationHistory> findExtendedLast30Days() {
		try {
			log.info(">>findExtendedLast30Days");

			Date endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
			Date startDate = Date.from(LocalDateTime.now().minusDays(30).atZone(ZoneId.systemDefault()).toInstant());
			return locationHistoryInterface.findAndConvert(startDate, endDate);
		} finally {
			log.info("<<findExtendedLast30Days");
		}
	}
}
