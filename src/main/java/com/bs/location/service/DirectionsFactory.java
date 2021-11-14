package com.bs.location.service;

import org.springframework.stereotype.Service;

@Service
public class DirectionsFactory {

	public DirectionsService getDirections(String service) {
		if(service.equalsIgnoreCase("GOOGLE")) {
			return new GoogleDirectionsService();
		}
		return null;
	}
}
