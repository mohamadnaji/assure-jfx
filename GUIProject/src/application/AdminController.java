package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import _data_type.Agent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController implements Initializable {

	public ObservableList<Agent> list = FXCollections.observableArrayList();

	@FXML
	private Label welcome;

	@FXML
	private Button AddAgent;

	@FXML
	private ImageView adminimage;

	@FXML
	private Label adminname;

	@FXML
	private AnchorPane in;
	@FXML
	private Button DeleteAgent;

	@FXML
	private Button LoadAgent;

	@FXML
	private TableColumn<Agent, String> Pass;

	@FXML
	private Label adminid;

	@FXML
	private TableView<Agent> AgentTable;

	@FXML
	private Button deleteagent;

	@FXML
	private TableColumn<Agent, Integer> AgentID;

	@FXML
	private TableColumn<Agent, String> AgentName;

	@FXML
	private TableColumn<Agent, String> AgentLastName;

	@FXML
	private TableColumn<Agent, String> Mail;

	@FXML
	private TextField error;
    @FXML
    private TextField search;


	public void load(ActionEvent event) {
		try {
			list.clear();
			Connection con = DBConnection.connect();
			Statement m_Statement = con.createStatement();
			String query = "SELECT * FROM Agent";
			ResultSet m_ResultSet = m_Statement.executeQuery(query);
			while (m_ResultSet.next()) {
				Agent t = new Agent(m_ResultSet.getInt(1), m_ResultSet.getString(2), m_ResultSet.getString(3),
						m_ResultSet.getString(4), m_ResultSet.getString(5));
				list.add(t);
				// System.out.println(t);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), e1, stage);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AgentID.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("id"));
		AgentName.setCellValueFactory(new PropertyValueFactory<Agent, String>("Name"));
		AgentLastName.setCellValueFactory(new PropertyValueFactory<Agent, String>("LastName"));
		Mail.setCellValueFactory(new PropertyValueFactory<Agent, String>("Mail"));
		Pass.setCellValueFactory(new PropertyValueFactory<Agent, String>("Pass"));
		AgentTable.setItems(list);
		load(null);
		try {
			load(null);
		} catch (Exception e1) {
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), e1, stage);
		}
	}

	@FXML
	public void AdminEditAgent(ActionEvent event) {
		try {
			Agent ag = AgentTable.getSelectionModel().getSelectedItem();
			if (ag == null) {
				error.setText("Please Select an Agent from the Table");
				AlertController.alert("Please Select an Agent from the Table","Edit Error");
			} else {
	
				son();
				AdminEditAgentController.setAg(ag);
				Scene scene = new SceneFactory().GetRecentScene("AdminEditAgent.fxml");
				Main.SwitchScene(scene);
				AdminEditAgentController t = (AdminEditAgentController) scene.getUserData();
				t.init();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), e1, stage);
		}
	}

	@FXML
	public void deleteAgent() {
		try {
			PreparedStatement prepare = null;
			Agent ag = AgentTable.getSelectionModel().getSelectedItem();
			if (ag == null) {
				error.setText("Please Select an Agent from the Table");
				AlertController.alert("Please Select an Agent from the Table","Delete Error");
			} else {
				Connection con = DBConnection.connect();
				String query = "delete from Agent where ID=? ";
				prepare = con.prepareStatement(query);
				prepare.setInt(1, AgentTable.getSelectionModel().getSelectedItem().getId());
				if (!prepare.execute())
					AlertController.alert1("Agent Deleted");
				else
					AlertController.alert1("Make sure no contrat with this agent");
				prepare.close();
				load(null);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void gotoAddAgent(ActionEvent event) throws IOException {
		son();
		Main.SwitchScene(new SceneFactory().GetRecentScene("AddAgent.fxml"));
	}

	public void ChangeNameID(String name, String id) {
		adminname.setText(name);
		adminid.setText(id);
	}

	@FXML
	public void gotoStart(ActionEvent event) throws IOException {
		// Main.SwitchScene(new SceneFactory().GetRecentScene("Start.fxml"));
		Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
	}

	@FXML
	public void gotoAddcp(ActionEvent event) throws IOException {
		son();
		Scene scene = new SceneFactory().GetRecentScene("AdminAddCompany.fxml");
		Main.SwitchScene(scene);
		AdminAddCompany t = (AdminAddCompany) scene.getUserData();
		t.ResetHelper();
	}
	
	@FXML
	public void search() {
			String s=search.getText().trim().toLowerCase();
			ObservableList<Agent> listcopy = FXCollections.observableArrayList();
			load(null);
			if(s.equals(" ")|| s.isEmpty())
				{
				load(null);
				}
			else
			{
				for(Agent ag: list)
				{
					if(ag.getId().toString().toLowerCase().contains(s)|| ag.getName().toLowerCase().contains(s)||ag.getLastName().toLowerCase().contains(s)||ag.getMail().toLowerCase().contains(s)||ag.getPass().toLowerCase().contains(s))
					{
						listcopy.add(ag);
					}
				}
				list.clear();
				for(Agent c:listcopy)
					list.add(c);
			}
			
	}
	
	public void son() {
		SceneFactory.getBackButton().add("Admin.fxml");
	}
}
