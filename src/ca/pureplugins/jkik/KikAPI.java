package ca.pureplugins.jkik;

import java.util.logging.Logger;

import org.java_websocket.util.Base64;

import ca.pureplugins.jkik.command.CommandBus;
import ca.pureplugins.jkik.event.base.EventBus;
import ca.pureplugins.jkik.exception.UpdateConfigException;
import ca.pureplugins.jkik.server.Webhook;
import lombok.Data;

@Data
public class KikAPI
{
	private final Logger logger = Logger.getLogger("JKik");

	private final String username;
	private final String auth;
	private final String token;

	private final EventBus eventBus = new EventBus();
	private final CommandBus commandBus = new CommandBus(this);
	private final Webhook webhook = new Webhook(this);

	public KikAPI(String username, String auth)
	{
		this.username = username;
		this.auth = auth;
		this.token = "Basic " + Base64.encodeBytes((username + ":" + auth).getBytes());
	}

	public KikAPI start(String address, int port, String path) throws UpdateConfigException
	{
		webhook.setAddress(address);
		webhook.setPort(port);
		webhook.setPath(path);
		webhook.start();
		return this;
	}

	public KikAPI stop()
	{
		webhook.stop();
		return this;
	}
}