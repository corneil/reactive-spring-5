package com.github.corneil.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document(collection = "location_history")
public class LocationHistory {
	@Id
	private String id;
	@Indexed
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date timestamp;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location")
	private GeoJsonPoint location;

	public LocationHistory(String id, Date timestamp, GeoJsonPoint location) {
		this.id = id;
		this.timestamp = timestamp;
		this.location = location;
	}

	public LocationHistory() {
	}
}
