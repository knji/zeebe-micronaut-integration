package micronaut.email.service.models;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.OffsetDateTime;
import java.util.List;

public class OrderCommandArgs implements Command {

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	private int orderId;
	private String item;
	private int numberOfItems;
	private double price;



	/*

	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(OffsetDateTime timeStNamp) {
		this.timeStamp = timeStNamp;
	}

	private OffsetDateTime timeStamp;

*/


	public OrderCommandArgs(){}

	public OrderCommandArgs(String item, int numberOfItems, double price, OffsetDateTime timeStamp) {

		this.item = item;
		this.numberOfItems = numberOfItems;
		this.price = price;
		//this.timeStamp = timeStamp;
		orderId = timeStamp.getNano();
	}

	private String orderStatus;
	//private OffsetDateTime orderProcessingTime;

	public OrderCommandArgs setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
		//this.orderProcessingTime = OffsetDateTime.now();
		return this;
	}
	/*
	public OffsetDateTime getOrderProcessingTime(){
		return this.orderProcessingTime;
	}*/

	public void setOrderProcessingTime(OffsetDateTime orderProcessingTime) {
		//this.orderProcessingTime = orderProcessingTime;
	}

	public String getOrderStatus(){
		return this.orderStatus;
	}

	private String[] packages;
	public void setPackages(String[] packages) {
		this.packages = packages;
	}

	public String[] getPackages(){
		return packages;
	}

	private List<Shipment> shipment;
	public void setShipment(List<Shipment> shipment) {
		this.shipment = shipment;
	}

	public List<Shipment> getShipment(){
		return this.shipment;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}

	private Payment payment;

	public Payment getPayment() {
		return payment;
	}

	public OrderCommandArgs setPayment(Payment payment) {
		this.payment = payment;
		return this;
	}
}
