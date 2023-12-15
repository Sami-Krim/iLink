package up.mi.skdh.frontend;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.ChargingPointFoundException;
import up.mi.skdh.exceptions.ChargingPointNotFoundException;
import up.mi.skdh.exceptions.CityNotFoundException;

public class FilePathReadingLayout extends VBox {
	private Stage primaryStage;
	private StartLayout starter;
	private ProblemResolutionMenuLayout resolutionMenu;
	private UrbanCommunity community;
	
	
	public FilePathReadingLayout(Stage primaryStage) {
		this.community = new UrbanCommunity();
		this.primaryStage = primaryStage;
		Label pageTitle = new Label("File Path Reader");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");
		TextField filePathInput = new TextField();
        filePathInput.setText("Entrez le chemin au fichier");
        Button browseButton = new Button("Parcourir");
        browseButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        Label resultReading = new Label("");
        Button nextButton = new Button("Suivant");
        nextButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        Button goBackButton = new Button("Précedent");
        goBackButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox filePathContainer = new HBox(30);
        filePathContainer.setAlignment(Pos.CENTER);     		filePathInput.prefWidthProperty().bind(filePathContainer.widthProperty().multiply(0.75));
        filePathContainer.getChildren().addAll(filePathInput, browseButton);
        
        HBox navigationButtonsContainer = new HBox(50);
        navigationButtonsContainer.setAlignment(Pos.CENTER);
        navigationButtonsContainer.getChildren().addAll(goBackButton, nextButton);
        
        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un fichier");
            
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            
            File selectedFile = fileChooser.showOpenDialog(null);
            
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                filePathInput.setText(filePath);
                this.loadUrbanCommunity(filePath, resultReading);
                nextButton.setOnAction(e -> this.switchProblemResolutionMenuLayout());
            }
        });
        
        goBackButton.setOnAction(e -> this.switchStartLayout());
        
        setSpacing(75);
        setAlignment(Pos.CENTER);
        this.getChildren().addAll(pageTitle, filePathContainer, resultReading, navigationButtonsContainer);
        
	}
	
	private void switchStartLayout() {
		starter = new StartLayout(this.primaryStage);
		this.primaryStage.setScene(new Scene(this.starter));
	}
	
	private void switchProblemResolutionMenuLayout() {
		this.resolutionMenu = new ProblemResolutionMenuLayout(this.primaryStage, this.community);
		this.primaryStage.setScene(new Scene(this.resolutionMenu));
	}
	
	private void loadUrbanCommunity(String filePath, Label result) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
        	String line;
        	while((line = fileReader.readLine())!=null) {
        		this.processFileLine(line);
        	}
		}catch(IOException e) {
            result.setText("Une erreur s'est produit lors de la lecture du fichier : " + filePath);
            result.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
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
				this.community.addRoad(cities[0], cities[1]);
			}
		}else if(lowerCaseLine.startsWith("recharge(")) {
			String cityName = line.substring(9, line.length() - 2);
			try {
				City city = this.community.findCity(cityName);
				city.addChargingPoint();
			}catch(CityNotFoundException | ChargingPointFoundException e) {}
		}
	}
}
