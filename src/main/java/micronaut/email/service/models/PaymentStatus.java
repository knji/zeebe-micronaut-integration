package micronaut.email.service.models;

public class PaymentStatus {
	private boolean success;
	private String status;

	public boolean isSuccess() {
		return success;
	}

	public PaymentStatus setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public PaymentStatus setStatus(String status) {
		this.status = status;
		return this;
	}
}
