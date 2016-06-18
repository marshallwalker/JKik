package ca.pureplugins.jkik.event;

import ca.pureplugins.jkik.event.base.Event;
import ca.pureplugins.jkik.interfaces.IChat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandEvent extends Event
{
	private final IChat chat;
	private final String message;
}