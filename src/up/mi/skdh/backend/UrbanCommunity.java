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
    //Menu pour l'utilisateur
	public void menuCommunity() {
		Scanner scanner =new Scanner(System.in);
		while(true) {
			System.out.println("\nMenu");
			System.out.println("1: Ajouter une ville");
			System.out.println("2: Ajouter une route");
			System.out.println("3: Ajouter une borne de recharge ®§ une ville");
			System.out.println("4: Retirer une borne de recharge d'une ville");
			System.out.println("5: Affichage des villes et leur voisin");
			System.out.println("6: Afficher les villes avec une zone de recharge");
			System.out.println("7: Afficher des villes dans la communaut®¶");
			System.out.println("8: V®¶rifier la contrainte d'accessibilit®¶");
			System.out.println("9: Quitter/Leave");
			
			System.out.println("Choix:");
			int choice=scanner.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Nom de la ville");
				String cityName=scanner.next();
				this.getCities().add(new City(cityName));
				break;
			case 2:
				System.out.println("Nom des villes que vous souhaitez relier avec une route");
				System.out.println("Nom de la ville A");
				String cityAName=scanner.next();
				System.out.println("Nom de la ville B");
				String cityBName=scanner.next();
				this.addRoad(cityAName, cityBName);
				break;
			case 3:
				System.out.println("Nom de la ville que vous voulez mettre une borne de recharge");
				cityName=scanner.next();
				try {
					City city=this.findCity(cityName);
					city.addChargingPoint();
					System.out.println("Borne de recharge ajouter ®§ la ville");
				}catch(CityNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Nom de la ville que vous souhaitez retire la borne de recharge");
				cityName=scanner.next();
				try {
					City city=this.findCity(cityName);
					city.removeChargingPoint();
					System.out.println("Borne de recharge retire de la ville");
				}catch(CityNotFoundException |ChargingPointNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				this.displayCitiesAndNeighbors();
				break;
			case 6:
				this.displayCitiesWithChargingPoint();
				break;
			case 7:
				this.displayUrbanCommunity();
				break;
			case 8:
				try {
					this.verifyAccessibilityConstraint();
	                System.out.println("Contrainte d'accessibilit®¶ v®¶rifi®¶e.");
				}catch (AccessibilityConstraintNotVerifiedException e) {
					System.out.println("Contrainte d'accessibilit®¶ non v®¶rifi®¶e : " + e.getMessage());
	            }
				break;
			case 9:
				System.out.println("Fin du programme");
				System.exit(0);
				break;
			default:
				System.out.println("Choix invalide.Veuillez r®¶assayer.");
			}
		}//Fin boucle While
	}
}//Fin classe UrbanCommunity
	
