package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import _data_type.PayCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PayCollectionController implements Initializable {

	@FXML
	private ImageView adminimage;

	@FXML
	private TableColumn<PayCollection, Integer> agentid;

	@FXML
	private TableColumn<PayCollection, Integer> clientid;

	@FXML
	private TableColumn<PayCollection, Integer> contratid;

	@FXML
	private TableColumn<PayCollection, String> dateend;

	@FXML
	private Label adminname;

	@FXML
	private Button loadcusinfo;

	@FXML
	private Label adminid;

	@FXML
	private TableView<PayCollection> payment;

	@FXML
	private Button showallpayment;

	@FXML
	private Label welcome;
	@FXML
	private AnchorPane in;

	public ObservableList<PayCollection> list = FXCollections.observableArrayList();

	@FXML
	public void loadmyPayment(ActionEvent event) {
		list.clear();
		Connection con = DBConnection.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("select * from PayCollection");// contient payed contrat
			rs = ps.executeQuery();
			while (rs.next()) {

				PayCollection t = new PayCollection(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
				list.add(t);
				// System.out.println(t);
			}
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public void loadnotPayed(ActionEvent event) {
		list.clear();
		Connection con = DBConnection.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("exec ClientDebt;");
			rs = ps.executeQuery();
			while (rs.next()) {

				PayCollection t = new PayCollection(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
				list.add(t);
			}
		} catch (Exception e1) {
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		agentid.setCellValueFactory(new PropertyValueFactory<PayCollection, Integer>("agentid"));
		clientid.setCellValueFactory(new PropertyValueFactory<PayCollection, Integer>("clientid"));
		contratid.setCellValueFactory(new PropertyValueFactory<PayCollection, Integer>("contratid"));
		dateend.setCellValueFactory(new PropertyValueFactory<PayCollection, String>("dateend"));
		payment.setItems(list);
	}

	public void gotoInsertPay() {

		try {
			son();
			Main.SwitchScene(new SceneFactory().GetRecentScene("InsertPay.fxml"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public void back() {
		try {
			Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	public void son() {
		SceneFactory.getBackButton().add("PayCollection.fxml");
	}
}
