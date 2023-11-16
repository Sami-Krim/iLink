package up.mi.skdh.frontend;

import java.util.InputMismatchException;
import java.util.Scanner;

import up.mi.skdh.exceptions.*;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;

/**
 * Gère les interactions utilisateur via des menus pour manipuler la communauté urbaine.
 * Permet d'ajouter des routes entre les villes, de gérer les bornes de recharge et d'afficher les options.
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */

public class MenuControl {
	// **************************************************
    // Attributs
    // **************************************************
	private Scanner choiceReader; // Scanner pour lire les choix de l'utilisateur
	private UrbanCommunity community; // Communauté urbaine gérée par cette classe
	
	// **************************************************
    // Constructeurs
    // **************************************************
	
	/**
     * Constructeur initialisant le scanner et la communauté urbaine.
     */
	public MenuControl() {
		this.choiceReader=new Scanner(System.in);
		this.community=new UrbanCommunity();
	}
	
	// **************************************************
    // Méthodes privées
    // **************************************************
	
	/**
     * Affiche le premier menu pour ajouter des liaisons entre les villes.
     */
	private void displayMenu1() {
		int choice;
		do {
			System.out.println("<<<<<<<<<<<<<< Menu >>>>>>>>>>>>>>");
			System.out.println("1: Ajouter une route");
			System.out.println("2: Fin");
			choice = choiceReader.nextInt();
			
			switch(choice) {
			case 1:
				this.addRoad(); //Ajouter une route
				break;
			case 2:
				this.community.displayCitiesAndNeighbors(); //Afficher les villes et leurs voisins
				this.displayMenu2(); //Passer au menu de gestion des bornes de recharges
				break;
			default: //Si le choix n'est pas valid
				System.out.println("Choix invalide. Veuillez choisir une autre fois");
				break;
			}
			this.pausePrints(); //Pauser l'affichage
		} while(choice != 2);
		
	}
	
	/**
     * Affiche le deuxième menu pour gérer les bornes de recharge.
     */
	private void displayMenu2() {
		int choice;
		do {
			System.out.println("<<<<<<<<<<<<<< Menu >>>>>>>>>>>>>>");
			System.out.println("1: Ajouter une borne de recharge pour une ville");
			System.out.println("2: Retirer une borne de recharge d'une ville");
			System.out.println("3: Fin");
			choice = choiceReader.nextInt();
			
			switch(choice) {
			case 1:
				this.addChargingCity(); //Ajoute une borne de recharge 
				break;
			case 2:
				this.removeChargingCity(); //Retirer une borne de recharge
				break;
			case 3:
				System.out.println("FIN");
				this.community.displayCitiesWithChargingPoint(); //Afficher les villes possédant une borne de recharge
				this.choiceReader.close(); //Libérer le scanner
				System.exit(0); //EXIT
				break;
			default: //Si le choix n'est pas valid
				System.out.println("Choix invalide. Veuillez choisir une autre fois");
				break;
			}
			this.pausePrints(); //Pauser l'affichage
		} while(choice != 3);
	}
	
	/**
     * Méthode pour ajouter une route entre deux villes.
     */
	private void addRoad(){
		System.out.println("Donnez le nom des villes que vous souhaitez relier avec une route.");
		System.out.println("Nom de la ville A");
		String cityAName = choiceReader.next(); //Lire le nom de la première ville
		System.out.println("Nom de la ville B");
		String cityBName = choiceReader.next(); //Lire le nom de la deuxième ville
		if(community.addRoad(cityAName, cityBName)){ //Ajouter une route entre les deux villes.
			System.out.println("Route ajoutée avec succés entre " + cityAName + " et " + cityBName + ".");
		}
	}
	
	/**
     * Méthode pour ajouter une borne de recharge à une ville.
     */
	private void addChargingCity() {
		System.out.println("Indiquez le nom de la ville pour laquelle vous souhaitez ajouter une borne de recharge");
		String cityName = choiceReader.next(); //Lire le nom de la ville
		try {
			City city = community.findCity(cityName); //Rechercher la ville
			city.addChargingPoint(); //Ajouter le point de chargement à la ville
			System.out.println("Borne de recharge ajouter à la ville " + city.getName() + ".");
		}catch(CityNotFoundException e) { //Si la ville n'a pas été trouvée
			System.out.println(e.getMessage());
		}
	}
	
	/**
     * Méthode pour retirer une borne de recharge d'une ville.
     */
	private void removeChargingCity() {
		System.out.println("Indiquez le nom de la ville pour laquelle vous souhaitez retirer une borne de recharge");
		String cityName = choiceReader.next(); //Lire le nom de la ville
		try {
			City city = community.findCity(cityName); //Rechercher la ville
			city.removeChargingPoint(); //Retirer le point de chargement de la ville
			//Vérifier la contrainte d'accessibilité
			if (this.community.verifyAccessibilityConstraint(city)) { //Si elle est vérifiée
	            System.out.println("Borne de recharge retirée de la ville.");
	        }
	    } catch (CityNotFoundException | ChargingPointNotFoundException e) { //Si la ville n'a pas été trouvée u la ville ne possède pas de point de chargement
	        System.out.println(e.getMessage());
	    } catch (AccessibilityConstraintNotVerifiedException e) { //Si la contrainte d'accessibilité n'est pas vérifiée
	        System.out.println(e.getMessage());
	    }
	}
	
	/**
     * Méthode pour charger la communauté urbaine avec des villes et leurs noms.
     */
	private void loadUrbanCommunity() {
		int numberOfCities;
        while (true) {
            try {
                System.out.println("Merci d'introduire le nombre de villes : ");
                numberOfCities = this.choiceReader.nextInt(); //Lire le nombre de villes de la communauté urbaine
                break;
            } catch (InputMismatchException e) { //Si la valeur introduite n'est pas un nombre entier
                System.out.println("Erreur : Entrez un <<nombre>> <<entier>> pour le nombre de villes.");
            } finally {
            	this.choiceReader.nextLine(); //Consommer l'entrée précédente
            }
        }

        for (int i = 0; i < numberOfCities; i++) {
            System.out.print("Entrez le nom de la ville " + (i + 1) + " : ");
            String cityName = this.choiceReader.nextLine(); //Lire le nom de la ville
            City city = new City(cityName); //Créer une nouvelle ville
            this.community.addCity(city); //Ajouter la ville à la liste des villes de la communauté urbaine 
        }

        System.out.println("Communité chargée avec succès!");
        this.community.displayUrbanCommunity();
        this.pausePrints();
    }
	
	/**
     * Méthode pour afficher une pause dans l'exécution du programme.
     */
	private void pausePrints() {
		System.out.println("Appuyez sur Entrée pour continuer...");
        this.choiceReader.nextLine();
	}
	
	// **************************************************
    // Méthodes publiques
    // **************************************************
	
	/**
     * Lance l'application en chargeant la communauté urbaine et affichant le premier menu.
     */
	public void startApp() {
		System.out.println("Bienvenue dans le Gestionnaire de bornes de recharge !");
		this.loadUrbanCommunity();
		this.displayMenu1();
	}
}
