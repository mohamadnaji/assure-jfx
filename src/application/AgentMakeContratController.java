package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import _data_type.Clients;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AgentMakeContratController implements Initializable {
	private static Clients cl;
	@FXML
	private Button add;

	@FXML
	private DatePicker sdate;

	@FXML
	private TextField ITID;

	@FXML
	private AnchorPane in;

	@FXML
	private Button clear;

	@FXML
	private Button back;

	@FXML
	private Label addcagentconfirm;

	@FXML
	private DatePicker edate;

	@FXML
	private Button getitid;

	@FXML
	private Button getexist;

	@FXML
	private TextField price;

	@FXML
	private Button getclid;

	@FXML
	private TextField CPID;

	@FXML
	private TextField CLID;

	@FXML
	private Label addagentconfirm;

	@FXML
	private TextField desc;

	public void initialize(URL location, ResourceBundle resources) {
		//getexist.setVisible(false);
		//CLID.setDisable(true);
//		CLID.setEditable(false);
//		CLID.setMouseTransparent(true);
//		CLID.setFocusTraversable(false);
//		CPID.setEditable(false);
//		CPID.setMouseTransparent(true);
//		CPID.setFocusTraversable(false);
//		ITID.setEditable(false);
//		ITID.setMouseTransparent(true);
//		ITID.setFocusTraversable(false);
//	ITID.setDisable(true);
	}

	public void AddContrat() {
		try {
			String agid = AgentController.getAgid();
			Connection con = DBConnection.connect();
			LocalDate ed = edate.getValue();
			LocalDate sd = sdate.getValue();
			PreparedStatement prepare = null;
			// String agent = AGID.getText();
			String client = CLID.getText();
			String company = CPID.getText();
			String insurancetype = ITID.getText();
			Date dates = java.sql.Date.valueOf(sd);
			Date datee = java.sql.Date.valueOf(ed);
			String prix = price.getText();
			String Description = desc.getText();
			String query = "Insert Into contrats(AGID,CLID,CPID,ITID,[Start date],[End date],Price,Description) Values(?,?,?,?,?,?,?,?)";
			prepare = con.prepareStatement(query);
	
			if (!client.isEmpty() && !company.isEmpty() && !prix.isEmpty() && !insurancetype.isEmpty()) {
				prepare.setString(1, agid);
				prepare.setString(2, client);
				prepare.setString(3, company);
				prepare.setString(4, insurancetype);
				prepare.setDate(5, dates);
				prepare.setDate(6, datee);
				prepare.setString(7, prix);
				if (Description.isEmpty()) {
					Description = "";
				}
				prepare.setString(8, Description);
				if(ed.isBefore(sd)) {
					addagentconfirm.setText("Input Date Error !");
					AlertController.alert("Input Date Error !","Input Error");		
				}
				else {
				prepare.execute();
				addagentconfirm.setText("Contrat Added !");
				AlertController.alert1("Contrat Added !");
				}
				prepare.close();
			} else {
				addagentconfirm.setText("Input Error !");
				AlertController.alert1("Input Error !");
			}
		} catch (Exception e1) {
			Stage stage = (Stage) in.getScene().getWindow(); 
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}

	}

	@FXML
	void clearall(ActionEvent event) {
		ClearCLID();
		ClearCPITID();
		CLID.clear();
		CPID.clear();
		ITID.clear();
	}

	@FXML
	public void DefineNewCLID(ActionEvent event) {

		son();
		Scene scene;
		scene = new SceneFactory().GetRecentScene("CreateClient.fxml");
		Main.SwitchScene(scene);
		CreateClientController t = (CreateClientController) scene.getUserData();
		t.setAddreturn(true);
	}

	public static Clients getCl() {
		return cl;
	}

	// in agent make contrat to showclients there is a submit keys ! when get back
	public static void setCl(Clients cl) {
		AgentMakeContratController.cl = cl;
	}

	@FXML
	public void gotoShowClients(ActionEvent event) {
		try {
			clearall(null);
			Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
		} catch (Exception e1) {

			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);

		}

	}

	@FXML
	public void Getexistid(ActionEvent event) {
		// son(); this case 1 loop because showclient do not use back to access this
		// page ! i'm the son here

		try {
			Scene scene = new SceneFactory().GetRecentScene("ShowClients.fxml");
			ShowClientsController t = (ShowClientsController) scene.getUserData();
			t.setGetclid(true);
			Main.SwitchScene(scene);
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}

	}

	@FXML
	public void gotoShowCompany(ActionEvent event) {

		try {
			son();
			Scene scene = new SceneFactory().GetRecentScene("ShowCompany.fxml");
			Main.SwitchScene(scene);
			ShowCompanyController t = (ShowCompanyController) scene.getUserData();
			t.setGetreturn(true);
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public void setClID(String id) {
		CLID.setText(id);
		CLID.setDisable(true);
		getclid.setVisible(false);
		getexist.setVisible(false);
	}

	public void ClearCLID() {
		CLID.setDisable(false);
		getclid.setVisible(true);
		getexist.setVisible(true);
	}

	public void setCPITID(String cpid, String itid) {
		CPID.setText(cpid);
		ITID.setText(itid);
		CPID.setDisable(true);
		ITID.setDisable(true);
		getitid.setVisible(false); // the call button
	}

	public void ClearCPITID() {
		CPID.setDisable(false);
		ITID.setDisable(false);
		getitid.setVisible(true);
	}

	void son() {
		SceneFactory.getBackButton().add("AgentMakeContrat.fxml");
	}
}
