package com.test.places.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.places.model.Places;
import com.test.places.repository.PlacesRepository;

@RestController
public class PlacesController {
	@Autowired
	private PlacesRepository placesRepository;

	@GetMapping("/findplaces")
	public List<Places> findAllPlaces() {
		return (List<Places>) placesRepository.findAll();
	}

	@GetMapping("/findplacesbyid/{monuments}")
	public Optional<Places> findPlacesById(@PathVariable String monuments) {

		return placesRepository.findById(monuments);
	}

	@PostMapping("/saveplace")
	public void savePlaces(@RequestBody Places newPlace) {
		placesRepository.save(newPlace);
	}

}
