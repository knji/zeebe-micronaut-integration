package micronaut.email.service.repositories;

import io.micronaut.context.annotation.Prototype;

import java.util.ArrayList;
import java.util.List;

@Prototype
public class InMemoryEmailRepository implements EmailRepository {

	private static List<String> emailsSent = new ArrayList<>();

	@Override
	public List<String> getEmailsSent() {
		return emailsSent;
	}
}
