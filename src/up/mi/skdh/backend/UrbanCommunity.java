package up.mi.skdh.backend;


import java.util.ArrayList;

import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.CityNotFoundException;

public class UrbanCommunity {
	private ArrayList<City> cities;
	
	public UrbanCommunity() {
		this.cities = new ArrayList<City>();
	}
	
	//Retourne la liste des villes qui composent la communité
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
	
	//Retourne vrai si la contrainte d'accessibilité est vérifiée, lève une exception sinon
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
}
