package _data_type;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PayCollection  {
	private final SimpleIntegerProperty agentid;
	private final SimpleIntegerProperty clientid;
	private final SimpleIntegerProperty contratid;
	private final SimpleStringProperty dateend;
	
	public PayCollection(Integer agentid, Integer clientid, Integer contratid, String dateend){
	super();
	this.agentid = new SimpleIntegerProperty(agentid);
	this.clientid = new  SimpleIntegerProperty(clientid);
	this.contratid = new  SimpleIntegerProperty(contratid);
	this.dateend = new SimpleStringProperty(dateend);
	}

	public Integer getAgentid() {
		return agentid.get();
	}

	public Integer getClientid() {
		return clientid.get();
	}

	public Integer getContratid() {
		return contratid.get();
	}

	public String getDateend() {
		return dateend.get();
	}

	@Override
	public String toString() {
		return "PayCollection [agentid=" + agentid + ", clientid=" + clientid + ", contratid=" + contratid
				+ ", dateend=" + dateend + "]";
	}
	
	
	



}
