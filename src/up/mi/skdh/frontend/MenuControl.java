package up.mi.skdh.frontend;
import java.util.Scanner;

import up.mi.skdh.exceptions.*;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;

public class MenuControl {
	private Scanner choiceReader;
	private UrbanCommunity community;
	
	public MenuControl() {
		this.choiceReader=new Scanner(System.in);
		this.community=new UrbanCommunity();
	}
	//Menu qui permet a l'utilisateur de mettre des routes entre les villes
	public void displayMenu() {
		int choice;
		do {
			System.out.println("====Menu====");
			System.out.println("1: Ajouter une route");
			System.out.println("2: Fin");
			choice=choiceReader.nextInt();
			
			switch(choice) {
			case 1:
				//Ajouter les routes
				addRoad();
				break;
			case 2:
				//Quitte l'option ajouter des routes pour avoir des nouvelles options
				displayMenu2();
				break;
			default:
				System.out.println("Choix invalit¨¦. Veuillez choisir ¨¤ nouveaux");
				break;
			}
		}while(choice!=2);
		
	}
	//Interface du 2¨¨me menu d'option pour ajouter ou retirer des borne
	private void displayMenu2() {
		int choice;
		do {
			System.out.println("====Option====");
			System.out.println("1: Ajouter une borne de recharge ¨¤ une ville");
			System.out.println("2: Retirer une borne de recharge d'une ville");
			System.out.println("3: Fin");
			choice=choiceReader.nextInt();
			
			switch(choice) {
			case 1:
				//Ajoute des borne de recharge 
				addChargingCities();
				break;
			case 2:
				//Retirer les bornes de recharge
				removeChargingCities();
				break;
			case 3:
				System.out.println("Fin des options");
				System.exit(0);
				break;
			default:
				System.out.println("Choix invalit¨¦. Veuillez choisir ¨¤ nouveaux");
				break;
			}
		}while(choice!=3);
	}
	//M¨¦thode qui permet d'ajouter des routes entre 2 villes
	private void addRoad(){
		System.out.println("Nom des villes que vous souhaitez relier avec une route");
		System.out.println("Nom de la ville A");
		String cityAName=choiceReader.next();
		System.out.println("Nom de la ville B");
		String cityBName=choiceReader.next();
		community.addRoad(cityAName, cityBName);
		System.out.println("Route ajout¨¦e avec succ¨¨s");

	}
	//M¨¦thode pour ajouter une borne de recharge dans une ville avec le choix d'indique la ville
	private void addChargingCities() {
		System.out.println("Nom de la ville que vous voulez mettre une borne de recharge");
		String cityName=choiceReader.next();
		try {
			City city=community.findCity(cityName);
			city.addChargingPoint();
			System.out.println("Borne de recharge ajouter ¨¤ la ville");
		}catch(CityNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	//M¨¦thode pour retirer une borne de recharge d'une ville 
	private void removeChargingCities() {
		System.out.println("Nom de la ville que vous souhaitez retire la borne de recharge");
		String cityName=choiceReader.next();
		try {
			City city=community.findCity(cityName);
			city.removeChargingPoint();
			System.out.println("Borne de recharge retire de la ville");
		}catch(CityNotFoundException |ChargingPointNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
