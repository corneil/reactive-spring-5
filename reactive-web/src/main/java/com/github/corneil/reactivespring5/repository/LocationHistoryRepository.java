package com.github.corneil.reactivespring5.repository;

import com.github.corneil.model.LocationHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Date;

public interface LocationHistoryRepository extends ReactiveCrudRepository<LocationHistory, String> {
	Flux<LocationHistory> findByTimestampBetween(Date startDate, Date endDate);
}
