package up.mi.skdh.frontend;

import java.util.Scanner;

import up.mi.skdh.backend.UrbanCommunity;

public class MenuControl {
	private Scanner choiceReader;
	private UrbanCommunity community;
	
	public MenuControl() {
		this.choiceReader = new Scanner(System.in);
		this.community = new UrbanCommunity();
	}
	
	public void displayMenu(int part){
		System.out.println("<<<<<<<<<<<<<< Menu >>>>>>>>>>>>>>");
		if(part == 1) {
			System.out.println("");
		} else {
			
		}
	}
	

}
