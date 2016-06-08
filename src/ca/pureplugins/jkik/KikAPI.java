package ca.pureplugins.jkik;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.java_websocket.util.Base64;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ca.pureplugins.jkik.command.CommandBus;
import ca.pureplugins.jkik.event.ChatMessageEvent;
import ca.pureplugins.jkik.event.CommandEvent;
import ca.pureplugins.jkik.event.base.EventBus;
import ca.pureplugins.jkik.model.Chat;
import ca.pureplugins.jkik.util.Logger;
import ca.pureplugins.jkik.util.Logger.Level;
import lombok.Getter;
import spark.Spark;

@Getter
public class KikAPI
{
	private final String username;
	private final String auth;
	private final String token;

	private String address;
	private int port = 8686;
	private String webhook = "/webhook";

	private final EventBus eventBus = new EventBus();
	private final CommandBus commandBus = new CommandBus(this);
	private final Logger logger = new Logger();

	public KikAPI(String username, String auth)
	{
		this.username = username;
		this.auth = auth;
		this.token = "Basic " + Base64.encodeBytes((username + ":" + auth).getBytes());
	}

	public KikAPI startWebhook(String address, String webhook, int port)
	{
		logger.log(Level.INFO, "Starting webhook...");

		this.address = address;
		this.webhook = webhook;
		this.port = port;

		updateConfig();

		Spark.port(port);
		Spark.post(webhook, (request, response) ->
		{
			JsonElement element = new JsonParser().parse(request.body());
			JsonElement wrapper = element.getAsJsonObject().get("messages");
			JsonElement content = wrapper.getAsJsonArray().get(0);

			String message = content.getAsJsonObject().get("body").getAsString();
			String username = content.getAsJsonObject().get("from").getAsString();
			String chatId = content.getAsJsonObject().get("chatId").getAsString();
			long timestamp = content.getAsJsonObject().get("timestamp").getAsLong();

			if (message.startsWith(commandBus.getPrefix()))
			{
				eventBus.post(new CommandEvent(new Chat(this, chatId, username), message));
			}
			else
			{
				eventBus.post(new ChatMessageEvent(new Chat(this, chatId, username), message, request.body(), timestamp));
			}
			return "";
		});

		logger.log(Level.INFO, "Started webhook!");
		return this;
	}

	public KikAPI stopWebhook()
	{
		Spark.stop();
		return this;
	}

	private void updateConfig()
	{
		try
		{
			logger.log(Level.INFO, "Updating config...");

			URL url = new URL("https://api.kik.com/v1/config");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.addRequestProperty("Authorization", token);
			con.setDoOutput(true);

			DataOutputStream writer = new DataOutputStream(con.getOutputStream());

			String json = "{\"webhook\"" + address + ":" + port + webhook + "\",\"features\": {\"manuallySendReadReceipts\": false,\"receiveReadReceipts\": false,\"receiveDeliveryReceipts\": false,\"receiveIsTyping\": false}}";

			writer.write(json.getBytes());
			writer.flush();
			writer.close();

			logger.log(Level.INFO, "Updated config!");
		}
		catch (IOException e)
		{
			logger.log(Level.ERROR, "Unable to update configuration, " + e.getLocalizedMessage());
		}
	}
}