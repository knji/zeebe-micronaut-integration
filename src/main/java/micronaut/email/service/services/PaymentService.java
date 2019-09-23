package micronaut.email.service.services;


import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import micronaut.email.service.models.OrderCommandArgs;
import micronaut.email.service.models.PaymentStatus;


public class PaymentService implements JobHandler {

	//private static final Log _log = LogFactory.getLog(PaymentService.class);
	private final PaymentProcessor _paymentProcessor;

	public PaymentService(PaymentProcessor paymentProcessor) {

		_paymentProcessor = paymentProcessor;
	}

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {

		trace(String.format("Payment service started for workflow instance %s and job instance %s", job.getWorkflowInstanceKey(), job.getKey()));
		trace("We are collecting some money.... please wait ");

		OrderCommandArgs order = job.getVariablesAsType(OrderCommandArgs.class);
		trace("Processing payment for order " + order.getOrderId());
		trace("Process order we retrieved from workflow variables: " + order);
		trace("Delegating payment processing to Payment Processor.... please wait.... ");

		PaymentStatus status = _paymentProcessor.process(order.getPayment());

		if (status.isSuccess()) {
			order.setOrderStatus("PAYMENT-RECEIVED");
		}
		else {
			order.setOrderStatus("PAYMENT-FAILED");
		}

		trace("We are done processing the order!");

		// put the order back..
		client.newCompleteCommand(job.getKey())
				.variables(order)
				.send()
				.join();
	}

	private void trace(String message){
		System.out.println(message);
		//_log.info(message);
	}
}
