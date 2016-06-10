package ca.pureplugins.jkik.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Suggestion
{
	private final JSONArray buttons = new JSONArray();
	private final String message;

	public Suggestion(String message)
	{
		this.message = message;
	}

	public Suggestion addButton(String text)
	{
		JSONObject button = new JSONObject();
		button.put("type", "text");
		button.put("body", text);
		buttons.put(button);
		return this;
	}

	public JSONObject build(Chat chat)
	{
		JSONObject builder = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject body = new JSONObject();
		body.put("chatId", chat.getChatId());
		body.put("type", "text");
		body.put("to", chat.getUser().getUsername());
		body.put("body", message);
		JSONArray keyboards = new JSONArray();
		JSONObject keyboard = new JSONObject();
		keyboard.put("to", chat.getUser().getUsername());
		keyboard.put("type", "suggested");
		keyboard.put("responses", buttons);
		keyboards.put(keyboard);
		body.put("keyboards", keyboards);
		messages.put(body);
		builder.put("messages", messages);
		return builder;
	}
}