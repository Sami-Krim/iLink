package up.mi.skdh.frontend;

import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import up.mi.skdh.frontend.layouts.StartLayout;

public class MainApp extends Application {
	private Stage primaryStage;
	private StartLayout start;
	
	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.start = new StartLayout(primaryStage);
        this.primaryStage.setTitle("iLink");
        

        // Set the initial scene with Layout 1
        Scene scene = new Scene(this.start);
        scene.setFill(Color.WHITE);
        this.primaryStage.setWidth(750);
        this.primaryStage.setHeight(700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
        launch(args);
    }

}
