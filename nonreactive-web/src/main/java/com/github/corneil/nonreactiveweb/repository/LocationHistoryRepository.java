package com.github.corneil.nonreactiveweb.repository;

import com.github.corneil.nonreactiveweb.model.LocationHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;

public interface LocationHistoryRepository extends CrudRepository<LocationHistory, String> {
	Collection<LocationHistory> findByTimestampBetween(Date startDate, Date endDate);
}
