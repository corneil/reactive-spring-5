package com.github.corneil.nonreactiveweb.service;

import com.github.corneil.model.LocationHistory;
import com.github.corneil.nonreactiveweb.model.ExtendedLocationHistory;

import java.util.Collection;
import java.util.Date;

public interface LocationHistoryInterface {
	Collection<LocationHistory> findByDates(Date startDate, Date endDate);

	Collection<ExtendedLocationHistory> findAndConvert(Date startDate, Date endDate);
}
