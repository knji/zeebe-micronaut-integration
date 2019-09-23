package micronaut.email.service.services;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import micronaut.email.service.models.OrderCommandArgs;


public class EmailService implements JobHandler {

	//private static final Log _log = LogFactory.getLog(FetchingService.class);

	public EmailService(){

	}

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {

		trace(String.format("Email service started for workflow instance %s and job instance %s", job.getWorkflowInstanceKey(), job.getKey()));

		OrderCommandArgs order = job.getVariablesAsType(OrderCommandArgs.class);

		trace("In the midst of computing email based on template to send to customer for  : " + order.getOrderId());
		trace("Customer requested items : " + order.getItem());
		trace("Customer has paid a total of : " + order.getPrice());
		trace("Customer wants : " + order.getNumberOfItems() + " items" );
		trace("Doing some work to fetch their email templates and firing off email... please wait.... ");

		Thread.sleep(2000);

		/*
		String[] packages = new String[order.getNumberOfItems()];
		for (int i = 0; i < packages.length; i++) {
			packages[i] = order.getItem() + ".package" + i;
			trace("\t Fetched " + packages[i] + " from warehouse....");
		}*/

		trace("Order received by the email service.  Notifying the customer we are shipping their stuff");

		client.newCompleteCommand(job.getKey())
				//.variables(order)
				.send()
				.join();
	}

	private void trace(String message){
		System.out.println(message);
		//_log.info(message);
	}
}

