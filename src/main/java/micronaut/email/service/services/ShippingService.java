package micronaut.email.service.services;


import com.google.gson.JsonSerializer;
import io.micronaut.core.reflect.ReflectionUtils;
import io.micronaut.jackson.convert.ObjectToJsonNodeConverter;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import micronaut.email.service.models.OrderCommandArgs;
import micronaut.email.service.models.Shipment;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


public class ShippingService implements JobHandler {

	//private static final Log _log = LogFactory.getLog(ShippingService.class);

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {

		trace(String.format("Shipping service started for workflow instance %s and job instance %s", job.getWorkflowInstanceKey(), job.getKey()));

		OrderCommandArgs order = job.getVariablesAsType(OrderCommandArgs.class);
		trace("Retrieving packages and preparing for shipment for order " + order);

		Thread.sleep(1000);

		List<Shipment> shipment = new ArrayList<>();
		String[] packages = order.getPackages();

		// logic like this can be part of an order .... isValid
		if (packages == null || packages.length == 0 && order.getNumberOfItems() > 0) {
			trace("This order has a price but warehouse did not send packages.  Cancelling and failing.  Please refund customer's money of " + order.getNumberOfItems());
			client.newFailCommand(job.getKey());
			return;
		}

		for (int i = 0; i < packages.length; i++) {
			trace("Shipping item " + packages[i] + " via FEDEX.... ");
			shipment.add(new Shipment(OffsetDateTime.now(), packages[i]));
		}


		order.setShipment(shipment);
		trace("We have shipped these customer items: " + shipment);

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
