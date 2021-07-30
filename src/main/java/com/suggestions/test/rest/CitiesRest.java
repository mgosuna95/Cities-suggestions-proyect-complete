package com.suggestions.test.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.suggestions.test.DAO.CitiesDAO;
import com.suggestions.test.entitys.Town;

@RestController
@RequestMapping("Cities")
public class CitiesRest {

	@GetMapping("/suggestions")
	public static JSONPObject suggestions(@RequestParam Optional<String> q,
			@RequestParam Optional<String> latitude, @RequestParam Optional<String> longitude) throws IOException {

		CitiesDAO citiesDAO = new CitiesDAO();
		ArrayList<Town> cities = citiesDAO.getCities(q, latitude, longitude);
		JSONPObject jsonCities = new JSONPObject("suggestions", cities);
		
		return jsonCities;
	}
}
