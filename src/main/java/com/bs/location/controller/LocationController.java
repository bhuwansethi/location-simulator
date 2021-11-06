package com.bs.location.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bs.location.service.LocationService;

@RestController
@RequestMapping(value = "/api")
public class LocationController {

	@Autowired
	LocationService service;
	
	@GetMapping("/location")
	public ResponseEntity<List<String>> getLatLong(@RequestParam(value = "origin") String origin, @RequestParam(value = "destination") String destination, @RequestParam(value = "interval", defaultValue = "50") int interval) {
		List<String> result = service.getLatLong(origin, destination, interval);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}	
}
