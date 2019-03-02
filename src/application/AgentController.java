package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AgentController implements Initializable {

	@FXML
	private Label mssg1;

	@FXML
	private Label firstname;

	@FXML
	private Label userId;

	@FXML
	private Label welcome;

	@FXML
	private Label email;

	@FXML
	private Label lastname;

	@FXML
	private AnchorPane in;

	private static String agid;

	public void load(ActionEvent event) {
		try {
			Connection con = DBConnection.connect();
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement("SELECT * FROM Agent WHERE ID = ?");
			ps.setString(1, agid);
			rs = ps.executeQuery();

			while (rs.next()) {
				userId.setText(rs.getString(1));
				firstname.setText(rs.getString(2));
				lastname.setText(rs.getString(3));
				email.setText(rs.getString(4));
			}
			rs.close();
			con.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		load(null);
	}

	public static String getAgid() {
		return agid;
	}

	public static void setAgid(String agid) {
		AgentController.agid = agid;
	}

	@FXML
	public void login() {
		try {
			son();
			Scene scene = new SceneFactory().GetRecentScene("ShowClients.fxml");

			Main.SwitchScene(scene);
		} catch (Exception e1) {

			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
		ShowClientsController.setAgid(agid);
	}

	@FXML
	public void gotoShowCompany() {
		son();
		try {
			Main.SwitchScene(new SceneFactory().GetRecentScene("ShowCompany.fxml"));
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void gotoPay() {

		try {
			son();
			Main.SwitchScene(new SceneFactory().GetRecentScene("PayCollection.fxml"));
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void CreateClient() {

		try {
			son();
			Main.SwitchScene(new SceneFactory().GetRecentScene("CreateClient.fxml"));
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void MakeContrat() {

		try {
			son();
			AgentMakeContratController.setCl(null);
			Scene scene = new SceneFactory().GetRecentScene("AgentMakeContrat.fxml");
			Main.SwitchScene(scene);
			AgentMakeContratController t = (AgentMakeContratController) scene.getUserData();
			t.ClearCLID();
			t.ClearCPITID();
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}

	}

	@FXML
	public void back() {

		try {
			Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	void son() {
		SceneFactory.getBackButton().add("Agent.fxml");
	}

}
