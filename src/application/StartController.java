package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartController implements Initializable {

	@FXML
	private RadioButton userrb;

	@FXML
	private AnchorPane in;

	@FXML
	private PasswordField passwordtf;

	@FXML
	private ImageView image;
	@FXML
	private Label mssg;

	@FXML
	private ToggleGroup UserOrAdmin;

	@FXML
	private RadioButton adminrb;

	@FXML
	private TextField useridtf;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		clear();
	}

	@FXML
	public void sttext() {

	}

	@FXML
	private void Login(ActionEvent event) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection con = DBConnection.connect();
			if (userrb.isSelected()) {

				ps = con.prepareStatement("SELECT * FROM Agent WHERE ID = ? and Password = ?");
				ps.setString(1, useridtf.getText());
				ps.setString(2, passwordtf.getText());
				rs = ps.executeQuery();

				if (rs.next()) {

					// here we go to the next agent page...
					AgentController.setAgid(useridtf.getText());
					son();
					Scene scene = new SceneFactory().GetRecentScene("Agent.fxml");
					Main.SwitchScene(scene);
					AgentController t = (AgentController) scene.getUserData();
					t.load(null);
					mssg.setText("");
					clear();

				}

				else {

					mssg.setText("Wrong Password Or AgentID");
					AlertController.alert("Wrong Password Or AgentID","Sign in Error");

				}

			}

			else {
				if (adminrb.isSelected()) {

					ps = con.prepareStatement("SELECT * FROM admin WHERE id = ? and password = ?");
					ps.setString(1, useridtf.getText());
					ps.setString(2, passwordtf.getText());
					rs = ps.executeQuery();
				}

				if (rs.next()) {

					// System.out.println("i' going to admin");
					Scene scene;

					scene = new SceneFactory().GetRecentScene("Admin.fxml");
					Main.SwitchScene(scene);
					son();
					mssg.setText("");
					AdminController t = (AdminController) scene.getUserData();
					t.ChangeNameID(rs.getString(2), useridtf.getText());
					clear();

				} else {
					mssg.setText("Wrong Password Or AdminID");
					AlertController.alert("Wrong Password Or AdminID","Input Error");
				}
				ps.close();
				rs.close();
				con.close();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), e1, stage);
		}

	}

	public void clear() {
		passwordtf.clear();
		useridtf.clear();
	}

	@FXML
	public void exitt() {
		Platform.exit();
	}

	public void son() {
		SceneFactory.getBackButton().add("Start.fxml");
	}

}
