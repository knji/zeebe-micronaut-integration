package micronaut.email.service.messagehandlers;

import io.micronaut.context.annotation.Prototype;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import micronaut.email.service.models.OrderCommandArgs;


import javax.inject.Inject;
import javax.inject.Provider;

@Prototype
public class OrchestratedOrderCommandHandler implements CommandHandler<OrderCommandArgs> {

	private final ZeebeClient _client;
	//private static final Log _log = LogFactory.getLog(OrchestratedOrderCommandHandler.class);

	@Inject
	public OrchestratedOrderCommandHandler(Provider<ZeebeClient> clientProvider) {

		_client = clientProvider.get();
	}

	@Override
	public String handle(OrderCommandArgs command) {

		trace("Handling orchestrated order command ....");
		trace("Creating workflow variables for deployed workflow");
		trace("Zeebe: creating an instance of deployed workflow");

		final WorkflowInstanceEvent workflowInstanceEvent = _client.newCreateInstanceCommand()
				.bpmnProcessId("order-process")
				.latestVersion()
				.variables(command)
				.send()
				.join();


		final long workflowInstanceKey = workflowInstanceEvent.getWorkflowInstanceKey();
		trace("Workflow instance created.  Key :" + workflowInstanceKey);

		return "ok";
	}

	private void trace(String message){
		System.out.println(message);
		//_log.info(message);
	}

}
