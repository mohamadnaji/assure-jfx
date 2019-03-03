package _data_type;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {

	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty LastName;
	private final SimpleStringProperty Mail;
	private final SimpleStringProperty Pass;

	public Agent(Integer id, String name, String LastName, String Mail, String Pass) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.LastName = new SimpleStringProperty(LastName);
		this.Mail = new SimpleStringProperty(Mail);
		this.Pass = new SimpleStringProperty(Pass);
	}

	public Integer getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getLastName() {
		return LastName.get();
	}

	public String getMail() {
		return Mail.get();
	}

	public String getPass() {
		return Pass.get();
	}

	@Override
	public String toString() {
		return "Agent id=" + id + ", name=" + name + ", LastName=" + LastName + ", Mail=" + Mail + " PASSWORD" + Pass;
	}

}
