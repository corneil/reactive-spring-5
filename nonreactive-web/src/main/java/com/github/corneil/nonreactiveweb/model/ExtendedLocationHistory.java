package com.github.corneil.nonreactiveweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExtendedLocationHistory extends LocationHistory {
	private String description;

	public ExtendedLocationHistory(String id, Date timestamp, GeoJsonPoint location, String description) {
		super(id, timestamp, location);
		this.description = description;
	}

	public ExtendedLocationHistory() {
	}
}
