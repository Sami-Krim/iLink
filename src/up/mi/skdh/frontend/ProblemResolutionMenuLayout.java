package up.mi.skdh.frontend;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import up.mi.skdh.backend.City;
import up.mi.skdh.backend.UrbanCommunity;
import up.mi.skdh.exceptions.AccessibilityConstraintNotVerifiedException;

public class ProblemResolutionMenuLayout extends VBox {
	private Stage primaryStage;
	private FilePathReadingLayout fileReader;
	private UrbanCommunity community;
	
	public ProblemResolutionMenuLayout(Stage primaryStage, UrbanCommunity community) {
		this.primaryStage = primaryStage;
		this.community = community;
		Label pageTitle = new Label("Menu principal");
		pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");
		Label communityLabel = new Label("");
		try {
			if(this.community.verifyAccessibilityConstraint()) {
				communityLabel.setText("La communité satisfait la contrainte d'eccessibilité");
				communityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
			}
		} catch (AccessibilityConstraintNotVerifiedException e) {
			communityLabel.setText("La communité ne satisfait pas la contrainte d'eccessibilité. Solution naive utilisée.");
			communityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
			this.community.naiveSolution();
		}
		ListView communityView = DisplayManager.displayCitiesWithNeighbors(this.community);
		
		this.getChildren().addAll(pageTitle, communityLabel, communityView);
	}
}
