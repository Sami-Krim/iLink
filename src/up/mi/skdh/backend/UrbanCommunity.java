package up.mi.skdh.backend;


import java.util.ArrayList;

import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.ChargingPointFoundException;
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
	/**
	 * Liste des villes de la communauté à visibilité privée 
	 */
	private ArrayList<City> cities; //Liste des villes de la communauté à visibilité privée 
	
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
	 * Permet de v��rifie si une ville avec le nom sp��cifi�� existe dans la liste des villes 
	 * @param cityName
	 * @return true si la ville �� le m��me nom, sinon false
	 */
	public boolean hasCity(String cityName){
		for(City city : this.cities) {
			if(city.getName().equals(cityName)) {
				return true; //La ville avec le nom a ��t�� trouv��e.
			}
		}
		return false;//La ville avec le nom n'as pas ��t�� trouv��e.
	}
	/**
	 * Permet de v��rifie si une route existe ou pas entre ces deux villes.
	 * @param cityAName
	 * @param cityBName
	 * @return l'existance d'une route entre les deux villes.
	 */
	public boolean hasRoad(String cityAName, String cityBName) {
	    try {
	    	//Recherche des objets City correspondants au nom de ville 
	        City cityA = this.findCity(cityAName);
	        City cityB = this.findCity(cityBName);
	        
	        //V��rifier si la villeA et villeB sont voisin
	        return cityA.getNeighbors().contains(cityB);
	    } catch (CityNotFoundException e) {}
	    return false;//L'une des villes n'a pas ��t�� trouv��e, donc il n'y a pas de route entre ces 2 villes.
	}

	
	/**
     * Retourne le nombre de villes avec une zone de recharge dans la communauté urbaine.
     * 
     * @return Le nombre de villes avec une zone de recharge
     */
	public int getNumberChargingPoints() {
	    int totalChargingPoints = 0;
	    for (City city : this.cities) {
	        if (city.hasChargingPoint()) {
	            totalChargingPoints++;
	        }
	    }
	    return totalChargingPoints;
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
	public void addRoad(String cityAName, String cityBName) throws CityNotFoundException{
		City cityA = this.findCity(cityAName); //Rechercher la première ville
		City cityB = this.findCity(cityBName); //Rechercher la deuxième ville
		cityA.addNeighbor(cityB); //Ajouter la première ville à la liste des voisins de la deuxième ville
		cityB.addNeighbor(cityA); //Ajouter la deuxième ville à la liste des voisins de la première ville
	}
	
	/**
     * Ajoute une route entre deux villes, les reliant.
     * 
     * @param cityAName Le nom de la première ville
     * @param cityBName Le nom de la deuxième ville
     * @return L'état de l'ajout de la route
     */
	public boolean addRoadLC(String cityAName, String cityBName) {
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
                	try {
						removedCity.addChargingPoint();
					} catch (ChargingPointFoundException e) {
						System.out.println(e.getMessage());
					} //Remettre la borne de racharge à la ville
                    throw new AccessibilityConstraintNotVerifiedException(); //Lever une exception 
                }
            }
        }
        return true;
    }
	
	/**
     * Vérifie si la contrainte d'accessibilité est respectée dans la communauté urbaine. 
     * 
     * @return true si la contrainte est vérifiée, sinon lance une exception
     * @throws AccessibilityConstraintNotVerifiedException Si la contrainte n'est pas respectée
     */
	public boolean verifyAccessibilityConstraint() throws AccessibilityConstraintNotVerifiedException{
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
                    throw new AccessibilityConstraintNotVerifiedException(); //Lever une exception 
                }
            }
        }
        return true;
    }
	
	/**
	 * Ajoute une borne de racharge à toutes les villes (solution naive).
	 */
	public void naiveSolution() {
		if(this.cities.size() > 0 ) {
			for(City city : this.cities) {
				try {
					city.addChargingPoint();
				} catch (ChargingPointFoundException e) {}
			}
		}
	}
	
	/**
     * Affiche les villes dans la communauté urbaine ayant un point de recharge.
     */
	public void displayCitiesWithChargingPoint() {
		System.out.print("Les villes avec une zone de recharge sont : ");
		for(City city:cities) {
			if(city.hasChargingPoint()) {
				System.out.print(city.getName() + " ");
			}
		}
		System.out.println();
	}
	
	/**
     * Affiche les villes dans la communauté urbaine n'ayant pas un point de recharge.
     */
	public void displayCitiesWithNoChargingPoint() {
		System.out.print("Les villes n'ayant pas une zone de recharge sont : ");
		for(City city:cities) {
			if(!city.hasChargingPoint()) {
				System.out.print(city.getName() + " ");
			}
		}
		System.out.println();
	}
	
	/**
     * Affiche toutes les villes dans la communauté urbaine.
     */
	public void displayUrbanCommunity() {
		System.out.print("Les villes de la communauté sont : ");
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
	
