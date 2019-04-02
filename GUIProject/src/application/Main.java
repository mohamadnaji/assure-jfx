
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	private static Stage mystage;
	private static int i = 0;

	@Override
	public void start(Stage primaryStage) {
		mystage = primaryStage;
		mystage.setTitle("Inssurance System");
		// just performed a change notice  A > next to file in eclipse
		mystage.getIcons().add(new Image("/img/icon-quality-assurance.png"));
		mystage.setResizable(false);
		// System.out.println("im in and stage is "+mystage);
		try {
			init();
		} catch (Exception e1) {
			
			Stage stage = (Stage) mystage.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public static void start2(Stage primaryStage, Scene scene) {
		try {
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e1) {
			// e.printStackTrace();
			Stage stage = (Stage) mystage.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public static void SwitchScene(Scene scene) {
		start2(mystage, scene);
	}

	public void init() {
		// Parent root=FXMLLoader.load(getClass().getResource("/fxml/testy.fxml"));
		// Parent root=FXMLLoader.load(getClass().getResource("testy.fxml"));
		// Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		// System.out.println("scene is "+scene);
		// System.out.println("sttage is q"+ mystage);
		try {
			if (i != 0) {
				SceneFactory.getBackButton().add("Start.fxml");
				start2(mystage, new SceneFactory().GetRecentScene("Start.fxml"));
			}
			i++;
		} catch (Exception e1) {
			Stage stage = (Stage) mystage.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public static void main(String[] args) {
	
		launch(args);
		
	}

	public Stage getStage() {
		return mystage;
	}
}
