package up.mi.skdh.frontend.layouts;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartLayout extends VBox {
	private Stage primaryStage;
	private FilePathReadingLayout fileReader;
	
	public StartLayout(Stage primaryStage) {
		this.primaryStage = primaryStage;
		String imagePath = "/iLink_transparent_background.png";
		Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView logo = new ImageView(image);

        Label welcomeLabel = new Label("Bienvenu à iLink!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
        
        Label descriptionLabel = new Label("Choisir l'une des méthodes suivantes");
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        Button loadFromFileButton = new Button("Charger d'un fichier");
        loadFromFileButton.setOnAction(e -> this.switchFilePathReadingLayout());

        Button manualLoadButton = new Button("Charger manuellement");
        manualLoadButton.setOnAction(e -> this.switchManualReadingLayout());
        
        loadFromFileButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        manualLoadButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox buttonsContainer = new HBox(150);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(loadFromFileButton, manualLoadButton);

        setSpacing(20);
        setAlignment(Pos.CENTER);
        getChildren().addAll(logo, welcomeLabel, descriptionLabel, buttonsContainer);
        this.setPadding(new Insets(10));
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
    }
	
	private void switchFilePathReadingLayout() {
		fileReader = new FilePathReadingLayout(this.primaryStage);
		this.primaryStage.setScene(new Scene(this.fileReader));
	}
	
	private void switchManualReadingLayout() {
		TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Chargement manuel");
        dialog.setHeaderText(null);
        dialog.setContentText("Introduire le nombre de villes : ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cities -> {
            try {
            	int numberOfCities = Integer.parseInt(cities);
                if (numberOfCities > 0) {
                	ManualCitiesLayout manualReader = new ManualCitiesLayout(this.primaryStage, numberOfCities);
                    this.primaryStage.setScene(new Scene(manualReader));
                } else {
                    // Show an error message for non-positive input
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Merci d'introduire un nombre valide (entier positif)!");
                    alert.showAndWait();
                }
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
	}
}
