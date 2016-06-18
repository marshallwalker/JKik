package ca.pureplugins.jkik.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.interfaces.IChat;
import ca.pureplugins.jkik.interfaces.ISuggestion;

public class Suggestion implements ISuggestion
{
	private final JSONArray buttons = new JSONArray();
	private final String message;

	public Suggestion(String message)
	{
		this.message = message;
	}

	@Override
	public Suggestion addButton(String text)
	{
		JSONObject button = new JSONObject();
		button.put("type", "text");
		button.put("body", text);
		buttons.put(button);
		return this;
	}

	@Override
	public JSONObject build(IChat chat, int delay) throws JSONException, GetUserException
	{
		JSONObject builder = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject body = new JSONObject();
		body.put("chatId", chat.getId());
		body.put("type", "text");
		body.put("to", chat.getSender().getUsername());
		body.put("delay", delay);
		body.put("body", message);
		JSONArray keyboards = new JSONArray();
		JSONObject keyboard = new JSONObject();
		keyboard.put("to", chat.getSender().getUsername());
		keyboard.put("type", "suggested");
		keyboard.put("responses", buttons);
		keyboards.put(keyboard);
		body.put("keyboards", keyboards);
		messages.put(body);
		builder.put("messages", messages);
		return builder;
	}
}