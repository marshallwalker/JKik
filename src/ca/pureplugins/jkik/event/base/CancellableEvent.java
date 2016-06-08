package ca.pureplugins.jkik.event.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CancellableEvent extends Event
{
	private boolean cancelled;
}