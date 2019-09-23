package micronaut.email.service.models;

import java.time.OffsetDateTime;

public class Shipment {
	public Shipment(){

	}

	public Shipment(OffsetDateTime timeStamp, String name ){
		//	this.timeStamp = timeStamp;
		this.name = name;
	}

	/*
	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}*/

	//private OffsetDateTime timeStamp;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
