package ca.pureplugins.jkik.model;

import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ca.pureplugins.jkik.KikAPI;
import lombok.Data;

@Data
public class Chat
{
	private final KikAPI api;
	private final String chatId;
	private final String sender;

	public void sendSuggestion(Suggestion suggestion)
	{
		try
		{
			Unirest.post("https://api.kik.com/v1/message")
			.header("Content-Type", "application/json")
			.header("Authorization", api.getToken())
			.body(suggestion.build(this))
			.asJson();
		}
		catch (UnirestException e)
		{
			api.getLogger().log(Level.SEVERE, "Unable to send suggestion", e);
		}
	}

	public void sendMessage(String message)
	{
		JSONObject wrapper = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject body = new JSONObject();
		body.put("body", message);
		body.put("to", sender);
		body.put("type", "text");
		body.put("chatId", chatId);
		messages.put(body);
		wrapper.put("messages", messages);

		try
		{
			Unirest.post("https://api.kik.com/v1/message")
			.header("Content-Type", "application/json")
			.header("Authorization", api.getToken())
			.body(wrapper)
			.asJson();
		}
		catch (UnirestException e)
		{
			api.getLogger().log(Level.SEVERE, "Unable to send chat message", e);
		}
	}
}