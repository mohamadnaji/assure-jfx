package application;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertController {
	public static void alertExc(String Content,Exception ex,Stage window) {
		Alert a=new Alert(AlertType.ERROR);

		a.setContentText(Content);
		

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		// Set expandable Exception into the dialog pane.
		a.getDialogPane().setExpandableContent(expContent);
		
		a.initOwner(window);//to take stage icon
		//a.initStyle(StageStyle.UTILITY);//to remove icon
		a.initModality(Modality.WINDOW_MODAL);
		a.show();
	}
	
	public static void alert(String header,String title) {
		Alert a=new Alert(AlertType.ERROR);
		a.setHeaderText(title);
		a.setContentText(header);
		a.initStyle(StageStyle.UTILITY);
		a.show();
		
	}
	public static void alert1(String header) {
		Alert a=new Alert(AlertType.INFORMATION);
		a.setHeaderText(null);
		a.setContentText(header);	
		a.initStyle(StageStyle.UTILITY);
		a.show();
		
	}

}
