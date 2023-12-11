package up.mi.skdh.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StartLayout extends VBox {
	public StartLayout() {
		String imagePath = "/iLink_transparent_background.png";
		Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView logo = new ImageView(image);

        Label welcomeLabel = new Label("Bienvenu à iLink!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
        
        Label descriptionLabel = new Label("Choisir l'une des méthodes suivantes");
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        Button loadFromFileButton = new Button("Charger d'un fichier");
        //loadFromFileButton.setOnAction();

        Button manualLoadButton = new Button("Charger manuellement");
        //manualLoadButton.setOnAction();
        
        loadFromFileButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        manualLoadButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox buttonsContainer = new HBox(150);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(loadFromFileButton, manualLoadButton);

        setSpacing(20);
        setAlignment(Pos.CENTER);
        getChildren().addAll(logo, welcomeLabel, descriptionLabel, buttonsContainer);
        this.setPadding(new Insets(10)); // Add padding if needed
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
    }
}
