package com.github.corneil.reactivespring5.model;

import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Date;

@Data
public class ExtendedLocationHistory extends LocationHistory {
	private String description;

	public ExtendedLocationHistory(String id, Date timestamp, GeoJsonPoint location, String description) {
		super(id, timestamp, location);
		this.description = description;
	}

	public ExtendedLocationHistory() {
	}
}
