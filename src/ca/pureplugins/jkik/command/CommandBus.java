package ca.pureplugins.jkik.command;

import java.util.ArrayList;
import java.util.List;

import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.event.CommandEvent;
import ca.pureplugins.jkik.util.Logger.Level;
import lombok.Data;

@Data
public class CommandBus
{
	private final List<Command> commands = new ArrayList<>();

	private String prefix = ".";

	public CommandBus(KikAPI api)
	{
		api.getEventBus().register(CommandEvent.class, event ->
		{
			Command toExecute = null;

			for (Command command : commands)
			{
				if (command.getAliases().contains(event.getMessage().split(" ")[0].replace(".", "")))
				{
					toExecute = command;
					break;
				}
			}

			if (toExecute == null)
				return;

			api.getLogger().log(Level.COMMAND, event.getChat().getSender() + " executed the command '" + event.getMessage() + "'");

			toExecute.setChat(event.getChat());
			toExecute.setArgs(event.getMessage().split(" "));
			toExecute.execute();
		});
	}

	public void register(Command command)
	{
		commands.add(command);
	}
}