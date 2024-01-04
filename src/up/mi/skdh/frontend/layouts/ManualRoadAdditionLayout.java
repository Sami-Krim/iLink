package up.mi.skdh.frontend.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.frontend.managers.CommunityManager;
import up.mi.skdh.frontend.managers.DisplayManager;

/**
 * La mise en page de la page d'ajout manuelle des routes de la communauté de l'application.
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */
public class ManualRoadAdditionLayout extends VBox {
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
    public ManualRoadAdditionLayout(Stage primaryStage, UrbanCommunity community) {
    	this.primaryStage = primaryStage;
    	this.community = community;

    	Label pageTitle = new Label("Ajouter une route");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #3137fd;");
		
		Label citiesListLabel = DisplayManager.displayUrbanCommunity(community);
		citiesListLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black;");
		
        Label cityALabel = new Label("Première ville :");
        cityALabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        TextField cityATextField = new TextField();
        cityATextField.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-alignment: center;");

        Label cityBLabel = new Label("Deuxième ville :");
        cityBLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        TextField cityBTextField = new TextField();
        cityBTextField.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-alignment: center;");

        Button addButton = new Button("Ajouter");
        Label resultLabel = new Label("");
        addButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        Button nextButton = new Button("Suivant");
        nextButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        nextButton.setOnAction(e -> this.switchProblemResolutionMenuLayout());
        
        addButton.setOnAction(e -> {
            String cityAName = cityATextField.getText();
            String cityBName = cityBTextField.getText();

            if (!cityAName.isEmpty() && !cityBName.isEmpty()) {
                CommunityManager.addRoad(this.community, cityAName, cityBName, resultLabel);
            } else {
                resultLabel.setText("Merci d'introduire le nom des deux villes.");
                resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
            }
        });
        
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(20));

        this.getChildren().addAll(pageTitle, citiesListLabel, cityALabel, cityATextField, cityBLabel, cityBTextField, resultLabel, addButton, nextButton);
        
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
    }
    
    /**
	 * Méthode pour passer à la page de résolution du problème
	 */
    private void switchProblemResolutionMenuLayout() {
		this.resolutionMenu = new ProblemResolutionMenuLayout(this.primaryStage, this.community);
		this.primaryStage.setScene(new Scene(this.resolutionMenu));
	}
}
