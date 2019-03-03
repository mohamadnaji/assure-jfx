package _data_type;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Clients {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty LastName;
	private final SimpleStringProperty telephone;
	
	public Clients(Integer id, String name, String LastName, String telephone){
	super();
	this.id = new SimpleIntegerProperty(id);
	this.name = new SimpleStringProperty(name);
	this.LastName = new SimpleStringProperty(LastName);
	this.telephone = new SimpleStringProperty(telephone);
	
	}

	public Clients(Clients client) {
		// TODO Auto-generated constructor stub
		super();
		this.id = new SimpleIntegerProperty(client.id.get());
		this.name = new SimpleStringProperty(client.name.get());
		this.LastName = new SimpleStringProperty(client.LastName.get());
		this.telephone = new SimpleStringProperty(client.telephone.get());
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

	public String getTelephone() {
		return telephone.get();
	}
	
	
	@Override
	public String toString() {
		return "Clients [id=" + id + ", name=" + name + ", LastName=" + LastName + ", telephone=" + telephone + "]";
	}
	
}
