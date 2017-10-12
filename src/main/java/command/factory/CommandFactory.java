package command.factory;

import command.Command;

public interface CommandFactory {

    Command getCommand(String path);
}
