package _data_type;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Company {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty address;
	private final SimpleStringProperty fax;
	private final SimpleIntegerProperty insuranceid;
	private final SimpleStringProperty code;
	private final SimpleDoubleProperty ratio;
	
	public Company(Integer id, String name, String address, String fax,Integer insuranceid,String code,Double ratio){
	super();
	this.id = new SimpleIntegerProperty(id);
	this.name = new SimpleStringProperty(name);
	this.address = new SimpleStringProperty(address);
	this.fax = new SimpleStringProperty(fax);
	this.insuranceid=new SimpleIntegerProperty(insuranceid);
	this.code = new SimpleStringProperty(code);
	this.ratio = new SimpleDoubleProperty(ratio);
	}

	

	public Integer getId() {
		return id.get();
	}



	public String getName() {
		return name.get();
	}



	public String getAddress() {
		return address.get();
	}



	public String getFax() {
		return fax.get();
	}



	public Integer getInsuranceid() {
		return insuranceid.get();
	}



	public String getCode() {
		return code.get();
	}



	public Double getRatio() {
		return ratio.get();
	}



	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", location=" + address + ", fax=" + fax + ", insuranceid="
				+ insuranceid + ", code=" + code + ", ratio=" + ratio + "]";
	}
	


	
}