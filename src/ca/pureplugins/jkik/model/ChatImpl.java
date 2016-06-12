package ca.pureplugins.jkik.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.exception.MessageNotSentException;
import ca.pureplugins.jkik.exception.SuggestionNotSentException;
import ca.pureplugins.jkik.interfaces.Chat;
import ca.pureplugins.jkik.interfaces.Suggestion;
import ca.pureplugins.jkik.interfaces.User;
import lombok.Getter;

public class ChatImpl implements Chat
{
	@Getter
	private final KikAPI api;
	private final String chatId;
	private final String sender;
	private final List<String> participants;
	
	public ChatImpl(KikAPI api, String chatId, String username, List<String> participants)
	{
		this.api = api;
		this.chatId = chatId;
		this.sender = username;
		this.participants = participants;
	}
	
	@Override
	public String getId()
	{
		return chatId;
	}

	@Override
	public void sendSuggestion(Suggestion suggestion) throws SuggestionNotSentException, JSONException, GetUserException
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
			throw new SuggestionNotSentException();
		}
	}

	@Override
	public void sendMessage(String message) throws MessageNotSentException
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
			throw new MessageNotSentException();
		}
	}
	
	@Override
	public User getSender() throws GetUserException
	{
		return new UserImpl(api, sender);
	}

	@Override
	public List<String> getParticipants()
	{
		return participants;
	}
}