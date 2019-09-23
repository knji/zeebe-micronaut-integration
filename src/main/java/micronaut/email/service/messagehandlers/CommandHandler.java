package micronaut.email.service.messagehandlers;

import micronaut.email.service.models.Command;

public interface CommandHandler <T extends Command> {

	String handle(T command);

}
