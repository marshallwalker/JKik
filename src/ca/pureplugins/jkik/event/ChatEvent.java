package ca.pureplugins.jkik.event;

import ca.pureplugins.jkik.event.base.Event;
import ca.pureplugins.jkik.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatEvent extends Event
{
	private final Chat chat;
	private final String message;
	private final long timestamp;
}