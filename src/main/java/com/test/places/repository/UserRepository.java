package com.test.places.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.places.model.DataAccessObjectUsers;

@Repository
public interface UserRepository extends CrudRepository<DataAccessObjectUsers, String> {

	DataAccessObjectUsers findByUsername(String username);

}
