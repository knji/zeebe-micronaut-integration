package micronaut.email.service.models;

public class OrderDto {

	private int orderId;
	private String item;
	private int numberOfItems;
	private double price;
	private String paymentMethod;
	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public OrderDto setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
		return this;
	}

	public String getItem() {
		return item;
	}

	public OrderDto setItem(String item) {

		this.item = item;
		return this;
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

	public OrderDto setPrice(double price){
		this.price = price;
		return this;
	}

	public OrderDto setPaymentMethod(String paymentMethod){
		this.paymentMethod = paymentMethod;
		return this;
	}

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public int getOrderId() {
		return orderId;
	}

	public OrderDto setOrderId(int orderId) {
		this.orderId = orderId;
		return this;
	}
}
