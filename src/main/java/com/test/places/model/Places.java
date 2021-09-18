package com.test.places.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "places")
public class Places {

	@Id
	@Column(name = "monument")
	public String monuments;
	@Column(name = "location")
	public String location;
	@Column(name = "country")
	public String country;
	@Column(name = "year_")
	public Integer year;

	public Places() {
	}

	public Places(String monuments, String location, String country, Integer year) {
		super();
		this.monuments = monuments;
		this.location = location;
		this.country = country;
		this.year = year;
	}

	public String getMonuments() {
		return monuments;
	}

	public void setMonuments(String monuments) {
		this.monuments = monuments;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
