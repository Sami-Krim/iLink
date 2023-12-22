package up.mi.skdh.frontend.layouts;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.exceptions.ChargingPointFoundException;
import up.mi.skdh.exceptions.ChargingPointNotFoundException;
import up.mi.skdh.exceptions.CityNotFoundException;
import up.mi.skdh.frontend.managers.CommunityManager;
import up.mi.skdh.frontend.managers.DisplayManager;

public class ManualSolutionMenuLayout extends VBox {
	/**
	 * Le stage auquel la page est liée
	 */
	private Stage primaryStage;
	/**
	 * La page de résolution du problème
	 */
	private ProblemResolutionMenuLayout resolutionMenu;
	/**
	 * La communauté courrante
	 */
	private UrbanCommunity community;
	
	/**
	 * Constructeur de la classe.
	 * Initialise la mise en page et le fonctionnement des différents composants.
	 * 
	 * @param primaryStage Le stage principale de l'application
	 * @param community La communauté a utilisé
	 */
	public ManualSolutionMenuLayout(Stage primaryStage, UrbanCommunity community) {
		this.primaryStage = primaryStage;
		this.community = community;
		
		Label pageTitle = new Label("Résolution manuelle");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #3137fd;");
		Label descriptionLabel = new Label("Gestion des zones de recharges");
		descriptionLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
		Label stateLabel = new Label("");
		if(community.getNumberChargingPoints() == community.getCities().size()) {
			stateLabel.setText("Toutes les villes possèdent une borne de recharge.");
    		stateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
		}
		
		Button removeChargingPointButton = new Button("Supprimer");
		removeChargingPointButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        removeChargingPointButton.setOnAction(e -> removeChargingPoint(stateLabel));
        Button addChargingPointButton = new Button("Ajouter");
        addChargingPointButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox controlButtonsContainer = new HBox(65);
        controlButtonsContainer.setAlignment(Pos.CENTER);
        controlButtonsContainer.getChildren().addAll(removeChargingPointButton, addChargingPointButton);
        addChargingPointButton.setOnAction(e -> addChargingPoint(stateLabel));
        
        Button endButton = new Button("Précedent");
        endButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        endButton.setOnAction(e -> switchProblemResolutionMenuLayout());

        setSpacing(75);
        setAlignment(Pos.CENTER);
        this.getChildren().addAll(pageTitle, descriptionLabel, stateLabel, controlButtonsContainer, endButton);
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
    }

	/**
	 * Méthode pour traiter graphiquement la suppression d'une zone de recharge
	 * 
	 * @param state Le label d'état de l'opération
	 */
    private void removeChargingPoint(Label state) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Supprimer une zone de recharge");
        dialog.setHeaderText(DisplayManager.displayCitiesWithChargingPoint(community).getText());
        dialog.setContentText("Indiquer le nom de la ville :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cityName -> {
        	try {
                CommunityManager.removeChargingCity(community, cityName, state);
            } catch (CityNotFoundException e) {
            	displayErrorMessage("Erreur", "La ville " + cityName + " n'existe pas.");
            } catch (ChargingPointNotFoundException e) {
                displayErrorMessage("Erreur", e.getMessage());
            } catch (AccessibilityConstraintNotVerifiedException e) {
                displayErrorMessage("Erreur", e.getMessage());
            }
        });
    }
    
    /**
     * Méthode pour traiter graphiquement les messages d'erreur
     * 
     * @param title Le titre de la fenêtre d'erreur
     * @param message L'information a affiché
     */
    private void displayErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
	 * Méthode pour traiter graphiquement l'ajout d'une zone de recharge
	 * 
	 * @param state Le label d'état de l'opération
	 */
    private void addChargingPoint(Label state) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter une zone de recharge");
        dialog.setHeaderText(DisplayManager.displayCitiesWithNoChargingPoint(community).getText());
        dialog.setContentText("indiquer le nom de la ville :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cityName -> {
        	try {
                CommunityManager.addChargingCity(community, cityName, state);
            } catch (CityNotFoundException e) {
            	displayErrorMessage("Erreur", "La ville " + cityName + "n'existe pas.");
            } catch (ChargingPointFoundException e) {
                displayErrorMessage("Erreur", e.getMessage());
            } catch (AccessibilityConstraintNotVerifiedException e) {
                displayErrorMessage("Erreur", e.getMessage());
            }
        });
	}
    
    /**
	 * Méthode pour passer à la page de résolution du problème
	 */
    private void switchProblemResolutionMenuLayout() {
		this.resolutionMenu = new ProblemResolutionMenuLayout(this.primaryStage, this.community);
		this.primaryStage.setScene(new Scene(this.resolutionMenu));
	}
}
