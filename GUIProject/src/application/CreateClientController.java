package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateClientController {

	@FXML
	private Button add;
	@FXML
	private AnchorPane in;

	@FXML
	private TextField Tel;

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

	// change this function and add a new client to the DB then affect clid by his
	// id asking another query for max id
	// see like company add
	private String clid;
	private boolean addreturn;

	public void AddClient() {
		Connection con = DBConnection.connect();
		PreparedStatement prepare = null;
		String name = AgentName.getText();
		String lastname = LastName.getText();
		String tel = Tel.getText();
		try {
			tel=""+Integer.parseInt(tel);
		} catch (Exception e) {
			// TODO: handle exception
			addagentconfirm.setText("Input Error !");
			AlertController.alert("Tel must be a number","Input Error!");
			return;
		}
		String query = "Insert Into Client(Name,Last,Tel) Values(?,?,?)";
		try {
			if (!name.isEmpty() && !lastname.isEmpty() && !tel.isEmpty() ) {
				prepare = con.prepareStatement(query);
				prepare.setString(1, name);
				prepare.setString(2, lastname);
				prepare.setString(3, tel);
				prepare.execute();
				addagentconfirm.setText("Client Added !");
				AlertController.alert1("Client Added !");
				prepare.close();
			} else {
				addagentconfirm.setText("Input Error !");
				AlertController.alert("Input Error","Error");
			}
			////// here affectation with the new added client

			if (addreturn) {
			//	clid = "24"; // example for testing
				ResultSet m_ResultSet = null;
				query="SELECT  TOP 1 [ID] FROM [Project_Prototype].[dbo].[Client] ORDER BY ID DESC";
				Statement m_Statement = con.createStatement();	
				m_ResultSet = m_Statement.executeQuery(query);
				clid=m_ResultSet.getString(1);
				submit(null);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);

		}

	}

	@FXML
	void back(ActionEvent event) {
		// we can also to main agent page
		String root = SceneFactory.getBackButton().pop();
		Scene scene;
		try {
			scene = new SceneFactory().GetRecentScene(root);
			Main.SwitchScene(scene);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	void submit(ActionEvent event) {
		// we are going to agentmakeocontrat controller :

		try {
			String root = SceneFactory.getBackButton().pop();
			Scene scene = new SceneFactory().GetRecentScene(root);
			Main.SwitchScene(scene);
			AgentMakeContratController t = (AgentMakeContratController) scene.getUserData();
			t.setClID(clid);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}

	}

	@FXML
	void clearall(ActionEvent event) {
		AgentName.clear();
		LastName.clear();
		Tel.clear();
		Pass.clear();
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public boolean isAddreturn() {
		return addreturn;
	}

	public void setAddreturn(boolean addreturn) {
		// Change button add to submit and viceverca
		this.addreturn = addreturn;
		if (this.addreturn)
			add.setText("Submit");
		else
			add.setText("ADD");
	}
}
