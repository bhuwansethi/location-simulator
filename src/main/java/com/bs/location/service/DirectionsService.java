package com.bs.location.service;

import java.util.List;

public interface DirectionsService {

	public List<String> getLatLong(String origin, String destination, int interval);
	
}
