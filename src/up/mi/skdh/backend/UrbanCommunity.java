package up.mi.skdh.backend;


import java.util.ArrayList;

import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.CityNotFoundException;

/**
 * Représente une communauté urbaine contenant des villes et leurs voisins.
 * Gère les villes, les routes entre elles et vérifie les contraintes d'accessibilité.
 * 
 * Cette classe facilite l'ajout, la recherche et l'affichage des villes dans la communauté urbaine.
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */

public class UrbanCommunity {
	// **************************************************
    // Attributs
    // **************************************************
	private ArrayList<City> cities; // Liste des villes dans la communauté
	
	// **************************************************
    // Constructeurs
    // **************************************************
	
	/**
     * Constructeur de la classe UrbanCommunity.
     * Initialise une liste vide de villes.
     */
	public UrbanCommunity() {
		this.cities = new ArrayList<City>();
	}
	
	/**
     * Retourne la liste des villes dans la communauté urbaine.
     * 
     * @return La liste des villes
     */
	public ArrayList<City> getCities(){
		return this.cities;
	}
	
	/**
     * Ajoute une ville dans la liste des villes.
     * 
     * @param city La ville à ajouter
     */
	public void addCity(City city){
		this.cities.add(city);
	}
	
	/**
     * Recherche une ville par son nom dans la liste des villes.
     * 
     * @param cityName Le nom de la ville à rechercher
     * @return L'objet ville s'il est trouvé
     * @throws CityNotFoundException Si la ville n'est pas trouvée dans la liste
     */
	public City findCity(String cityName) throws CityNotFoundException{
		for(City c : this.cities) {
			if(c.getName().equals(cityName)) {
				return c;
			}
		}
		throw new CityNotFoundException("La ville " + cityName + " n'existe pas.");
	}
	
	/**
     * Ajoute une route entre deux villes, les reliant.
     * 
     * @param cityAName Le nom de la première ville
     * @param cityBName Le nom de la deuxième ville
     * @return L'état de l'ajout de la route
     */
	public boolean addRoad(String cityAName, String cityBName) {
		try {
			City cityA = this.findCity(cityAName); //Rechercher la première ville
			City cityB = this.findCity(cityBName); //Rechercher la deuxième ville
			cityA.addNeighbor(cityB); //Ajouter la première ville à la liste des voisins de la deuxième ville
			cityB.addNeighbor(cityA); //Ajouter la deuxième ville à la liste des voisins de la première ville
			return true;
		} catch(CityNotFoundException e) { //Si l'une des villes n'a pas été trouvé
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
     * Vérifie si la contrainte d'accessibilité est respectée dans la communauté urbaine.
     * 
     * @param removedCity La ville pour laquelle la contrainte est vérifiée
     * @return true si la contrainte est vérifiée, sinon lance une exception
     * @throws AccessibilityConstraintNotVerifiedException Si la contrainte n'est pas respectée
     */
	public boolean verifyAccessibilityConstraint(City removedCity) throws AccessibilityConstraintNotVerifiedException{
		for (City city : cities) { //Parcourir toutes les villes de la communauté urbaine
            if (!city.hasChargingPoint()) { //Vérifier pour chaque ville ne possédant pas une borne de recharge si elle est en contact direct avec une autre ville (voisin) possédant une borne de recharge
                boolean hasNeighborWithCP = false; //Pour vérifier la contrainte
                for (City neighbor : city.getNeighbors()) {
                    if (neighbor.hasChargingPoint()) { //S'il existe au moins un voisin en contact direct et possédant une borne de recharge
                    	hasNeighborWithCP = true;
                        break;
                    }
                }
                if (!hasNeighborWithCP) { //Si aucune des villes possède est en contact direct avec une autre ville (voisin) possédant une borne de recharge
                	removedCity.addChargingPoint(); //Remettre la borne de racharge à la ville
                    throw new AccessibilityConstraintNotVerifiedException(); //Lever une exception 
                }
            }
        }
        return true;
    }
	
	/**
     * Affiche les villes dans la communauté urbaine ayant un point de recharge.
     */
	public void displayCitiesWithChargingPoint() {
		System.out.print("Les villes avec une zone de recharge sont: ");
		for(City city:cities) {
			if(city.hasChargingPoint()) {
				System.out.print(city.getName() + " ");
			}
		}
		System.out.println();
	}
	
	/**
     * Affiche toutes les villes dans la communauté urbaine.
     */
	public void displayUrbanCommunity() {
		System.out.print("Les villes de la communauté sont: ");
		for(City city:cities) {
			System.out.print(city.getName() + " ");
		}
		System.out.println();
	}
	
	/**
     * Affiche chaque ville dans la communauté urbaine avec ses voisins.
     */
    public void displayCitiesAndNeighbors() {
    	System.out.println("Les villes de la communauté avec leurs voisins: ");
        for (City city : cities) {
            System.out.print("Ville: " + city.getName() + ", Voisins: ");
            ArrayList<City> neighbors = city.getNeighbors();
            for (City neighbor : neighbors) {
                System.out.print(neighbor.getName() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
	
