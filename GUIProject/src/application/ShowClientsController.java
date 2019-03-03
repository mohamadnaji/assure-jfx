package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import _data_type.Clients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowClientsController implements Initializable {

	public ObservableList<Clients> list = FXCollections.observableArrayList();

	@FXML
	private RadioButton existingclient;

	@FXML
	private Button back;
   
	@FXML
    private Button acsident;

    @FXML
    private TextField search;

	@FXML
	private ToggleGroup gr;

	@FXML
	private TextField error;

	@FXML
	private RadioButton newclients;

	@FXML
	private Button lac;

	@FXML
	private Button loadmyclients;

	@FXML
	private Button NewContract;

	@FXML
	private TableColumn<Clients, Integer> clientid;

	@FXML
	private TableColumn<Clients, String> clientname;

	@FXML
	private TableColumn<Clients, String> clientlastname;

	@FXML
	private TableColumn<Clients, String> telephone;

	@FXML
	private TableView<Clients> clientstable;

	@FXML
	private Label welcome;
	@FXML
	private AnchorPane in;
	private static String agid;

	//// ------------------ LOAD Clients -----------------------///////
	@FXML
	public void loadallClients(ActionEvent event) {
		list.clear();
		Connection con = DBConnection.connect();
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = con.createStatement();
			rs = ps.executeQuery("SELECT * FROM Client");
			while (rs.next()) {
				Clients t = new Clients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(t);
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void loadmyClients(ActionEvent event) {
		list.clear();
		Connection con = DBConnection.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"select c.ID,c.Name,c.Last,c.Tel from Client c, Contrats e where e.AGID=? and c.ID=E.CLID");
			// System.out.println(agid + "je suis agid");
			ps.setString(1, agid);
			rs = ps.executeQuery();
			while (rs.next()) {
//////////////////////////we can create fleiweight pattern for client ymknnnnnnn
				
				//////////////
				//////////////
				////////////
				////////////// see this
				Clients t = new Clients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(t);
				//System.out.println(t);
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void search() {
			String s=search.getText().trim().toLowerCase();
			ObservableList<Clients> listcopy = FXCollections.observableArrayList();
			loadallClients(null);
			if(s.equals(" ")|| s.isEmpty())
				{
					loadallClients(null);
				}
			else
			{
				for(Clients client: list)
				{
					
					if(client.getId().toString().toLowerCase().contains(s)|| client.getName().toLowerCase().contains(s)||client.getLastName().toLowerCase().contains(s)||client.getTelephone().toLowerCase().contains(s))
					{
						listcopy.add(client);
					}
				}
				list.clear();
				for(Clients c:listcopy)
					list.add(c);
			}
			
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientid.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("Id"));
		clientname.setCellValueFactory(new PropertyValueFactory<Clients, String>("Name"));
		clientlastname.setCellValueFactory(new PropertyValueFactory<Clients, String>("LastName"));
		telephone.setCellValueFactory(new PropertyValueFactory<Clients, String>("Telephone"));
		clientstable.setItems(list);
		newclients.setSelected(true);
		clientid.setSortable(true);
		loadallClients(null);
	}

	public static String getAgid() {
		return agid;
	}

	public static void setAgid(String agid) {
		ShowClientsController.agid = agid;
	}

	private boolean getclid;

	@FXML
	public void gotoMakeContrat(ActionEvent event) {
		try {
			if (existingclient.isSelected()) {
				Clients ag = clientstable.getSelectionModel().getSelectedItem();
				if (ag == null)
					error.setText("Please Select a Client from the Table");
				else {
					// System.out.println(ag);
					// create a copy agent
					AgentMakeContratController.setCl(ag);
					son();
					Scene scene = new SceneFactory().GetRecentScene("AgentMakeContrat.fxml");

					// SceneFactory.getBackButton().add("ShowClients.fxml");

					AgentMakeContratController t = (AgentMakeContratController) scene.getUserData();
					t.setClID(ag.getId() + "");
					if (!getclid)
						t.ClearCPITID();

					setGetclid(false);
					Main.SwitchScene(scene);

				}
			} else {
				// here we have a button to create a new client later test if null to show a
				// button to create new client
				// and show buttun using some method from here .. and hide it in up
				son();
				AgentMakeContratController.setCl(null);
				Scene scene = new SceneFactory().GetRecentScene("AgentMakeContrat.fxml");
				AgentMakeContratController t = (AgentMakeContratController) scene.getUserData();
				t.ClearCLID();
				t.ClearCPITID();
				setGetclid(false);
				Main.SwitchScene(scene);
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	

	@FXML
	public void gotoAgent(ActionEvent event) {
		// Main.SwitchScene(new SceneFactory().GetRecentScene("Agent.fxml"));
		try {
			Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
			if (getclid)
				setGetclid(false);
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	void son() {
		SceneFactory.getBackButton().add("ShowClients.fxml");
	}

	public boolean isGetclid() {
		return getclid;
	}

	
	public void setGetclid(boolean getclid) {
		this.getclid = getclid;
		//System.out.println("i enter setgetclid with getclid = " + this.getclid);
		if (this.getclid) {
			existingclient.setSelected(true);
			existingclient.setDisable(true);
			newclients.setDisable(true);
			NewContract.setText("SUBMIT");
		} else {
			//System.out.println("i never enter here ");
			existingclient.setSelected(false);
			existingclient.setDisable(false);
			newclients.setDisable(false);
			NewContract.setText("NEW CONTRACT");
		}
	}
	
	
	@FXML
	public void gotoinsertIncident(ActionEvent event) {
		
		try {
			son();
			Main.SwitchScene(new SceneFactory().GetRecentScene("InsertIncident.fxml"));
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); 
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

}
