package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import _data_type.Clients;
import _data_type.Company;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowCompanyController implements Initializable {

	public ObservableList<Company> list = FXCollections.observableArrayList();

    @FXML
    private TextField search;

	
	@FXML
	private TableColumn<Company, Integer> id;

	@FXML
	private Button loadmycompany;

	@FXML
	private TableColumn<Company, String> name;

	@FXML
	private Button back;

	@FXML
	private TableColumn<Company, String> address;

	@FXML
	private TableColumn<Company, Integer> insuranceid;

	@FXML
	private TableColumn<Company, String> it;

	@FXML
	private TableColumn<Company, String> fax;

	@FXML
	private TableColumn<Company, Double> ratio;
	@FXML
	private TextField error;

	@FXML
	private Label welcome;

	@FXML
	private TableView<Company> companytable;

	@FXML
	private Button loadallcompany;
	@FXML
	private AnchorPane in;

	private static String agid;
	private boolean getreturn = false;

	//// ------------------ LOAD Clients -----------------------///////
	@FXML
	public void loadallCompany(ActionEvent event) {
		list.clear();
		Connection con = DBConnection.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"select c.id,c.Name,c.Location,c.Fax,i.ID,i.Code,i.Ratio from Company c, InsuranceTypes i,CPhaveIT ci where ci.CPID=c.ID and ci.ITID=i.ID ");
			rs = ps.executeQuery();
			while (rs.next()) {
				Company t = new Company(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getDouble(7));
				list.add(t);
				// System.out.println(t);
			}
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.setCellValueFactory(new PropertyValueFactory<Company, Integer>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));
		address.setCellValueFactory(new PropertyValueFactory<Company, String>("address"));
		fax.setCellValueFactory(new PropertyValueFactory<Company, String>("fax"));
		insuranceid.setCellValueFactory(new PropertyValueFactory<Company, Integer>("insuranceid"));
		it.setCellValueFactory(new PropertyValueFactory<Company, String>("code"));
		ratio.setCellValueFactory(new PropertyValueFactory<Company, Double>("ratio"));
		companytable.setItems(list);
		loadallCompany(null);
	}

	public static String getAgid() {
		return agid;
	}

	@FXML
	public void gotoAgent(ActionEvent event) {
		// Main.SwitchScene(new SceneFactory().GetRecentScene("Agent.fxml"));
		// go back we have 2 case etheir to return a value to AgentMakeContrat or just
		// back to previous page:
		try {
			Scene scene = new SceneFactory().GetRecentScene(SceneFactory.getBackButton().peek());

			if (getreturn) {
				Company cp = companytable.getSelectionModel().getSelectedItem();
				if (cp == null) {
					error.setText("Please Select a Contrat type from the Table");
					AlertController.alert("Please Select a Contrat type from the Table","Error");
				}

				else {
					AgentMakeContratController t = (AgentMakeContratController) scene.getUserData();
					t.setCPITID(cp.getId() + "", cp.getInsuranceid() + "");
					SceneFactory.getBackButton().pop();
					Main.SwitchScene(scene);
					back.setText("BACK");// plus this from me
					getreturn = false;
				}

			} else {
				SceneFactory.getBackButton().pop();
				Main.SwitchScene(scene);
			}
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	public void search() {
			String s=search.getText().trim().toLowerCase();
			ObservableList<Company> listcopy = FXCollections.observableArrayList();
			loadallCompany(null);
			if(s.equals(" ")|| s.isEmpty())
				{
				loadallCompany(null);
				}
			else
			{
				for(Company cp: list)
				{
					if(cp.getId().toString().toLowerCase().contains(s)|| cp.getName().toLowerCase().contains(s)||cp.getAddress().toLowerCase().contains(s)||cp.getFax().toLowerCase().contains(s)||cp.getInsuranceid().toString().toLowerCase().contains(s)||cp.getCode().toLowerCase().contains(s)||cp.getRatio().toString().toLowerCase().contains(s))
					{
						listcopy.add(cp);
					}
				}
				list.clear();
				for(Company c:listcopy)
					list.add(c);
			}
			
	}
	
	public boolean isGetreturn() {
		return getreturn;
	}

	public void setGetreturn(boolean getreturn) {
		// add later change back button to submit or add a new back button discouraged
		back.setText("Submit");
		this.getreturn = getreturn;

	}

}
