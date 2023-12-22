package up.mi.skdh.frontend.managers;

import java.util.Random;

import javafx.scene.control.Label;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.ChargingPointFoundException;
import up.mi.skdh.exceptions.ChargingPointNotFoundException;
import up.mi.skdh.exceptions.CityNotFoundException;

/**
 * Classe de gestion de la communauté urbaine.
 * Cette classe contient les méthodes de gestion de la communauté qui lie l'interface graphique et le backend.
 * 
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */
public class CommunityManager {
	
	/**
	 * Méthode static pour choisir une ville aléatoirement.
	 * 
	 * @param community La communauté a traité
	 * 
	 * @return une ville aléatoire des ville de la communauté
	 */
	private static City getRandomCity(UrbanCommunity community) {
		Random random = new Random();
        int randomIndex = random.nextInt(community.getCities().size()); //Choisir un entier inférieur ou égal à la taille de la communauté urbaine
        return community.getCities().get(randomIndex);
    }
	
	/**
	 * Méthode statique pour claculer le score d'une communauté urbaine.
	 * Le calcul est basé sur le nombre de zone de recharge dans la communauté.
	 * 
	 * @param community La communauté a traité
	 * 
	 * @return un entier représentant le score de la communauté
	 */
	private static int calculateScore(UrbanCommunity community) {
	    return community.getNumberChargingPoints();
	}

	/**
	 * Méthode statique pour résoudre le problème automatiquement.
	 * L'algorithme utilisé est basé sur le principe des tests de score de la communauté après le changement de l'état de l'une des villes de la communauté.
	 * 
	 * @param community La communauté a traité
	 * @param k Représente le nombre d'itération de l'algorithme.
	 */
	public static void slightlyLessNaiveAlgorithm(UrbanCommunity community, int k) {
        int i = 0;
        int currentScore = calculateScore(community); // Calculer le score actuel de la solution
        while (i < k) {
            City randomCity = getRandomCity(community); // Obtenir une ville au hasard dans la communauté
            try {
	            if (randomCity.hasChargingPoint()) {
	                randomCity.removeChargingPoint();
	            } else {
	                randomCity.addChargingPoint();
	            }
	
	            int updatedScore = calculateScore(community);
	            if (updatedScore < currentScore) {
	                i = 0;
	                currentScore = updatedScore;
	            } else {
	                if (randomCity.hasChargingPoint()) {
	                    randomCity.removeChargingPoint();
	                } else {
	                    randomCity.addChargingPoint();
	                }
	            }
	            if (community.verifyAccessibilityConstraint(randomCity)) {
	                i++;
	            }
	        } catch (ChargingPointNotFoundException | ChargingPointFoundException | AccessibilityConstraintNotVerifiedException e) {}
        }
    }
	
	/**
	 * Méthode statique pour traiter la suppression d'une zone de recharge d'une ville
	 * 
	 * @param community La communauté a traité
	 * @param cityName Le nom de la ville pour laquelle on veut supprimer une zone de recharge
	 * @param state Le label où afficher le résultat
	 * 
	 * @throws CityNotFoundException 
	 * @throws ChargingPointNotFoundException
	 * @throws AccessibilityConstraintNotVerifiedException
	 */
	public static void removeChargingCity(UrbanCommunity community, String cityName, Label state) throws CityNotFoundException, ChargingPointNotFoundException, AccessibilityConstraintNotVerifiedException {
		City city = community.findCity(cityName); //Rechercher la ville
		city.removeChargingPoint(); //Retirer le point de chargement de la ville
		//Vérifier la contrainte d'accessibilité
		if (community.verifyAccessibilityConstraint(city)) { //Si elle est vérifiée
            state.setText("Borne de recharge retirée de la ville " + cityName);
    		state.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
        }
	}
	
	/**
	 * Méthode statique pour traiter l'ajout d'une zone de recharge à une ville
	 * 
	 * @param community La communauté a traité
	 * @param cityName Le nom de la ville pour laquelle on veut ajouter une zone de recharge
	 * @param state Le label où afficher le message d'information
	 * 
	 * @throws CityNotFoundException
	 * @throws ChargingPointFoundException
	 * @throws AccessibilityConstraintNotVerifiedException
	 */
	public static void addChargingCity(UrbanCommunity community, String cityName, Label state) throws CityNotFoundException, ChargingPointFoundException, AccessibilityConstraintNotVerifiedException {
		City city = community.findCity(cityName); //Rechercher la ville
		city.addChargingPoint(); //Ajouter le point de chargement à la ville
		state.setText("Borne de recharge ajoutée à la ville " + cityName);
		state.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
	}
	
	/**
	 * Méthode statique pour traiter l'ajout d'une route entre deux villes de la communauté
	 * 
	 * @param community La communauté a traité
	 * @param cityAName Le nom de la première ville
	 * @param cityBName Le nom de la deuxième ville
	 * @param state Le label où afficher le message d'information
	 */
	public static void addRoad(UrbanCommunity community, String cityAName, String cityBName, Label state){
		if (!community.hasRoad(cityAName, cityBName) && !community.hasRoad(cityBName, cityAName)) { //vérifier que la route n'existe pas déjà
	        try {
	            community.addRoad(cityAName, cityBName);
	            state.setText("Route ajoutée entre " + cityAName + " et " + cityBName);
	            state.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
	        } catch (CityNotFoundException e) {
	            state.setText("Erreur: L'une des villes n'a pas été trouvée.");
	            state.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
	        }
	    } else {
	        state.setText("Une route existe déjà entre " + cityAName + " et " + cityBName);
	        state.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
	    }
	}
}
