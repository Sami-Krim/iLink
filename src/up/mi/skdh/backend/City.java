package up.mi.skdh.backend;

import java.util.ArrayList;

import up.mi.skdh.exceptions.ChargingPointNotFoundException;

public class City {
	private String name; //Nom de la ville
    private boolean chargingPoint; //Montre si la ville possède une borne de recharge ou pas
    private ArrayList<City> neighbors; //Liste des voisins de la ville
    
    public City(String name) {
    	this.name = name;
    	this.chargingPoint = true;
    	this.neighbors = new ArrayList<City>();
    }
    
    //Retourne le nom de la ville
    public String getName() {
    	return this.name;
    }
    
    //Retourne un booleen montrant si la ville possède une borne de recharge ou pas
    public boolean hasChargingPoint() {
    	return this.chargingPoint;
    }
    
    //Retourne la liste des voisins de la ville
    public ArrayList<City> getNeighbors(){
    	return this.neighbors;
    }
    
    //Ajouter un voisin à la liste des voisins de la ville
    public void addNeighbor(City city){
    	this.neighbors.add(city);
    	city.getNeighbors().add(this);
    }
    
    //Ajouter une point de chargement à la ville
    public void addChargingPoint() {
    	this.chargingPoint = true;
    }
    
    //Retirer le point de chargement de la ville
    public void removeChargingPoint() throws ChargingPointNotFoundException{
    	if(!this.chargingPoint) {
    		throw new ChargingPointNotFoundException();
    	} else {
    		this.chargingPoint = false;
    	}
    }
}
