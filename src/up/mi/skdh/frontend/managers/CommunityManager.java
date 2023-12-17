package up.mi.skdh.frontend.managers;

import java.util.Random;

import javafx.scene.control.Label;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.ChargingPointFoundException;
import up.mi.skdh.exceptions.ChargingPointNotFoundException;
import up.mi.skdh.exceptions.CityNotFoundException;

public class CommunityManager {
	private static City getRandomCity(UrbanCommunity community) {
		Random random = new Random();
        int randomIndex = random.nextInt(community.getCities().size());
        return community.getCities().get(randomIndex);
    }
	
	private static int calculateScore(UrbanCommunity community) {
	    return community.getNumberChargingPoints();
	}

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
	
	public static void removeChargingCity(UrbanCommunity community, String cityName, Label state) throws CityNotFoundException, ChargingPointNotFoundException, AccessibilityConstraintNotVerifiedException {
		City city = community.findCity(cityName); //Rechercher la ville
		city.removeChargingPoint(); //Retirer le point de chargement de la ville
		//Vérifier la contrainte d'accessibilité
		if (community.verifyAccessibilityConstraint(city)) { //Si elle est vérifiée
            state.setText("Borne de recharge retirée de la ville " + cityName);
    		state.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
        }
	}
	
	public static void addChargingCity(UrbanCommunity community, String cityName, Label state) throws CityNotFoundException, ChargingPointFoundException, AccessibilityConstraintNotVerifiedException {
		City city = community.findCity(cityName); //Rechercher la ville
		city.addChargingPoint(); //Ajouter le point de chargement à la ville
		System.out.println("Borne de recharge ajouter à la ville " + city.getName() + ".");
		state.setText("Borne de recharge ajoutée à la ville " + cityName);
		state.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
	}
}
