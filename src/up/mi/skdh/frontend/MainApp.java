package up.mi.skdh.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import up.mi.skdh.frontend.layouts.StartLayout;

/** 
 * Classe principale de l'application iLink.
 * Cette classe étend la classe "Application" de JavaFX et définit le point d'entrée de l'application
 *  
 * @author Sami KRIM
 * @author Daniel HUANG
 *
 */
public class MainApp extends Application {
	/**
	 *  Le stage principale de l'application
	 */
	private Stage primaryStage;
	/**
	 * La Landing page
	 */
	private StartLayout start;
	
	/**
	 * Méthode de lancement de l'application
	 * 
	 * @param primaryStage Le stage courrant de la page
	 */
	@Override
    public void start(Stage primaryStage) {
		// Chargement du logo de l'application
		Image icon = new Image("/iLink_transparent_background.png");
        primaryStage.getIcons().add(icon);
        
        //Initialisation des attributs
        this.primaryStage = primaryStage;
        this.start = new StartLayout(primaryStage);
        this.primaryStage.setTitle("iLink");
        
        Scene scene = new Scene(this.start);
        this.primaryStage.setWidth(750);
        this.primaryStage.setHeight(700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}