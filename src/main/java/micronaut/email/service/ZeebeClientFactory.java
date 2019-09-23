package micronaut.email.service;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.worker.JobWorker;
import micronaut.email.service.services.EmailService;
import micronaut.email.service.services.FetchingService;
import micronaut.email.service.services.InMemoryVisaPaymentProcessor;
import micronaut.email.service.services.PaymentProcessor;
import micronaut.email.service.services.PaymentService;
import micronaut.email.service.services.ShippingService;

import javax.inject.Provider;
import javax.inject.Singleton;


@Singleton
public class ZeebeClientFactory implements Provider<ZeebeClient> {

	private ZeebeClient _zeeBeClient = null;
	private Object _lock = new Object();


	private ZeebeClient setupOrderProcessingOrchestration(){


		System.out.println("Iniializing the zeebe client ");
		final String brokerUrl = "127.0.0.1:26500";
		System.out.println("Zeebe client connected ");

		final ZeebeClient client = ZeebeClient.newClientBuilder().brokerContactPoint(brokerUrl)
				.build();

		// ================== Building a specific order process ===============//

		final String orderProcess = "order-process.bpmn";
		System.out.println("Zeebe: deploying workflow  " + orderProcess);
		final DeploymentEvent deploymentEvent = client.newDeployCommand()
				.addResourceFromClasspath(orderProcess)
				.send()
				.join();


		final int version = deploymentEvent.getWorkflows().get(0).getVersion();
		System.out.println("Workflow deployed. Version : " + version);


		// ========================   Adding the workers =========================//
		System.out.println("Adding a job worker for the Email Service");
		final JobWorker emailServiceTask = client.newWorker()
				.jobType("email-service")
				.handler(new EmailService())
				// Be careful, this only returns some variables.  BEST practice is to ALWAYA model things and not rely on variables.
				//.fetchVariables("orderId")
				.open();


		PaymentProcessor paymentProcessor = new InMemoryVisaPaymentProcessor();
		PaymentService paymentService = new PaymentService(paymentProcessor);
		// ========================   Adding the workers =========================//
		System.out.println("Adding a job worker for the Payment Service");
		final JobWorker paymentServiceTask = client.newWorker()
				.jobType("payment-service")
				.handler(paymentService)
				// Be careful, this only returns some variables.  BEST practice is to ALWAYA model things and not rely on variables.
				//.fetchVariables("orderId")
				.open();

		//paymentServiceTask.close();

		System.out.println("Adding a job worker for the Fetching Items Service");
		final JobWorker fetchingItemsTask = client.newWorker()
				.jobType("fetching-items-service")
				.handler(new FetchingService())
				// Be careful, this only returns some variables.  BEST practice is to ALWAYA model things and not rely on variables.
				//.fetchVariables(Arrays.asList("orderId", orderItemsStr, "totalPrice"))
				.open();

		//fetchingItemsTask.close();

		System.out.println("Adding a job worker for the Shipping Service Items Service");
		final JobWorker shippingServiceTask = client.newWorker()
				.jobType("shipping-service")
				.handler(new ShippingService())
				// Be careful, this only returns some variables.  BEST practice is to ALWAYA model things and not rely on variables.
				//.fetchVariables(Arrays.asList("orderId", "totalPrice", "itemsRetrieved", orderItemsStr))
				.open();



		// ================= Done adding the workers ======================//


		return client;
	}

	@Override
	public ZeebeClient get() {

		if (_zeeBeClient == null)
		{
			synchronized (_lock){
				if (_zeeBeClient == null)
				{
					_zeeBeClient = setupOrderProcessingOrchestration();
				}
			}

		}

		return _zeeBeClient;
	}
}
