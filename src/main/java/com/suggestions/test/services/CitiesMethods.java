package com.suggestions.test.services;

import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import org.decimal4j.util.DoubleRounder;
import org.springframework.core.io.ClassPathResource;

import com.suggestions.test.entitys.Town;

public class CitiesMethods {

	CitiesValidations citiesValidations = null;
	String nameTownTSV = "", latTSV = "", lonTSV = "";
	Town townAux = null, evaluatedTown = null;
	ArrayList<Town> towns = null;
	String line = null;
	Double score = null;
	Boolean encontrado = false;

	// lee el archivo TSV para obtener los datos rqueridos para la busqueda
	public ArrayList<Town> findSuggestionTown(String q, String lat, String lon) throws IOException {

		citiesValidations = new CitiesValidations();
		evaluatedTown = new Town();
		towns = new ArrayList<Town>();

		InputStream resource = new ClassPathResource(
			      "cities-canada-usa.tsv").getInputStream();
		
		try (BufferedReader TSVReader = new BufferedReader(
				new InputStreamReader(resource))) { //"/src/main/resources/cities-canada-usa.tsv"

			TSVReader.readLine();
			while ((line = TSVReader.readLine()) != null) {
				String[] lineItems = line.split("\t");
				nameTownTSV = citiesValidations.getItemNameTown(lineItems);
				latTSV = lineItems[4];
				lonTSV = lineItems[5];

				evaluatedTown = getEvaluatedTown(nameTownTSV, q, lat, lon, latTSV, lonTSV);

				if (evaluatedTown != null) {
					towns.add(evaluatedTown);
					Collections.sort(towns);// ordenar de manera desendente
				}

				if (encontrado)
					break;
			}
		} catch (Exception e) {
			// System.out.println(e);
			return null;
		}
		return towns;
	}

	// pondera los 3 parametros de busqueda indicados y devulve una sugerencia
	// aceptable
	private Town getEvaluatedTown(String nameTown, String q, String lat, String lon, String lat2, String lon2) {

		Integer totalCad = q.length(), totalInsidencias = 0;
		char[] splitq = q.toLowerCase().toCharArray(), splitnameTown = nameTown.toLowerCase().toCharArray();
		;

		for (int i = 0; i < (totalCad <= nameTown.length() ? totalCad : nameTown.length()); i++) {
			if (splitq[i] == splitnameTown[i]) {
				totalInsidencias++;
			} else {
				break;
			}
		}

		if (totalInsidencias == 0)
			return null;
		if (nameTown.length() == totalInsidencias) {
			encontrado = true;
			//score = (double) 1;
			//return addTown();
		}

		Integer pon1 = totalInsidencias * 200 / totalCad;// promedio de caracteres correctos encontrados en q

		Double latitud = Double.parseDouble(lat);
		Double longitud = Double.parseDouble(lon);
		Double latitudTVS = Double.parseDouble(lat2);
		Double longitudTVS = Double.parseDouble(lon2);

		Double pon2 = (5
				- ((latitud >= latitudTVS ? latitud : latitudTVS) - (latitud <= latitudTVS ? latitud : latitudTVS)))
				* 10; // 10 - resta de lat * 10 = score en un rango del 0 al 100 donde 0 es el 100%
		Double pon3 = (5 - ((longitud >= longitudTVS ? longitud : longitudTVS)
				- (longitud <= longitudTVS ? longitud : longitudTVS))) * 10;

		score = ((pon1 + pon2 + pon3) * 100 / 300) / 100;// la busqueda por nombre tiene un 66.6% de prioridad, latitud
															// y longitud un 16.66% respectivamente dando el total de
															// 100%
		score = DoubleRounder.round(score, 1);

		if (score < 0)
			return null;

		return addTown();
	}

	public Town addTown() {
		townAux = new Town();
		townAux.setName(nameTownTSV);
		townAux.setLate(latTSV);
		townAux.setLon(lonTSV);
		townAux.setScore(score);

		return townAux;
	}

}
