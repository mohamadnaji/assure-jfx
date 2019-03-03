package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InsertPayController {

	@FXML
	private Button add;

	@FXML
	private DatePicker date;

	@FXML
	private TextField ctid;

	@FXML
	private Label addcusconfirm;

	@FXML
	private TextField clid;

	@FXML
	private TextField agid;

	@FXML
	private Label success;
	@FXML
	private Label addcusconfirme;
	@FXML
	private AnchorPane in;

	@FXML
	private Button add1;

	public void AddPay() {
		Connection con = DBConnection.connect();
		PreparedStatement prepare = null;
		String agentid = agid.getText();
		String clientid = clid.getText();
		String contratid = ctid.getText();

		try {
			LocalDate ddd = date.getValue();
			Date date1 = java.sql.Date.valueOf(ddd);
			String query = "Insert Into PayCollection Values(?,?,?,?)";
			if (!agentid.isEmpty() && !clientid.isEmpty() && !contratid.isEmpty()) {
				prepare = con.prepareStatement(query);
				prepare.setString(1, agentid);
				prepare.setString(2, clientid);
				prepare.setString(3, contratid);
				prepare.setDate(4, date1);
				prepare.execute();
				success.setText("Pay Completed");
				AlertController.alert1("Pay Completed");
				prepare.close();
			} else {
				success.setText("Input Error");
				AlertController.alert("Input Error","Error");
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	void backtoAgent(ActionEvent event){
		try {
			Main.SwitchScene(new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop()));
		}  catch (Exception e1) {
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}
}
