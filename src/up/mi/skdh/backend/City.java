package up.mi.skdh.backend;


import java.util.ArrayList;

import up.mi.skdh.exceptions.ChargingPointNotFoundException;

public class City {
	private String name;
    private boolean chargingPoint;
    private ArrayList<City> neighbors;
    
    public City(String name) {
    	this.name = name;
    	this.chargingPoint = true;
    	this.neighbors = new ArrayList<City>();
    }
    
    public String getName() {
    	return this.name;
    }
    
    public boolean hasChargingPoint() {
    	return this.chargingPoint;
    }
    
    public ArrayList<City> getNeighbors(){
    	return this.neighbors;
    }
    
    public void addNeighbor(City city){
    	this.neighbors.add(city);
    	city.getNeighbors().add(this);
    }
    
    public void addChargingPoint() {
    	this.chargingPoint = true;
    }
    
    public void removeChargingPoint() throws ChargingPointNotFoundException{
    	if(!this.chargingPoint) {
    		throw new ChargingPointNotFoundException();
    	} else {
    		this.chargingPoint = false;
    	}
    }
}
