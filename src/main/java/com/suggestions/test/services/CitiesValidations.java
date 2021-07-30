package com.suggestions.test.services;

import java.util.Optional;

public class CitiesValidations {

	public boolean validateParameters(Optional<String> q, Optional<String> lat, Optional<String> lon) {
		
		if (!q.isPresent() || !lat.isPresent() | !lon.isPresent())
			return false;

		 //if(q.isEmpty() || lat.isEmpty() || lon.isEmpty())//cadenas vacias
		 //return false;

		String qAux2 = q.get().replaceAll("[^a-zA-Z0-9\\s\\,\\.]", "");// rempalzo caracteres espciales
		if (qAux2.length() != q.get().length())
			return false;

		if (lat.get().matches("[+]?\\d*(\\d+)?") || lon.get().matches("[+]?\\d*(\\d+)?"))// compruba que sea numeros
			return false;

		return true;
	}

	public String getItemNameTown(String[] lineItems) {

		return lineItems[1].toString() + ", " + (!Character.isDigit(lineItems[10].charAt(0)) ? lineItems[10] : "ON")
				+ ", " + (lineItems[8].toUpperCase() == "CA" ? "Canadá" : "EE. UU.");
	}
}
