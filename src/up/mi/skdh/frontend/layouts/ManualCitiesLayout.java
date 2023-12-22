package up.mi.skdh.frontend.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;

public class ManualCitiesLayout extends VBox {
	/**
	 * Le stage auquel la page est liée
	 */
	private Stage primaryStage;
	/**
	 * La Landing page
	 */
	private StartLayout starter;
	/**
	 * La page d'ajout des routes
	 */
	private ManualRoadAdditionLayout roads;
	/**
	 * La communauté courrante
	 */
	private UrbanCommunity community;
	
	/**
	 * Constructeur de la classe.
	 * Initialise la mise en page et le fonctionnement des différents composants.
	 * 
	 * @param primaryStage Le stage principale de l'application
	 * @param numberOfCities Le nombre de villes à ajouter
	 */
	public ManualCitiesLayout(Stage primaryStage, int numberOfCities) {
		this.primaryStage = primaryStage;
		this.community = new UrbanCommunity();
		
        VBox inputFields = this.createCityInputFields(numberOfCities);
        
        Label pageTitle = new Label("Ajouter des villes");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #3137fd;");
		
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(inputFields);

        Button executeButton = new Button("Entrer");
        executeButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        executeButton.setOnAction(event -> this.addCities(inputFields));
        
        Button goBackButton = new Button("Précedent");
        goBackButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        goBackButton.setOnAction(e -> this.switchStartLayout());

        VBox layoutWithButton = new VBox(10);
        layoutWithButton.getChildren().addAll(pageTitle, scrollPane, executeButton, goBackButton);
        layoutWithButton.setAlignment(Pos.CENTER);
        layoutWithButton.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        setSpacing(30);
        this.getChildren().add(layoutWithButton);

    }
	
	/**
	 * Méthode pour passer à la landing page
	 */
	private void switchStartLayout() {
		this.starter = new StartLayout(this.primaryStage);
		this.primaryStage.setScene(new Scene(this.starter));
	}
	
	/**
	 * Méthode pour passer à la page d'ajout des routes entre les villes
	 */
	private void switchAddRoadslayout() {
		this.roads = new ManualRoadAdditionLayout(this.primaryStage, this.community);
		this.primaryStage.setScene(new Scene(this.roads));
	}

	/**
	 * Méthode pour créer les inputs de textes pour lire les noms des villes
	 * 
	 * @param numberOfCities Nombre de villes à ajouter
	 * @return La liste des conteneur des inputs pour lire les noms des villes
	 */
    private VBox createCityInputFields(int numberOfCities) {
        VBox inputFields = new VBox(10);
        inputFields.setPadding(new Insets(10));

        for (int i = 0; i < numberOfCities; i++) {
            HBox cityInput = new HBox(10);
            Label label = new Label("Nom de la ville " + (i + 1) + " : ");
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
            TextField textField = new TextField();
            textField.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            cityInput.getChildren().addAll(label, textField);
            inputFields.getChildren().add(cityInput);
        }
        return inputFields;
    }
    
    /**
     * Méthode pour créer les villes à partir des valeurs des inputs de l'interface
     * 
     * @param inputFields La liste des conteneur des inputs pour lire les noms des villes
     */
	private void addCities(VBox inputFields) {
		boolean nextPage = true;
        for (Node node : inputFields.getChildren()) { // Passer par tous les noeuds (itextInputs)
            if (node instanceof HBox) { // Chaque noeud représente une HBox (Label + TextInput)
                HBox cityInput = (HBox)node;
                TextField textField = (TextField)cityInput.getChildren().get(1);
                String cityName = textField.getText();
                if (!cityName.isEmpty()) {
                    if (!this.community.hasCity(cityName)) {
                        City city = new City(cityName);
                        this.community.addCity(city);
                    } else { // traiter le cas des duplicats
                    	this.community = new UrbanCommunity();
                    	nextPage = false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Attention!");
                        alert.setHeaderText(null);
                        alert.setContentText("La ville " + cityName + " est dupliquée!");
                        alert.showAndWait();
                        break;
                    }
                }
            }
        }
        if(nextPage) {
        	this.switchAddRoadslayout();
        }
    }
}
