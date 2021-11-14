package com.bs.location.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bs.location.utilities.LocationUtilities;
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

public class GoogleDirectionsService implements DirectionsService{

	private final String API_KEY = "API_KEY";
	
	@Override
	public List<String> getLatLong(String origin, String destination, int interval) {
		LocationUtilities utilities = new LocationUtilities();
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
						double distance = utilities.distanceBetweenPoints(lastLat, lastLan, cordinate.lat, cordinate.lng);
						System.out.println(lastLat + "," + lastLan + "\t" + cordinate.lat + "," + cordinate.lng + "\t" + distance);
						while (distance >= interval) {
							if (distance > interval && Math.round(distance) > interval) {
								double bearing = utilities.calculateBearing(lastLat, lastLan, cordinate.lat, cordinate.lng);
								double[] newPoint = utilities.getDestinationLatLong(lastLat, lastLan, bearing, distance-interval);
								lastLat = newPoint[0];
								lastLan = newPoint[1];
								list.add(lastLat + "," + lastLan + ",");
								System.out.println("WHILE:" + lastLat + "," + lastLan + "\t" + cordinate.lat + "," + cordinate.lng + "\t" + distance);
								distance = utilities.distanceBetweenPoints(lastLat, lastLan, cordinate.lat, cordinate.lng);
							} else {
								lastLat = cordinate.lat;
								lastLan = cordinate.lng;
								list.add(lastLat + "," + lastLan + ",");
								break;
							}
							
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
}
