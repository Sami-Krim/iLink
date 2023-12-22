package up.mi.skdh.frontend.layouts;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;
import up.mi.skdh.frontend.managers.CommunityManager;
import up.mi.skdh.frontend.managers.DisplayManager;

public class ProblemResolutionMenuLayout extends ScrollPane {
	private Stage primaryStage;
	private SaveCommunityLayout saveCommunity;
	private ManualSolutionMenuLayout manualSolution;
	private UrbanCommunity community;
	
	public ProblemResolutionMenuLayout(Stage primaryStage, UrbanCommunity community) {
		this.primaryStage = primaryStage;
		this.community = community;
		
		VBox content = new VBox(20);
		
		Label pageTitle = new Label("Menu principal");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #3137fd;");
		Label communityLabel = new Label("");
		try {
			if(this.community.verifyAccessibilityConstraint()) {
				communityLabel.setText("La communité satisfait la contrainte d'accessibilité");
				communityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
			}
		} catch (AccessibilityConstraintNotVerifiedException e) {
			communityLabel.setText("La communité ne satisfait pas la contrainte d'accessibilité. Solution naive utilisée.");
			communityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
			this.community.naiveSolution();
		}
		
		Label citiesLabel = DisplayManager.displayUrbanCommunity(community);
		citiesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
		Label citiesWCPLabel = DisplayManager.displayCitiesWithChargingPoint(community);
		citiesWCPLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
		Label citiesWNCPLabel = DisplayManager.displayCitiesWithNoChargingPoint(community);
		citiesWNCPLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        
        ChoiceBox<String> menuChoices = new ChoiceBox<>();
        menuChoices.getItems().addAll("Résoudre manuellement", "Résoudre automatiquement", "Sauvegarder", "Fin");
        menuChoices.setValue("Résoudre manuellement");
        menuChoices.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-text-fill: #3137fd; -fx-font-weight: bold;");

        Button executeButton = new Button("Exécuter");
        executeButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
		
		BorderPane graphContainer = new BorderPane();
		Canvas canvas = DisplayManager.createGraphVisualization(this.community);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(canvas);

		graphContainer.setCenter(scrollPane);
		
        executeButton.setOnAction(event -> {
            String selectedChoice = menuChoices.getValue();
            executeSelectedChoice(selectedChoice, menuChoices, executeButton, pageTitle, communityLabel);
        });
		
		content.getChildren().addAll(pageTitle, communityLabel, graphContainer, citiesLabel, citiesWCPLabel, citiesWNCPLabel, menuChoices, executeButton);
		content.setAlignment(Pos.CENTER);
		this.setContent(content);
		this.setFitToWidth(true);
		this.setPadding(new Insets(20));
		this.setVvalue(0);
	}
	
	private void switchSaveCommunityLayout() {
		saveCommunity = new SaveCommunityLayout(this.primaryStage, this.community);
		this.primaryStage.setScene(new Scene(this.saveCommunity));
	}
	
	private void executeSelectedChoice(String selectedChoice, ChoiceBox<String> menu, Button execute, Label page, Label lbl) {
        switch (selectedChoice) {
            case "Résoudre manuellement":
            	manualSolution = new ManualSolutionMenuLayout(this.primaryStage, this.community);
            	this.primaryStage.setScene(new Scene(this.manualSolution));
                break;
            case "Résoudre automatiquement":
            	TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Solution automatique");
                dialog.setHeaderText(null);
                dialog.setContentText("Introduire le nombre d'essaie : ");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(iterations -> {
                    try {
                        int numberOfIterations = Integer.parseInt(iterations);
                        // Call the method that handles automatic solution with the provided number of iterations
                        CommunityManager.slightlyLessNaiveAlgorithm(this.community, numberOfIterations);
                        ProblemResolutionMenuLayout refreshPage = new ProblemResolutionMenuLayout(this.primaryStage, this.community);
                		this.primaryStage.setScene(new Scene(refreshPage));
                    } catch (NumberFormatException e) {
                        // Handle invalid input
                        // For example, show an error message dialog
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Merci d'introduire un nombre valide (entier positif)!");
                        alert.showAndWait();
                    }
                });
                break;
            case "Sauvegarder":
                this.switchSaveCommunityLayout();
                break;
            case "Fin":
            	page.setText("FIN !!");
            	VBox content = (VBox) this.getContent();
                content.getChildren().removeAll(menu, execute, lbl);
                break;
            default:
                break;
        }
    }
}
