package up.mi.skdh.frontend.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.control.Label;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.ChargingPointFoundException;
import up.mi.skdh.exceptions.ChargingPointNotFoundException;
import up.mi.skdh.exceptions.CityNotFoundException;

public class FileManager {
	
	public static UrbanCommunity loadUrbanCommunity(String filePath, Label result) {
		UrbanCommunity community = new UrbanCommunity();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
        	String line;
        	while((line = fileReader.readLine())!=null) {
        		processFileLine(line, community);
        	}
        	return community;
		}catch(IOException e) {
            result.setText("Une erreur s'est produit lors de la lecture du fichier : " + filePath);
            result.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
		return community;
    }
	
	private static void processFileLine(String line, UrbanCommunity community) {
		String lowerCaseLine = line.toLowerCase();//Convertit la ligne en minuscule pour pas avoir des problème de casse
		if(lowerCaseLine.startsWith("ville(")) {
			//Traitement pour ajouter une ville 
			String cityName = line.substring(6, line.length() - 1).replace(" ", "");//Extraire le nom de la ville
			City city = new City(cityName);
			try {
				city.removeChargingPoint();
			} catch (ChargingPointNotFoundException e) {}
			community.addCity(city);
		}else if(lowerCaseLine.startsWith("route(")) {
			String[] cities = line.substring(6, line.length() - 1).replace(" ", "").split(",");//Extraire les noms des villes
			if(cities.length == 2) {
				community.addRoad(cities[0], cities[1]);
			}
		}else if(lowerCaseLine.startsWith("recharge(")) {
			String cityName = line.substring(9, line.length() - 2);
			try {
				City city = community.findCity(cityName);
				city.addChargingPoint();
			}catch(CityNotFoundException | ChargingPointFoundException e) {}
		}
	}
	
	public static File saveCommunity(UrbanCommunity community, String filePath, Label result) {
		Set<String> visitedRoads = new HashSet<>(); // Pour stocker les routes sous la forme villeAvilleB
		File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Ecrire les villes
            for (City city : community.getCities()) {
                writer.write("ville(" + city.getName() + ")\n");
            }

            // Ecrire les routes
            for (City city : community.getCities()) {
                List<City> neighbors = city.getNeighbors();
                for (City neighbor : neighbors) {
                    String roadKey = city.getName() + neighbor.getName();
                    String reverseRoadKey = neighbor.getName() + city.getName();
                    if (!visitedRoads.contains(roadKey) && !visitedRoads.contains(reverseRoadKey)) {
                        writer.write("route(" + city.getName() + "," + neighbor.getName() + ")\n");
                        visitedRoads.add(roadKey);
                    }
                }
            }

            // Ecrires les villes avec une zone de recharge
            for (City city : community.getCities()) {
                if (city.hasChargingPoint()) {
                    writer.write("recharge(" + city.getName() + ")\n");
                }
            }
            System.out.println("Communauté sauvegardée avec succés!");
        } catch (IOException e) {
        	result.setText("Une erreur s'est produit lors de la création du fichier : " + filePath);
            result.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
		return file;
	}
}
