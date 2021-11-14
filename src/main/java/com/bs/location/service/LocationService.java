package com.bs.location.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	@Value("${directions.service}")
	private String serviceType;
	
	public List<String> getLatLong(String origin, String destination, int interval) {
		DirectionsFactory factory = new DirectionsFactory();
		DirectionsService service = factory.getDirections(serviceType);
		return service.getLatLong(origin, destination, interval);
	}

}
