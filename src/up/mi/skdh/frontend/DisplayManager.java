package up.mi.skdh.frontend;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;

public class DisplayManager {
	
	public static ListView<String> displayCitiesWithNeighbors(UrbanCommunity community) {
	    ListView<String> cityAndNeighborsListView = new ListView<>();
	    for (City city : community.getCities()) {
	        StringBuilder neighborsText = new StringBuilder();
	        neighborsText.append("Ville: ").append(city.getName()).append(", Voisins: ");
	        for (City neighbor : city.getNeighbors()) {
	            neighborsText.append(neighbor.getName()).append(" ");
	        }
	        cityAndNeighborsListView.getItems().add(neighborsText.toString());
	    }
	    return cityAndNeighborsListView;
	}

	
	public static ListView displayUrbanCommunity(UrbanCommunity community) {
		ListView<String> cityListView = new ListView<>();
		for (City city : community.getCities()) {
		    cityListView.getItems().add(city.getName());
		}
		return cityListView;
	}
	
	public static ListView displayCitiesWithChargingPoint(UrbanCommunity community) {
		ListView<String> citiesWithChargingPointListView = new ListView<>();
		for (City city : community.getCities()) {
		    if (city.hasChargingPoint()) {
		        citiesWithChargingPointListView.getItems().add(city.getName());
		    }
		}
		return citiesWithChargingPointListView;
	}
	
	public static ListView displayCitiesWithNoChargingPoint(UrbanCommunity community) {
		ListView<String> citiesWithNoChargingPointListView = new ListView<>();
		for (City city : community.getCities()) {
		    if (!city.hasChargingPoint()) {
		        citiesWithNoChargingPointListView.getItems().add(city.getName());
		    }
		}
		return citiesWithNoChargingPointListView;
	}
}
