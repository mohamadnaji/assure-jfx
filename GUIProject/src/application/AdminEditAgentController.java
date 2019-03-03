package application;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import _data_type.Agent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminEditAgentController implements Initializable {
	public static Agent ag;
	@FXML
	private Button add;

	@FXML
	private TextField Mail;

	@FXML
	private TextField Pass;

	@FXML
	private AnchorPane in;
	
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

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@FXML
	public void init() {
		AgentName.setText(ag.getName());
		LastName.setText(ag.getLastName());
		Mail.setText(ag.getMail());
		Pass.setText(ag.getPass());
	}

	@FXML
	public void updateAgent()  {
		try {
				Connection con = DBConnection.connect();
				PreparedStatement prepare = null;
				String name = AgentName.getText();
				String lastname = LastName.getText();
				String mail = Mail.getText();
				String pass = Pass.getText();
				String query = "update agent set Name=?,Last=?,Mail=?,Password=? where id =?";
	
				if (!name.isEmpty() && !lastname.isEmpty() && !mail.isEmpty() && !pass.isEmpty()) {
					prepare = con.prepareStatement(query);
					prepare.setString(1, name);
					prepare.setString(2, lastname);
					prepare.setString(3, mail);
					prepare.setString(4, pass);
					prepare.setString(5, ag.getId().toString());
					prepare.execute();
					addagentconfirm.setText("Agent Updated !");
					AlertController.alert1("Agent Updated !");
					prepare.close();
					ag= new Agent(ag.getId(), name, lastname, mail, pass);
					
				} else {
					
					AlertController.alert("Input Error !","Error");
					addagentconfirm.setText("Input Error !");
				}
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}
	}

	@FXML
	void backtoAdmin(ActionEvent event)  {

		// Main.SwitchScene(new SceneFactory().GetRecentScene("Admin.fxml"));
		try {
			Scene scene=new SceneFactory().GetRecentScene(SceneFactory.getBackButton().pop());
			AdminController t=(AdminController)scene.getUserData();
			t.load(null);
			Main.SwitchScene(scene);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Stage stage = (Stage) in.getScene().getWindow(); // cant catch email syntax
			AlertController.alertExc(e1.getMessage(), (Exception) e1, stage);
		}

	}

	@FXML
	void clearall(ActionEvent event) {
		AgentName.clear();
		LastName.clear();
		Mail.clear();
		Pass.clear();
	}

	public static Agent getAg() {
		return ag;
	}

	public static void setAg(Agent ag) {
		AdminEditAgentController.ag = ag;
	}

}