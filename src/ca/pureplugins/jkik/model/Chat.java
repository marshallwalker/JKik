package ca.pureplugins.jkik.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.exception.MessageNotSentException;
import ca.pureplugins.jkik.exception.PictureNotSentException;
import ca.pureplugins.jkik.exception.SuggestionNotSentException;
import ca.pureplugins.jkik.interfaces.IChat;
import ca.pureplugins.jkik.interfaces.IUser;
import lombok.Getter;

public class Chat implements IChat
{
	@Getter
	private final KikAPI api;
	private final String chatId;
	private final String sender;

	public Chat(KikAPI api, String chatId, String username)
	{
		this.api = api;
		this.chatId = chatId;
		this.sender = username;
	}

	@Override
	public String getId()
	{
		return chatId;
	}

	@Override
	public void sendSuggestion(Suggestion suggestion) throws SuggestionNotSentException, JSONException, GetUserException
	{
		sendSuggestion(suggestion, 0);
	}

	@Override
	public void sendSuggestion(Suggestion suggestion, int delay) throws SuggestionNotSentException, JSONException, GetUserException
	{
		try
		{
			postData(suggestion.build(this, delay));
		}
		catch (UnirestException e)
		{
			throw new SuggestionNotSentException();
		}
	}
	
	@Override
	public void sendMessage(String message) throws MessageNotSentException
	{
		sendMessage(message, 0);
	}

	@Override
	public void sendMessage(String message, int delay) throws MessageNotSentException
	{
		JSONObject body = new JSONObject();
		body.put("body", message);
		body.put("to", sender);
		body.put("type", "text");
		body.put("chatId", chatId);
		body.put("delay", delay);

		try
		{
			postData(new JSONObject().put("messages", new JSONArray().put(body)));
		}
		catch (UnirestException e)
		{
			throw new MessageNotSentException();
		}
	}

	@Override
	public void sendPicture(String url) throws PictureNotSentException
	{
		sendPicture(url, 0);
	}
	
	@Override
	public void sendPicture(String url, int delay) throws PictureNotSentException
	{
		JSONObject body = new JSONObject();
		body.put("chatId", chatId);
		body.put("type", "picture");
		body.put("to", sender);
		body.put("delay", delay);
		body.put("picUrl", url);

		try
		{
			postData(new JSONObject().put("messages", new JSONArray().put(body)));
		}
		catch (UnirestException e)
		{
			throw new PictureNotSentException();
		}
	}

	@Override
	public IUser getSender() throws GetUserException
	{
		return new UserImpl(api, sender);
	}

	private void postData(JSONObject object) throws UnirestException
	{
		Unirest.post("https://api.kik.com/v1/message")
		.header("Content-Type", "application/json")
		.header("Authorization", api.getToken())
		.body(object)
		.asJson();
	}
}