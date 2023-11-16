package up.mi.skdh.backend;


import java.util.ArrayList;
import java.util.InputMismatchException;//Exception sur l'erreur de frappe
import java.util.Scanner;

import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.ChargingPointNotFoundException;
import up.mi.skdh.exceptions.CityNotFoundException;

public class UrbanCommunity {
	private ArrayList<City> cities;
	
	public UrbanCommunity() {
		this.cities = new ArrayList<City>();
	}
	public UrbanCommunity(int numberCity) {
		this.cities=new ArrayList<City>(numberCity);
	}
	
	//Retourne la liste des villes qui composent la communit√©
	public ArrayList<City> getCities(){
		return this.cities;
	}
	
	//Recherche une ville par son nom dans la liste des villes
	public City findCity(String cityName) throws CityNotFoundException{
		for(City c : this.cities) {
			if(c.getName().equals(cityName)) {
				return c;
			}
		}
		throw new CityNotFoundException("La ville " + cityName + " n'existe pas.");
	}
	
	//Ajoute une route entre deux villes
	public void addRoad(String cityAName, String cityBName) {
		try {
			City cityA = this.findCity(cityAName);
			City cityB = this.findCity(cityBName);
			cityA.addNeighbor(cityB);
			cityB.addNeighbor(cityA);
		} catch(CityNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Retourne vrai si la contrainte d'accessibilit√© est v√©rifi√©e, l√®ve une exception sinon
	public boolean verifyAccessibilityConstraint() throws AccessibilityConstraintNotVerifiedException{
		for (City city : cities) {
            if (!city.hasChargingPoint()) {
                boolean hasNeighborWithCP = false;
                for (City neighbor : city.getNeighbors()) {
                    if (neighbor.hasChargingPoint()) {
                    	hasNeighborWithCP = true;
                        break;
                    }
                }
                if (!hasNeighborWithCP) {
                    throw new AccessibilityConstraintNotVerifiedException();
                }
            }
        }
        return true;
    }
	//Affichage des villes avec une borne de recharge
	public void displayCitiesWithChargingPoint() {
		System.out.println("Affichage des villes avec une zone de recharge");
		for(City city:cities) {
			if(city.hasChargingPoint()) {
				System.out.println(city.getName()+" ");
			}
		}
		System.out.println("\n");
	}
	
	//Affichage des villes dans la communaut®¶
	public void displayUrbanCommunity() {
		System.out.println("Affichage des villes dans la communaut®¶");
		for(City city:cities) {
			System.out.println(city.getName()+" ");
		}
		System.out.println("\n");
	}
	//Affichage de la ville et de son voisin
    public void displayCitiesAndNeighbors() {
        for (City city : cities) {
            System.out.println("Ville: " + city.getName() + ", Voisins: ");
            ArrayList<City> neighbors = city.getNeighbors();
            for (City neighbor : neighbors) {
                System.out.print(neighbor.getName() + " ");
            }
            System.out.println("");
        }
    }
}//Fin classe UrbanCommunity
	
