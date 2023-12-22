package up.mi.skdh.frontend.layouts;



import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.frontend.managers.FileManager;

/**
 * La mise en page de la page de lecture du chemin du fichier de chargement de la communauté de l'application.
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */
public class FilePathReadingLayout extends VBox {
	/**
	 * Le stage auquel la page est liée
	 */
	private Stage primaryStage;
	/**
	 * La Landing page
	 */
	private StartLayout starter;
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
	 */
	public FilePathReadingLayout(Stage primaryStage) {
		this.community = new UrbanCommunity();
		this.primaryStage = primaryStage;
		Label pageTitle = new Label("Emplacement du fichier");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #3137fd;");
		TextField filePathInput = new TextField();
        filePathInput.setText("Entrez le chemin au fichier");
        Button browseButton = new Button("Parcourir");
        browseButton.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
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
                this.community = FileManager.loadUrbanCommunity(filePath, resultReading);
                if(this.community != null) nextButton.setOnAction(e -> this.switchProblemResolutionMenuLayout());
            }
        });
        
        goBackButton.setOnAction(e -> this.switchStartLayout());
        
        setSpacing(75);
        setAlignment(Pos.CENTER);
        this.getChildren().addAll(pageTitle, filePathContainer, resultReading, navigationButtonsContainer);
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        this.setBackground(background);
	}
	
	/**
	 * Méthode pour passer à la landing page
	 */
	private void switchStartLayout() {
		starter = new StartLayout(this.primaryStage);
		this.primaryStage.setScene(new Scene(this.starter));
	}
	
	/**
	 * Méthode pour passer à la page de résolution du problème
	 */
	private void switchProblemResolutionMenuLayout() {
		this.resolutionMenu = new ProblemResolutionMenuLayout(this.primaryStage, this.community);
		this.primaryStage.setScene(new Scene(this.resolutionMenu));
	}
}
