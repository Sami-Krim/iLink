package up.mi.skdh.frontend.commandLineVer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

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
	/**
	 * Générateur de nombres aléatoires
	 */
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
     * Affiche le menu principal pour choisir le type de résolution, la sauvegarde dans un fichier et le fin du programme.
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
			this.pausePrints();
			
			switch(choice) {
			case 1:
				this.displayManualMenu2();
				this.pausePrints(); //Pauser l'affichage
				break;
			case 2:
				this.automaticSolution();
				this.pausePrints(); //Pauser l'affichage
				break;
			case 3:
				this.saveCommunity();
				this.pausePrints();
				break;
			case 4:
				System.out.println("FIN!");
				this.community.displayCitiesAndNeighbors();
				this.community.displayCitiesWithChargingPoint();
				this.choiceReader.close(); //Libérer le scanner
				System.exit(0); //EXIT
				break;
			default: //Si le choix n'est pas valid
				System.out.println("Choix invalide. Veuillez choisir une autre fois");
				break;
			}
		} while(choice != 4);
		
	}
	
	/**
     * Affiche le deuxième menu la résolution manuelle afin de gérer les bornes de recharge.
     */
	private void displayManualMenu2() {
		int choice = 0;
		do {
			System.out.println("<<<<<<<<<<<<<< Menu >>>>>>>>>>>>>>");
			System.out.println("1: Ajouter une borne de recharge pour une ville");
			System.out.println("2: Retirer une borne de recharge d'une ville");
			System.out.println("3: Fin");
			try {
				choice = choiceReader.nextInt();
				switch(choice) {
				case 1:
					this.community.displayCitiesWithNoChargingPoint();
					this.addChargingCity(); //Ajoute une borne de recharge
					this.pausePrints();
					break;
				case 2:
					this.community.displayCitiesWithChargingPoint();
					this.removeChargingCity(); //Retirer une borne de recharge
					this.pausePrints();
					break;
				case 3:
					this.community.displayCitiesAndNeighbors();
					this.community.displayCitiesWithChargingPoint(); //Afficher les villes possédant une borne de recharge
					this.pausePrints();
					this.displayMainMenu();
					break;
				default: //Si le choix n'est pas valid
					System.out.println("Choix invalide. Veuillez choisir une autre fois");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Merci d'introduire un nombre correspondant à l'un des choix précédent");
				choiceReader.next();
			}
			
		} while(choice != 3);
	}
	
	/**
     * Méthode pour ajouter une borne de recharge à une ville.
     */
	private void addChargingCity() {
		if(this.community.getNumberChargingPoints() != this.community.getCities().size()) {
			System.out.println("Indiquez le nom de la ville pour laquelle vous souhaitez ajouter une borne de recharge");
			String cityName = choiceReader.next(); //Lire le nom de la ville
			try {
				City city = community.findCity(cityName); //Rechercher la ville
				city.addChargingPoint(); //Ajouter le point de chargement à la ville
				System.out.println("Borne de recharge ajouter à la ville " + city.getName() + ".");
			}catch(CityNotFoundException | ChargingPointFoundException e) { //Si la ville n'a pas été trouvée
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Toutes les villes possèdent une borne de recharge.");
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
	 * 
	 * @param filePath le chemin absolu auf fichier de la communauté
	 */
	private boolean loadUrbanCommunity(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
        	String line;
        	while((line = fileReader.readLine())!=null) {
        		this.processFileLine(line);
        	}
            System.out.println("Communité chargée avec succés!");
            this.community.displayUrbanCommunity();
            this.pausePrints();
        	try {
        		this.community.verifyAccessibilityConstraint();
        		System.out.println("La contrainte d'accessibilité est respectée.");
        		return true;
        	}catch(AccessibilityConstraintNotVerifiedException e) {
        		System.out.println("La contrainte d'accessibilité n'est pas respectée : "+ e.getMessage());
        		System.out.println("La solution naive sera utilisée");
        		this.community.naiveSolution();
        		this.pausePrints();
        		this.community.displayCitiesAndNeighbors();
        		this.community.displayCitiesWithChargingPoint();
        		return true;
        	}
		}catch(IOException e) {
            System.out.println("Une erreur s'est produit lors de la lecture du fichier : " + e.getMessage());
        }
		return false;
    }
	
	/**
	 * Méthode pour traiter chaque ligne du fichier
	 * 
	 * @param line La ligne à traiter
	 */
	private void processFileLine(String line) {
		String lowerCaseLine = line.toLowerCase();//Convertit la ligne en minuscule pour pas avoir des problème de casse
		if(lowerCaseLine.startsWith("ville(")) {
			//Traitement pour ajouter une ville 
			String cityName = line.substring(6, line.length() - 1).replace(" ", "");//Extraire le nom de la ville
			City city = new City(cityName);
			try {
				city.removeChargingPoint();
			} catch (ChargingPointNotFoundException e) {}
			this.community.addCity(city);
		}else if(lowerCaseLine.startsWith("route(")) {
			String[] cities = line.substring(6, line.length() - 1).replace(" ", "").split(",");//Extraire les noms des villes
			if(cities.length == 2) {
				this.community.addRoadLC(cities[0], cities[1]);
			}
		}else if(lowerCaseLine.startsWith("recharge(")) {
			String cityName = line.substring(9, line.length() - 1);
			try {
				City city = this.community.findCity(cityName);
				city.addChargingPoint();
			}catch(CityNotFoundException | ChargingPointFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Méthode qui le nombre d'itération de l'algorithme et propose une solution plus optimale.
	 */
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
		this.community.displayCitiesAndNeighbors();
		System.out.println("Le nombre de borne de recharge est : " + this.community.getNumberChargingPoints());
		this.community.displayCitiesWithChargingPoint();
	}
	
	/**
     * Méthode pour afficher une pause dans l'exécution du programme.
     */
	private void pausePrints() {
		System.out.println("Appuyez sur Entrée pour continuer...");
        this.choiceReader.nextLine();
	}
	
	/**
	 * Choisie une ville aléatoirement.
	 * 
	 * @return ville aléatoire parmis les villes de communauté
	 */
	private City getRandomCity() {
        int randomIndex = random.nextInt(this.community.getCities().size());
        return this.community.getCities().get(randomIndex);
    }
	
	/**
	 * Calcul le score de la communauté selon son état présent. Le score est le nombre de zones de recharge dans la communauté.
	 * 
	 * @return score de la communauté (nombre de zones de recharge)
	 */
	private int calculateScore() {
	    return this.community.getNumberChargingPoints();
	}

	/**
	 * Propose une solution plus optimale que la solution présente.
	 * 
	 * @param k le nombre d'itération de l'algorithme
	 */
	private void slightlyLessNaiveAlgorithm(int k) {
        int i = 0;
        int currentScore = this.calculateScore(); // Calculer le score actuel de la solution
        while (i < k) {
            City randomCity = getRandomCity(); // Obtenir une ville au hasard dans la communauté
            try {
	            if (randomCity.hasChargingPoint()) {
	                randomCity.removeChargingPoint();
	            } else {
	                randomCity.addChargingPoint();
	            }
	
	            int updatedScore = this.calculateScore();
	            if (updatedScore < currentScore) {
	                i = 0;
	                currentScore = updatedScore;
	            } else {
	                // Rollback the charging point operation
	                if (randomCity.hasChargingPoint()) {
	                    randomCity.removeChargingPoint();
	                } else {
	                    randomCity.addChargingPoint();
	                }
	            }
	            if (this.community.verifyAccessibilityConstraint(randomCity)) {
	                i++;
	            }
	        } catch (ChargingPointNotFoundException | ChargingPointFoundException | AccessibilityConstraintNotVerifiedException e) {}
        }
    }
	
	/**
	 * Méthode pour lire le chemin absolu du fichier à utiliser.
	 * 
	 * @return le chemin absolu au fichier
	 */
	private String readFilePath() {
		System.out.println("Préciser l'emplacement du fichier : ");
		String filePath = "";
		do {
			 filePath = this.choiceReader.nextLine().trim();
		} while(filePath == "");
		if (!filePath.toLowerCase().endsWith(".txt")) {
        	filePath += ".txt";
        }
		return filePath;
	}
	
	/**
	 * Méthode pour stocker la communauté dans un fichier .txt.
	 * Les villes sont stockées sous la forme : ville(X) 
	 * Les routes sont stockées sous la forme : route(X,Y)
	 * Les zones de recharge sont stockées sous la forme : recharge(X)
	 * Tel que : X et Y représentent des noms des villes.
	 */
	private void saveCommunity() {
		String filePath = this.readFilePath();
		Set<String> visitedRoads = new HashSet<>(); // Pour stocker les routes sous la forme villeAvilleB

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Ecrire les villes
            for (City city : this.community.getCities()) {
                writer.write("ville(" + city.getName() + ")\n");
            }

            // Ecrire les routes
            for (City city : this.community.getCities()) {
                List<City> neighbors = city.getNeighbors();
                for (City neighbor : neighbors) {
                    String roadKey = city.getName() + neighbor.getName();
                    String reverseRoadKey = neighbor.getName() + city.getName();
                    if (!visitedRoads.contains(roadKey) && !visitedRoads.contains(reverseRoadKey)) {
                        writer.write("route(" + city.getName() + "," + neighbor.getName() + ")\n");
                        visitedRoads.add(roadKey);
                    }
                }
            }

            // Ecrires les villes avec une zone de recharge
            for (City city : this.community.getCities()) {
                if (city.hasChargingPoint()) {
                    writer.write("recharge(" + city.getName() + ")\n");
                }
            }
            System.out.println("Communauté sauvegardée avec succés!");
        } catch (IOException e) {
            System.out.println(e.getMessage() + ". Merci de vérifier le chemin introduit.");
        }
	}
	
	// **************************************************
    // Méthodes publiques
    // **************************************************
	
	/**
	 * Lance l'application en chargeant la communauté urbaine d'un fichier et affichant le premier menu.
	 * 
	 * @param filePath Le chemin absolu vers le fichier contenant la communauté
	 */
	public void startApp(String filePath) {
		System.out.println("Bienvenue dans le Gestionnaire de bornes de recharge !");
		boolean result = this.loadUrbanCommunity(filePath);
		if(result) {
			this.displayMainMenu();
		}
	}
}
