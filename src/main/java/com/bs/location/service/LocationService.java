package com.bs.location.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

@Service
public class LocationService {

	private final String API_KEY = "AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos";

	public List<String> getLatLong(String origin, String destination, int interval) {
		List<String> list = new ArrayList<>();
		DirectionsResult directions = getDirections(origin, destination);
		DirectionsRoute[] routes = directions.routes;
		double lastLat = 0;
		double lastLan = 0;
		for (DirectionsRoute route : routes) {
			DirectionsLeg[] legs = route.legs;
			for (DirectionsLeg leg : legs) {
				lastLat = leg.startLocation.lat;
				lastLan = leg.startLocation.lng;
				list.add(lastLat + "," + lastLan);
				DirectionsStep[] steps = leg.steps;
				for (DirectionsStep step : steps) {
					EncodedPolyline polyLine = step.polyline;
					List<LatLng> listLatLng = polyLine.decodePath();
					for (LatLng cordinate : listLatLng) {
						if (distanceBetweenPoints(lastLat, lastLan, cordinate.lat, cordinate.lng) >= interval) {
							lastLat = cordinate.lat;
							lastLan = cordinate.lng;
							list.add(lastLat + "," + lastLan);
						}
					}
				}
			}
		}
		return list;
	}

	public DirectionsResult getDirections(String origin, String destination) {
		DirectionsResult result = null;

		GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();
		DirectionsApiRequest request = DirectionsApi.newRequest(context);
		request.origin(origin);
		request.destination(destination);
		request.mode(TravelMode.DRIVING);
		try {
			result = DirectionsApi.getDirections(context, origin, destination).await();
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public double distanceBetweenPoints(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}
}
