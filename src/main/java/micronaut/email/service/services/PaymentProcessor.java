package micronaut.email.service.services;


import micronaut.email.service.models.Payment;
import micronaut.email.service.models.PaymentStatus;

public interface PaymentProcessor  {
	PaymentStatus process(Payment payment);
}
