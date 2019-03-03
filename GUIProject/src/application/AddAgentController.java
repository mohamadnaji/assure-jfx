package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddAgentController {

	@FXML
	private Button add;

	@FXML
	private TextField Mail;

	@FXML
	private TextField Pass;

	@FXML
	private ImageView userimage;

	@FXML
	private Button clear;

	@FXML
	private Button back;

	@FXML
	private TextField AgentName;

	@FXML
	private TextField LastName;

	@FXML
	private Label addcagentconfirm;

	@FXML
	private Label addagentconfirm;

	@FXML
	private AnchorPane in;

	public void AddAgent() {

		Connection con = DBConnection.connect();
		PreparedStatement prepare = null;
		String name = AgentName.getText();
		String lastname = LastName.getText();
		String mail = Mail.getText();
		String pass = Pass.getText();
		String query = "Insert Into Agent(Name,Last,Mail,Password) Values(?,?,?,?)";

		if (!name.isEmpty() && !lastname.isEmpty() && !mail.isEmpty() && !pass.isEmpty()) {

			try {
				prepare = con.prepareStatement(query);
				prepare.setString(1, name);
				prepare.setString(2, lastname);
				prepare.setString(3, mail);
				prepare.setString(4, pass);
				prepare.execute();
				prepare.close();
				addagentconfirm.setText("Agent Added !");
				AlertController.alert1("Agent Added !");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			//	e1.printStackTrace();
				Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
				AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);

			}
		} else {
			addagentconfirm.setText("Input Error !");
			AlertController.alert("Input Error !","Error");
		}

	}

	@FXML
	void backtoAdmin(ActionEvent event) throws IOException {
		// Main.SwitchScene(new SceneFactory().GetRecentScene("Admin.fxml"));
		Scene scene=new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop());
		AdminController t=(AdminController)scene.getUserData();
		t.load(null);
		Main.SwitchScene(scene);
	}

	@FXML
	void clearall(ActionEvent event) throws IOException {
		AgentName.clear();
		LastName.clear();
		Mail.clear();
		Pass.clear();
	}
}
