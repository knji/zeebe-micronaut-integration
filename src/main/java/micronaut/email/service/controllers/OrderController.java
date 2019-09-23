package micronaut.email.service.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import micronaut.email.service.messagehandlers.CommandHandler;
import micronaut.email.service.models.OrderCommandArgs;
import micronaut.email.service.models.OrderDto;
import micronaut.email.service.models.Payment;

import javax.inject.Inject;
import javax.xml.ws.Response;
import java.time.OffsetDateTime;

@Controller("/api/orders")
public class OrderController {

	private CommandHandler<OrderCommandArgs> _orderCommandCommandHandler;

	@Inject
	public OrderController(CommandHandler<OrderCommandArgs> orderCommandCommandHandler ){

		_orderCommandCommandHandler = orderCommandCommandHandler;
	}

	@Get
	public String getOrder(){

		return "order";
	}

	@Post(value = "/order", consumes = MediaType.APPLICATION_JSON )
	public HttpStatus placeAnOrder(@Body OrderDto orderDto) {

		Payment payment = new Payment()
				.setCharge(orderDto.getPrice())
				.setPaymentMethod(orderDto.getPaymentMethod())
				.setAccountNumber(orderDto.getAccountNumber());

		OrderCommandArgs orderCommand = new OrderCommandArgs(
				orderDto.getItem(), orderDto.getNumberOfItems(),
				orderDto.getPrice(),
				OffsetDateTime.now());
		orderCommand.setPayment(payment);

		_orderCommandCommandHandler.handle(orderCommand);

		System.out.println("Received an order for " + orderCommand.getPrice());
		return HttpStatus.OK;
	}
}
