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
	private Stage primaryStage;
	private StartLayout starter;
	private UrbanCommunity community;
	
	public ManualCitiesLayout(Stage primaryStage, int numberOfCities) {
		this.primaryStage = primaryStage;
		this.community = new UrbanCommunity();
		
        VBox inputFields = this.createCityInputFields(numberOfCities);
        
        Label pageTitle = new Label("File Path Reader");
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
        setSpacing(30);
        this.getChildren().add(layoutWithButton);

    }
	
	private void switchStartLayout() {
		starter = new StartLayout(this.primaryStage);
		this.primaryStage.setScene(new Scene(this.starter));
	}

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
    
	private void addCities(VBox inputFields) {
        for (Node node : inputFields.getChildren()) {
            if (node instanceof HBox) {
                HBox cityInput = (HBox)node;
                TextField textField = (TextField)cityInput.getChildren().get(1);
                String cityName = textField.getText();
                if (!cityName.isEmpty()) {
                    if (!this.community.hasCity(cityName)) {
                        City city = new City(cityName);
                        this.community.addCity(city);
                    } else {
                    	this.community = new UrbanCommunity();
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
    }
}
