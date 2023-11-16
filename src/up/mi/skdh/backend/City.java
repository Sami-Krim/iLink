package up.mi.skdh.backend;

import java.util.ArrayList;

import up.mi.skdh.exceptions.ChargingPointNotFoundException;

/**
 * Représente une ville avec ses attributs et méthodes associées.
 * Une ville possède un nom, peut avoir une borne de recharge et a une liste de voisins.
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */

public class City {
	// **************************************************
    // Attributs
    // **************************************************
	private String name; //Nom de la ville
    private boolean chargingPoint; //Montre si la ville possède une borne de recharge ou pas
    private ArrayList<City> neighbors; //Liste des voisins de la ville
    
    // **************************************************
    // Constructeurs
    // **************************************************
    
    /**
     * Constructeur de la classe City.
     * Initialise le nom de la ville, la présence d'une borne de recharge et la liste de voisins.
     * 
     * @param name Le nom de la ville
     */
    public City(String name) {
    	this.name = name;
    	this.chargingPoint = true;
    	this.neighbors = new ArrayList<City>();
    }

	// **************************************************
    // Méthodes publiques
    // **************************************************
    
    /**
     * Retourne le nom de la ville.
     * 
     * @return Le nom actuel de la ville
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Vérifie si la ville possède une borne de recharge.
     * 
     * @return true si la ville possède une borne de recharge, sinon false
     */
    public boolean hasChargingPoint() {
    	return this.chargingPoint;
    }
    
    /**
     * Récupère la liste des voisins de la ville.
     * 
     * @return La liste des villes voisines
     */
    public ArrayList<City> getNeighbors(){
    	return this.neighbors;
    }
    
    /**
     * Ajoute une ville à la liste des voisins.
     * 
     * @param city La ville à ajouter comme voisin
     */
    public void addNeighbor(City city){
    	this.neighbors.add(city);
    }
    
    /**
     * Ajoute une borne de recharge à la ville.
     */
    public void addChargingPoint() {
    	this.chargingPoint = true;
    }
    
    /**
     * Supprime la borne de recharge de la ville.
     * 
     * @throws ChargingPointNotFoundException Si la ville n'a pas de borne de recharge à supprimer
     */
    public void removeChargingPoint() throws ChargingPointNotFoundException{
    	if(!this.chargingPoint) {
    		throw new ChargingPointNotFoundException();
    	} else {
    		this.chargingPoint = false;
    	}
    }
}
