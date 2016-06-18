package ca.pureplugins.jkik.server;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.event.ChatEvent;
import ca.pureplugins.jkik.event.CommandEvent;
import ca.pureplugins.jkik.exception.UpdateConfigException;
import ca.pureplugins.jkik.model.Chat;
import lombok.Data;
import spark.Spark;

@Data
public class Webhook
{
	private final KikAPI api;

	private String address;
	private int port = 8686;
	private String path = "/webhook";

	public Webhook start() throws UpdateConfigException
	{
		setConfig();

		Spark.port(port);
		Spark.post(path, (request, response) ->
		{
			JSONArray messages = new JSONObject(request.body()).getJSONArray("messages");

			for (int i = 0; i < messages.length(); i++)
			{
				JSONObject message = messages.getJSONObject(i);

				String sender = message.getString("from");
				String body = message.getString("body");
				String chatId = message.getString("chatId");
				long timestamp = message.getLong("timestamp");

				if (body.startsWith(api.getCommandBus().getPrefix()))
				{
					api.getEventBus().post(new CommandEvent(new Chat(api, chatId, sender), body));
				}
				else
				{
					api.getEventBus().post(new ChatEvent(new Chat(api, chatId, sender), body, timestamp));
				}
			}
			return "200";
		});
		return this;
	}

	public Webhook stop()
	{
		Spark.stop();
		return this;
	}

	public Webhook setConfig() throws UpdateConfigException
	{
		JSONObject obj = new JSONObject();
		obj.put("webhook", address + ":" + port + path);
		JSONObject body = new JSONObject();
		body.put("manuallySendReadReceipts", false);
		body.put("receiveReadReceipts", false);
		body.put("receiveDeliveryReceipts", false);
		body.put("receiveIsTyping", false);
		obj.put("features", body);

		try
		{
			Unirest.post("https://api.kik.com/v1/config")
			.header("Content-Type", "application/json")
			.header("Authorization", api.getToken())
			.body(obj)
			.asJson();
		}
		catch (UnirestException e)
		{
			throw new UpdateConfigException();
		}
		return this;
	}
}