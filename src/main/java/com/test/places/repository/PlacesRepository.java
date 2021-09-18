package com.test.places.repository;

import org.springframework.data.repository.CrudRepository;

import com.test.places.model.Places;

public interface PlacesRepository extends CrudRepository<Places, String> {

}
