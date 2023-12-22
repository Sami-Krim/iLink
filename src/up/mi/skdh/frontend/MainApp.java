package up.mi.skdh.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import up.mi.skdh.frontend.layouts.StartLayout;
/** Classe principale de l'application JavaFX.
 *  cette classe ¨¦tend la classe Application de JavaFX et d¨¦finit le point d'entr¨¦e de l'application
 *  
 * @author Sami KRIM
 * @author Daniel HUANG
 *
 */
public class MainApp extends Application {
	// **************************************************
    // Attributs
    // **************************************************
	/**
	 *  Stage repr¨¦sente la fen¨ºtre principale de l'application
	 */
	private Stage primaryStage;
	/**
	 * StartLayount est la mise en page initiale de l'application
	 */
	private StartLayout start;
	
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