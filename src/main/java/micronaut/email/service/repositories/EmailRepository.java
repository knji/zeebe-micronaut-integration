package micronaut.email.service.repositories;

import java.util.List;

public interface EmailRepository {
	List<String> getEmailsSent();
}
