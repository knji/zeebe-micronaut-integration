package micronaut.email.service.models;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Payment {

	private double charge;
	private String paymentMethod;
	private boolean success;
	private String failureReason;
	private String accountNumber;

	public double getCharge() {
		return charge;
	}

	@JsonSetter
	public Payment setCharge(double charge) {
		this.charge = charge;
		return this;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	@JsonSetter
	public Payment setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
		return this;
	}

	public boolean getSuccess() {
		return success;
	}

	@JsonSetter
	public Payment setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getFailureReason() {
		return failureReason;
	}

	@JsonSetter
	public Payment setFailureReason(String failureReason) {
		this.failureReason = failureReason;
		return this;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@JsonSetter
	public Payment setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
		return this;
	}
}
