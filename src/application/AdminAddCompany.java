package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import _data_type.InsuranceTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminAddCompany implements Initializable {

	@FXML
	private TextField cplocationin;

	@FXML
	private Label welcome11;

	@FXML
	private Button DeleteAgent;

	@FXML
	private Button LoadAgent;

	@FXML
	private TextField cpfaxin;

	@FXML
	private TextField error;

	@FXML
	private Label welcome111;

	@FXML
	private TextField cpnamein;

	@FXML
	private Label welcome1;

	@FXML
	private AnchorPane in;

	@FXML
	private Label welcome;
	@FXML
	private TableColumn<InsuranceTypes, Double> ratiocol;

	@FXML
	private TableColumn<InsuranceTypes, String> codecol;

	@FXML
	private TableColumn<InsuranceTypes, Integer> idcol;

	@FXML
	private TableColumn<InsuranceTypes, CheckBox> checkboxcol;

	@FXML
	private TableView<InsuranceTypes> tableview;

	ObservableList<InsuranceTypes> data = FXCollections.observableArrayList();


	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// data = FXCollections.observableArrayList(new InsuranceTypes("Jacob", "Smith",
		// "jacob.smith@example.com"),
		// new InsuranceTypes("Isabella", "Johnson", "isabella.johnson@example.com"),
		// new InsuranceTypes("Ethan", "Williams", "ethan.williams@example.com"),
		// new InsuranceTypes("Emma", "Jones", "emma.jones@example.com"),
		// new InsuranceTypes("Michael", "Brown", "michael.brown@example.com"),
		// new InsuranceTypes("Michael", "Brown", "michael.brown@example.com"),
		// new InsuranceTypes("Michael", "Brown", "michael.brown@example.com"),
		// new InsuranceTypes("Michael", "Brown", "michael.brown@example.com"));
		// fill data from insurance type sql:
		try {
			load(null);

			idcol.setCellValueFactory(new PropertyValueFactory<InsuranceTypes, Integer>("id"));
			codecol.setCellValueFactory(new PropertyValueFactory<InsuranceTypes, String>("code"));
			ratiocol.setCellValueFactory(new PropertyValueFactory<InsuranceTypes, Double>("ratio"));
			checkboxcol.setCellValueFactory(new PropertyValueFactory<InsuranceTypes, CheckBox>("checkbox"));
			tableview.setItems(data);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), e1, stage);
		}
	}

	Statement m_Statement;
	PreparedStatement ps = null;
	ResultSet m_ResultSet = null;
	Connection con;

	public void load(ActionEvent event) throws SQLException {
		try {
		data.clear();
		con = DBConnection.connect();

		m_Statement = con.createStatement();
		String query = "SELECT * FROM InsuranceTypes";

		m_ResultSet = m_Statement.executeQuery(query);
		while (m_ResultSet.next()) {
			InsuranceTypes t = new InsuranceTypes(m_ResultSet.getInt(1), m_ResultSet.getString(2),
					m_ResultSet.getDouble(3));
			data.add(t);
			//System.out.println(t);
		}
		m_Statement.close();
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow();
			AlertController.alertExc(e1.getMessage(), e1, stage);
		}
	}

	@FXML
	void ResetForm(ActionEvent event) {
		ResetHelper();
	}

	void ResetHelper() {
		for (InsuranceTypes bean : data) {
			if (bean.getCheckbox().isSelected()) {
				bean.untick();
			}
		}
		cpfaxin.setText("");
		cplocationin.setText("");
		cpnamein.setText("");
	}

	@FXML
	private void InsertCompany(ActionEvent event) {
try {
		if (!cpfaxin.getText().isEmpty() && !cplocationin.getText().isEmpty() && !cpnamein.getText().isEmpty()) {
			// there no need here for preprestatement just practicing to the next one :)
			ps = con.prepareStatement("insert into [Company] ([Name],[Location],[Fax]) values (?,?,?)");

			ps.setString(1, cpnamein.getText());
			ps.setString(2, cplocationin.getText());
			ps.setString(3, cpfaxin.getText());
			ps.executeUpdate();
			ps.close();
			//System.out.println("i' here ewrfew");

			Integer cpid;
			ps = con.prepareStatement("select max(id) from Company ");
			m_ResultSet = ps.executeQuery();
			m_ResultSet.next();
			cpid = m_ResultSet.getInt(1);

			error.setText("Your New inserted Company have ID " + cpid);
			AlertController.alert1("Your New inserted Company have ID " + cpid);
			m_ResultSet.close();
			ps.close();

			ps = con.prepareStatement("insert into [CPhaveIT] ([CPID],[ITID]) values (?,?)");
			ps.setString(1, cpid.toString());
			ps.setString(2, cplocationin.getText());

			for (InsuranceTypes bean : data) {
				if (bean.getCheckbox().isSelected()) {
					//System.out.println(bean.getId());
					ps.setString(2, bean.getId() + "");
					ps.executeUpdate();

				}

			}
			AlertController.alert1("Company Added");
			ps.close();
		} else {
			error.setText("Fill The Form and Check Box to Insert !!");
			AlertController.alert("Please Fill The Form and Check Box to Insert !!","Insert Error");
		}
			
}catch (Exception  e1) {
	// TODO Auto-generated catch block
	// e1.printStackTrace();
	Stage stage = (Stage) in.getScene().getWindow();
	AlertController.alertExc(e1.getMessage(), e1, stage);
}
	}

	@FXML
	private void GotoAdmin(ActionEvent event) throws IOException {

		Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
	}

}
