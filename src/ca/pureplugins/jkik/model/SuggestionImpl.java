package ca.pureplugins.jkik.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.interfaces.Chat;
import ca.pureplugins.jkik.interfaces.Suggestion;

public class SuggestionImpl implements Suggestion
{
	private final JSONArray buttons = new JSONArray();
	private final String message;

	public SuggestionImpl(String message)
	{
		this.message = message;
	}

	@Override
	public SuggestionImpl addButton(String text)
	{
		JSONObject button = new JSONObject();
		button.put("type", "text");
		button.put("body", text);
		buttons.put(button);
		return this;
	}

	@Override
	public JSONObject build(Chat chat) throws JSONException, GetUserException
	{
		JSONObject builder = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject body = new JSONObject();
		body.put("chatId", chat.getId());
		body.put("type", "text");
		body.put("to", chat.getSender().getUsername());
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