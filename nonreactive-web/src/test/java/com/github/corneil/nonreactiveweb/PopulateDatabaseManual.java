package com.github.corneil.nonreactiveweb;


import com.github.corneil.model.LocationHistory;
import com.github.corneil.nonreactiveweb.repository.LocationHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PopulateDatabaseManual {
	@Autowired
	LocationHistoryRepository locationHistoryRepository;

	Random random = new Random(System.currentTimeMillis());

	@Test
	public void createEntries() {
		LocalDateTime now = LocalDateTime.now();
		double lastX = (random.nextGaussian() * 360.0) - 180.0;
		double lastY = (random.nextGaussian() * 180.0) - 90.0;
		LocalDateTime startTime = now.minusDays(random.nextInt(30));
		final int entries = 10000;
		List<LocationHistory> items = new ArrayList<>();
		for (int i = 0; i < entries; i++) {

			if (random.nextBoolean()) {
				lastX += random.nextGaussian();
			} else {
				lastX -= random.nextGaussian();
			}
			if (random.nextBoolean()) {
				lastY += random.nextGaussian();
			} else {
				lastY -= random.nextGaussian();
			}
			if (lastX >= 180.0 || lastX <= -180.0) {
				lastX = (lastX % 180.0) * -1.0;
			}
			if (lastY >= 90.0 || lastY <= -90.0) {
				lastY = (lastY % 90.0) * -1.0;
			}
			log.info("X={},Y={}", lastX, lastY);
			Date timestamp = startTime.plusMinutes(i).toDate();
			if (timestamp.after(new Date())) {
				log.error("timestamp:{}", timestamp);
			}
			LocationHistory history = new LocationHistory(
				UUID.randomUUID().toString(),
				timestamp,
				new GeoJsonPoint(lastX, lastY));
			items.add(history);
			if (items.size() > 100) {
				locationHistoryRepository.saveAll(items);
				items.clear();
			}
		}
		if (!items.isEmpty()) {
			locationHistoryRepository.saveAll(items);
		}
		System.out.println(String.format("Database contains %d entries", locationHistoryRepository.count()));
	}
}
