package com.suggestions.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.suggestions.test.DAO.CitiesDAO;
import com.suggestions.test.entitys.Town;

@SpringBootTest
class CitiesSuggestionsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void deploySuggestions() throws IOException {
		
		Optional<String> q = Optional.ofNullable("London, ON, EE. UU.");
		Optional<String> latitude = Optional.ofNullable("42.98339");
		Optional<String> longitude = Optional.ofNullable("-81.23304");
		
		CitiesDAO citiesDAO = new CitiesDAO();
		ArrayList<Town> towns = citiesDAO.getCities(q, latitude, longitude);
		
		assertTrue(towns.get(0).getScore().equals(Double.parseDouble("1")));
	}
}
