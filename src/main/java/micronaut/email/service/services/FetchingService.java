package micronaut.email.service.services;


import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import micronaut.email.service.models.OrderCommandArgs;


public class FetchingService implements JobHandler {

	//private static final Log _log = LogFactory.getLog(FetchingService.class);

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {

		trace(String.format("Fetching service started for workflow instance %s and job instance %s", job.getWorkflowInstanceKey(), job.getKey()));

		OrderCommandArgs order = job.getVariablesAsType(OrderCommandArgs.class);

		trace("In the midst of processing order : " + order.getOrderId());
		trace("Customer requested items : " + order.getItem());
		trace("Customer has paid a total of : " + order.getPrice());
		trace("Customer wants : " + order.getNumberOfItems() + " items" );
		trace("Doing some work to fetch their items... please wait.... ");

		Thread.sleep(2000);

		String[] packages = new String[order.getNumberOfItems()];
		for (int i = 0; i < packages.length; i++) {
			packages[i] = order.getItem() + ".package" + i;
			trace("\t Fetched " + packages[i] + " from warehouse....");
		}

		order.setPackages(packages);
		trace("Modified original order, adding customer packages for traceability. Order is: " + order);
		trace("We are done retrieving the customers items.  Passing order on......");

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
