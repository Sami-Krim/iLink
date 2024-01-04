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

/**
 * Classe de gestion des opérations sur les fichiers
 * Cette classe contient les méthodes de gestion de chargement et de stockage de la communauté urbaine d'un/dans un fichier.
 * 
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */
public class FileManager {
	
	/**
	 * Méthode statique pour charger une communauté urbaine d'un fichier
	 * 
	 * @param filePath Le chemin absolu du fichier représentant la communauté
	 * @param result Contient une information sur l'opération
	 * 
	 * @return La communauté urbaine chargée du fichier
	 */
	public static UrbanCommunity loadUrbanCommunity(String filePath, Label result) {
		UrbanCommunity community = new UrbanCommunity();
		boolean villeLineFound = false;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
        	String line;
        	while((line = fileReader.readLine())!=null) {
        		processFileLine(line, community);
        		if (line.toLowerCase().startsWith("ville(") && !villeLineFound) {
                    villeLineFound = true;
                }
        	}
        	if (!villeLineFound) { // Si aucune ville existe dans le fichier
                result.setText("Erreur : le fichier ne possède aucune ville : " + filePath);
                result.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
                return null;
            }
        	return community;
		}catch(IOException e) {
            result.setText("Une erreur s'est produit lors de la lecture du fichier : " + filePath);
            result.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
            return null;
        }
    }
	
	/**
	 * Méthode statique pour le traitement de chaque ligne du fichier lors du chargement
	 * 
	 * @param line la ligne courrante du fichier
	 * @param community La communauté a traité (ou effectuer l'opération)
	 */
	private static void processFileLine(String line, UrbanCommunity community) {
		String lowerCaseLine = line.toLowerCase();//Convertit la ligne en minuscule afin d'éviter les problème de casse
		if(lowerCaseLine.startsWith("ville(")) {
			//Traitement pour ajouter une ville 
			String cityName = line.substring(6, line.length() - 1).replace(" ", "");//Extraire le nom de la ville
			City city = new City(cityName);
			try {
				city.removeChargingPoint();
			} catch (ChargingPointNotFoundException e) {}
			community.addCity(city);
		}else if(lowerCaseLine.startsWith("route(")) {
			// Traitement pour ajouter une route entre deux villes
			String[] cities = line.substring(6, line.length() - 1).replace(" ", "").split(","); //Extraire les noms des villes
			if(cities.length == 2) {
				try {
					community.addRoad(cities[0], cities[1]);
				} catch (CityNotFoundException e) {}
			}
		}else if(lowerCaseLine.startsWith("recharge(")) {
			// Traitement pour ajouter une zone de recharge à une ville 
			String cityName = line.substring(9, line.length() - 1);
			try {
				City city = community.findCity(cityName);
				city.addChargingPoint();
			}catch(CityNotFoundException | ChargingPointFoundException e) {}
		}
	}
	
	/**
	 * Méthode statique pour sauvegarder la communauté dans un fichier texte
	 * Les villes sont stockées sous la forme : ville(X) 
	 * Les routes sont stockées sous la forme : route(X,Y)
	 * Les zones de recharge sont stockées sous la forme : recharge(X)
	 * Tel que : X et Y représentent des noms des villes.
	 * 
	 * @param community La communauté à sauvegarder (stocker)
	 * @param filePath Le chemin absolu vers le fichier résultat
	 * @param result Le label où retourner des informations sur le résultat
	 * 
	 * @return Le fichier résultat
	 */
	public static File saveCommunity(UrbanCommunity community, String filePath, Label result) {
		Set<String> visitedRoads = new HashSet<>(); // Pour stocker les routes sous la forme villeAvilleB
		File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Ecrire les villes
            for (City city : community.getCities()) {
                writer.write("ville(" + city.getName() + ")\n"); // Sous la forme ville(X)
            }

            // Ecrire les routes
            for (City city : community.getCities()) {
                List<City> neighbors = city.getNeighbors();
                for (City neighbor : neighbors) {
                    String roadKey = city.getName() + neighbor.getName();
                    String reverseRoadKey = neighbor.getName() + city.getName();
                    if (!visitedRoads.contains(roadKey) && !visitedRoads.contains(reverseRoadKey)) {
                        writer.write("route(" + city.getName() + "," + neighbor.getName() + ")\n");
                        visitedRoads.add(roadKey); // Sous la forme route(X,Y)
                    }
                }
            }

            // Ecrires les villes avec une zone de recharge
            for (City city : community.getCities()) {
                if (city.hasChargingPoint()) {
                    writer.write("recharge(" + city.getName() + ")\n"); // Sous la forme recharge(X)
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
