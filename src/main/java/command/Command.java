package command;


import command.factory.impl.CommandFactoryImpl;
import services.factory.ServiceFactory;
import services.factory.impl.ServiceFactoryImpl;
import validators.UserValidator;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    ServiceFactory SERVICE_FACTORY = ServiceFactoryImpl.getServiceFactoryInstance();
    CommandFactoryImpl COMMAND_FACTORY = CommandFactoryImpl.getCommandFactoryImplInstance();
    UserValidator USER_VALIDATOR = UserValidator.getUserValidatorInstance();

    String execute(HttpServletRequest request);
}
