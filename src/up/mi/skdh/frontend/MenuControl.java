package up.mi.skdh.frontend;

import java.util.InputMismatchException;
import java.util.Random;
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
	
	private Random random;
	
	// **************************************************
    // Constructeurs
    // **************************************************
	
	/**
     * Constructeur initialisant le scanner et la communauté urbaine.
     */
	public MenuControl() {
		this.choiceReader = new Scanner(System.in);
		this.random = new Random();
		this.community = new UrbanCommunity();
	}
	
	// **************************************************
    // Méthodes privées
    // **************************************************
	
	/**
     * Affiche le premier menu pour ajouter des liaisons entre les villes.
     */
	private void displayMainMenu() {
		int choice;
		do {
			System.out.println("<<<<<<<<<<<<<< Menu principal >>>>>>>>>>>>>>");
			System.out.println("1: Résoudre manuellement");
			System.out.println("2: Résoudre automatiquement");
			System.out.println("3: Sauvegarder");
			System.out.println("4: Fin");
			choice = choiceReader.nextInt();
			this.community.displayCitiesAndNeighbors();
			this.community.displayCitiesWithChargingPoint();
			
			switch(choice) {
			case 1:
				this.displayManualMenu2();
				break;
			case 2:
				this.automaticSolution();
				break;
			case 3:
				//this.saveCommunity();
				break;
			case 4:
				break;
			default: //Si le choix n'est pas valid
				System.out.println("Choix invalide. Veuillez choisir une autre fois");
				break;
			}
			this.pausePrints(); //Pauser l'affichage
		} while(choice != 4);
		
	}
	
	/**
     * Affiche le premier menu pour ajouter des liaisons entre les villes.
     */
	private void displayManualMenu1() {
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
				//this.displayManualMenu2(); //Passer au menu de gestion des bornes de recharges
				this.displayMainMenu();
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
	private void displayManualMenu2() {
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
		int numberOfCities;
        while (true) {
            try {
                System.out.println("Merci d'introduire le nombre de villes : ");
                numberOfCities = this.choiceReader.nextInt(); //Lire le nombre de villes de la communauté urbaine
                if (numberOfCities >= 0) {
                	break;
                } else {
                	throw new InputMismatchException();
                }
            } catch (InputMismatchException e) { //Si la valeur introduite n'est pas un nombre entier
                System.out.println("Erreur : Entrez un <<nombre>> <<entier>> <<positif>> pour le nombre de villes.");
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
        this.displayManualMenu1();

        System.out.println("Communité chargée avec succès!");
        this.community.displayUrbanCommunity();
        this.pausePrints();
    }
	
	
	private void automaticSolution() {
		int k;
		while (true) {
            try {
                System.out.println("Merci d'introduire le nombre d'itérations : ");
                k = this.choiceReader.nextInt(); //Lire le nombre d'itérations
                if (k >= 0) {
                	break;
                } else {
                	throw new InputMismatchException();
                }
            } catch (InputMismatchException e) { //Si la valeur introduite n'est pas un nombre entier
                System.out.println("Erreur : Entrez un <<nombre>> <<entier>> <<positif>>.");
            } finally {
            	this.choiceReader.nextLine(); //Consommer l'entrée précédente
            }
        }
		
		this.slightlyLessNaiveAlgorithm(k);
	}
	
	/**
     * Méthode pour afficher une pause dans l'exécution du programme.
     */
	private void pausePrints() {
		System.out.println("Appuyez sur Entrée pour continuer...");
        this.choiceReader.nextLine();
	}
	
	private City getRandomCity() {
        int randomIndex = random.nextInt(this.community.getCities().size());
        return this.community.getCities().get(randomIndex);
    }
	
	private void naiveAlgorithm(int k) {
        int i = 0;
        while (i < k) {
            City randomCity = getRandomCity(); // Obtenir une ville au hasard dans la communauté
            if (randomCity.hasChargingPoint()) {
                try {
					randomCity.removeChargingPoint();
				} catch (ChargingPointNotFoundException e) {
					System.out.println(e.getMessage());
				}
            } else {
                try {
					randomCity.addChargingPoint();
				} catch (ChargingPointFoundException e) {
					System.out.println(e.getMessage());
				}
            }
            try {
    			if(this.community.verifyAccessibilityConstraint(randomCity)) {
    				i++;
    			}
    		} catch(AccessibilityConstraintNotVerifiedException e) {
    			System.out.println(e.getMessage());
    		}
        }
    }
	
	private int calculateScore() {
	    return this.community.getNumberChargingPoints();
	}

	private void slightlyLessNaiveAlgorithm(int k) {
        int i = 0;
        int currentScore = calculateScore(); // Calculer le score actuel de la solution
        while (i < k) {
            City randomCity = getRandomCity(); // Obtenir une ville au hasard dans la communauté
            if (randomCity.hasChargingPoint()) {
                try {
					randomCity.removeChargingPoint();
				} catch (ChargingPointNotFoundException e) {
					System.out.println(e.getMessage());
				}
            } else {
                try {
					randomCity.addChargingPoint();
				} catch (ChargingPointFoundException e) {
					System.out.println(e.getMessage());
				}
            }
            int updatedScore = calculateScore(); // Recalcul du score après la modification
            if (updatedScore < currentScore) {
                i = 0;
                currentScore = updatedScore;
            } else {
            	 try {
         			if(this.community.verifyAccessibilityConstraint(randomCity)) {
         				i++;
         			}
         		} catch(AccessibilityConstraintNotVerifiedException e) {
         			System.out.println(e.getMessage());
         		}
            }
        }
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
		this.displayMainMenu();;
	}
}
