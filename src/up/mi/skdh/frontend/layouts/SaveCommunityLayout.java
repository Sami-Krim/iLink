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
 * La mise en page de la page de lecture du chemin du fichier d'enregistrement de la communauté de l'application.
 * 
 * @author Sami KRIM
 * @author Daniel HUANG
 */
public class SaveCommunityLayout extends VBox {
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
	public SaveCommunityLayout(Stage primaryStage, UrbanCommunity community) {
		this.primaryStage = primaryStage;
		this.community = community;
		
		Label pageTitle = new Label("Sauvegarde de la communauté");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #3137fd;");
		TextField filePathInput = new TextField();
        filePathInput.setText("Entrez l'emplacement du fichier");
        Button browseButton = new Button("Parcourir");
        browseButton.setStyle("-fx-background-color: #2c75f1; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        Label result = new Label("");
        Button goBackButton = new Button("Précedent");
        goBackButton.setStyle("-fx-background-color: #3137fd; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox filePathContainer = new HBox(30);
        filePathContainer.setAlignment(Pos.CENTER);     		filePathInput.prefWidthProperty().bind(filePathContainer.widthProperty().multiply(0.75));
        filePathContainer.getChildren().addAll(filePathInput, browseButton);
        
        browseButton.setOnAction(event -> {
        	FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un fichier pour enregistrer");

            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                
                if (!filePath.toLowerCase().endsWith(".txt")) {
                	filePath += ".txt";
                }

                // Save the community and get the resulting file
                File communityFile = FileManager.saveCommunity(this.community, filePath, result);
                if (communityFile != null) {
                    result.setText("La communauté a été sauvegardé : " + filePath);
                    result.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: green;");
                }
            }
        });
        
        goBackButton.setOnAction(e -> this.switchProblemResolutionMenuLayout());
        
        setSpacing(75);
        setAlignment(Pos.CENTER);
        this.getChildren().addAll(pageTitle, filePathContainer, result, goBackButton);
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
