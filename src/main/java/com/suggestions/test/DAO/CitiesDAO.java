package com.suggestions.test.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.suggestions.test.entitys.Town;
import com.suggestions.test.services.CitiesMethods;
import com.suggestions.test.services.CitiesValidations;

public class CitiesDAO {

	CitiesValidations citiesValidations = null;
	CitiesMethods citiesMethods = null;
	ArrayList<Town> cities = null;

	boolean auxScoreMin = false;

	public ArrayList<Town> getCities(Optional<String> q, Optional<String> lat, Optional<String> lon) throws IOException {

		citiesValidations = new CitiesValidations();
		citiesMethods = new CitiesMethods();
		cities = new ArrayList<Town>();

		if (!citiesValidations.validateParameters(q, lat, lon))
			return cities;

		cities = citiesMethods.findSuggestionTown(q.get(), lat.get(), lon.get());

		return cities;
	}
}
