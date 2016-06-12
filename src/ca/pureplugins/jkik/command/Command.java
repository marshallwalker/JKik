package ca.pureplugins.jkik.command;

import java.util.Arrays;
import java.util.List;

import ca.pureplugins.jkik.interfaces.Chat;
import lombok.Data;

@Data
public abstract class Command
{
	private final List<String> aliases;
	private Chat chat;
	private String[] args;

	public Command(String... aliases)
	{
		this.aliases = Arrays.asList(aliases);
	}

	public abstract void execute();
}