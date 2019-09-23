package micronaut.email.service.services;


import micronaut.email.service.models.Payment;
import micronaut.email.service.models.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

public class InMemoryVisaPaymentProcessor implements PaymentProcessor {

	//private static final Log _log = LogFactory.getLog(InMemoryVisaPaymentProcessor.class);

	private static Map<String, Account> accounts = new HashMap<>();

	public InMemoryVisaPaymentProcessor(){
		accounts.put("1234", new Account().setCardNumber("1234").setBalance(1000));
		accounts.put("4567", new Account().setCardNumber("4567").setBalance(1000));
	}

	@Override
	public PaymentStatus process(Payment payment) {

		trace("Payment processing started by InMemoryVisaPaymentProcessor..");

		PaymentStatus status = new PaymentStatus();

		try {
			Thread.sleep(2000);

			if (accounts.containsKey(payment.getAccountNumber())){

				Account account = accounts.get(payment.getAccountNumber());

				if (account.hasSufficientFunds(payment.getCharge())){
				account.charge(payment.getCharge());

				payment.setSuccess(true);
				status.setSuccess(true);

				trace(String.format("We have a VISA account for %s.  Charging card.  Account balance is %.2f ",
						payment.getAccountNumber(), account.getBalance()));
				}
				else{
					String failureMessage = "Account " + payment.getAccountNumber() + " does not have sufficient funds for charge " + payment.getCharge();
					handleChargeFailure(payment, failureMessage);
				}

			}else{

				String failureMessage = "We do not have a VISA account for " + payment.getPaymentMethod() + ".  Cannot process card.";
				handleChargeFailure(payment, failureMessage);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return status;
	}

	private void handleChargeFailure(Payment payment, String failureMessage) {
		trace(failureMessage);
		payment.setSuccess(false);
		payment.setFailureReason("VISA Account does not exist for " + payment.getAccountNumber());
	}

	private void trace(String message){

		//_log.info(message);
		System.out.println(message);
	}

	public static class Account{
		private String cardNumber;
		private double balance;
		private double overdraftLimit = 1000;

		public String getCardNumber() {
			return cardNumber;
		}

		public Account setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
			return this;
		}

		public double getBalance() {
			return balance;
		}

		public Account setBalance(double balance) {
			this.balance = balance;
			return this;
		}

		public Account charge(double amount){
			balance = balance - amount;
			return this;
		}

		public boolean hasSufficientFunds(double charge){
			if (balance >= charge)
				return true;

			double futureBalance = balance - charge;

			if (futureBalance < 0 && (futureBalance * -1 ) > overdraftLimit ) {
				return false;
			}

			return true;

		}
	}
}
