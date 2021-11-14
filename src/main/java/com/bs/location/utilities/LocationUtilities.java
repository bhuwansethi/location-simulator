package com.bs.location.utilities;

import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

@Service
public class LocationUtilities {

	//private static final double R = 6378.1;
	private static final double earthRadius = 6371000; // meters
	
	public double distanceBetweenPoints(double lat1, double lng1, double lat2, double lng2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}
	
	public double[] getDestinationLatLong(double lat, double lng, double bearing, double distance) {
		double brng = Math.toRadians(bearing);
		double d = distance;
		double lat1 = Math.toRadians(lat);
		double lon1 = Math.toRadians(lng);
		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / earthRadius) + Math.cos(lat1) * Math.sin(d / earthRadius) * Math.cos(brng));
		double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / earthRadius) * Math.cos(lat1),
				Math.cos(d / earthRadius) - Math.sin(lat1) * Math.sin(lat2));
		double[] arr = new double[2];
		arr[0] = DoubleRounder.round(Math.toDegrees(lat2), 5);
		arr[1] = DoubleRounder.round(Math.toDegrees(lon2), 5);
		return arr;
	}
	
	public double calculateBearing(double lat1, double lng1, double lat2, double lng2) {
		double startLat = Math.toRadians(lat1);
		double startLong = Math.toRadians(lng1);
		double endLat = Math.toRadians(lat2);
		double endLong = Math.toRadians(lng2);
		double dLong = endLong - startLong;
		double dPhi = Math.log(Math.tan(endLat / 2.0 + Math.PI / 4.0) / Math.tan(startLat / 2.0 + Math.PI / 4.0));
		if (Math.abs(dLong) > Math.PI) {
			if (dLong > 0.0) {
				dLong = -(2.0 * Math.PI - dLong);
			} else {
				dLong = (2.0 * Math.PI + dLong);
			}
		}
		return (Math.toDegrees(Math.atan2(dLong, dPhi)) + 360.0) % 360.0;
	}
}
