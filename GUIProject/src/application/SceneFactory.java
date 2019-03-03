package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneFactory {
	private static String srcfxml = "/fxml/";// ="/fxml/";
	private static String srccss = "/css/";// ="/css/";

	private static Map<String, Scene> AIOScene = new HashMap<String, Scene>();
	private static Stack<String> BackButton = new Stack<String>();

	// in this Queue add the path of parent when calling the son
	// then when we want to back from the son we pop that queue..
	public int TotalObjectsCreated() {
		return AIOScene.size();
	}

	// flyweight pattern :
	public Scene GetRecentScene(String Path) {
		Scene scene = null;
		try {
			if (AIOScene.containsKey(Path)) {
				 System.out.println("i use a recent scene called "+ Path);
				scene = AIOScene.get(Path);
			} else {
				 System.out.println("i put a new scene with path : "+ Path);
				scene = getScene(Path);
				AIOScene.put(Path, scene);
			}

		} catch (Exception e1) {

		}
		return scene;
	}

	/// if we want a new scene or if we want we make a clear form before we want
	/// also put a buttonn clear
	public Scene getScene(String Path) throws IOException {

		// Path if we want to change Package to fxml
		// Path="/fxml/"+Path;
		// old root build :
		// Parent root = FXMLLoader.load(getClass().getResource(src + Path));
		// new after saving controller in scene user data:
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(srcfxml + Path));
		loader.load();
		Parent root = loader.getRoot();
		// now we have controller in loader.getController
		Scene scene = new Scene(root);
		scene.setUserData(loader.getController());
		/* now if we want to change data between scene we just call for the scene then
		 * get it's controller >>> cast it to the specific class >> then call the
		 * function and send withing parameters to affect it's private variables 
		 add diffrent source for css root folder //
		 change souve into package css do
		 System.out.println(Path);
		 */
		scene.getStylesheets().add(getClass().getResource(srccss + Path.substring(0, Path.length() - 5) + ".css").toExternalForm());

		return scene;
	}

	public static Stack<String> getBackButton() {
		return BackButton;
	}

	public static void setBackButton(Stack<String> backButton) {
		BackButton = backButton;
	}

}
