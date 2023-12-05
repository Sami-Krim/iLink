package up.mi.skdh.frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	/**
	 * Scanner pour lire les choix de l'utilisateur à visibilité privée 
	 */
	private Scanner choiceReader;
	/**
	 * Communauté urbaine gérée par cette classe à visibilité privée 
	 */
	private UrbanCommunity community;
	
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
				this.community.displayUrbanCommunity();
				this.addRoad(); //Ajouter une route
				this.community.displayCitiesAndNeighbors();
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
				this.community.displayCitiesWithNoChargingPoint();
				this.addChargingCity(); //Ajoute une borne de recharge 
				break;
			case 2:
				this.community.displayCitiesWithChargingPoint();
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
		System.out.println("Nom de la première ville : ");
		String cityAName = choiceReader.next(); //Lire le nom de la première ville
		System.out.println("Nom de la deuxième ville : ");
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
		}catch(CityNotFoundException | ChargingPointFoundException e) { //Si la ville n'a pas été trouvée
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
        /*System.out.println("Entre le chemin du fichier de la communaut�� urbaine: ");
        String filePath=this.choiceReader.next();
        */
		
		String filePath="C:\\Users\\SHAO\\Desktop\\text.txt";
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
        	String line;
        	while((line=fileReader.readLine())!=null) {
        		processFileLine(line);
        	}
            System.out.println("Communit�� charg��e avec succ��s!");
            this.community.displayUrbanCommunity();
            this.pausePrints();
        	try {
        		for(City city:this.community.getCities()) {
        			this.community.verifyAccessibilityConstraint(city);
        		}
        		System.out.println("Les contraintes d'accessibilit�� sont respect��es.");
        	}catch(AccessibilityConstraintNotVerifiedException e) {
        		System.out.println("Les contraintes d'accessibilit�� ne sont pas respect��es:"+ e.getMessage());
        	}
		}catch(IOException e) {
            System.out.println("Une erreur s'est produit lors de la lecture du fichier: "+e.getMessage());
        }
    }
	/**
	 * M��thode pour traiter chaque ligne du fichier
	 */
	private void processFileLine(String line) {
		line=line.toLowerCase();//Convertit la ligne en minuscule pour pas avoir des probl��me de casse
		if(line.startsWith("ville(")) {
			//Traitement pour ajouter une ville 
			String cityName=line.substring(6,line.length()-2);//Extraire le nom de la ville
			City city=new City(cityName);
			city.setChargingPoint(false);
			this.community.addCity(city);
		}else if(line.startsWith("route(")) {
			String[] cities=line.substring(6,line.length()-2).split(",");//Extraire les noms des villes
			if(cities.length==2) {
				this.community.addRoad(cities[0],cities[1]);
			}
		}else if(line.startsWith("recharge(")) {
			String cityName=line.substring(9,line.length()-2);
			try {
				City city=this.community.findCity(cityName);
				city.addChargingPoint();
			}catch(CityNotFoundException |ChargingPointFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		
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
