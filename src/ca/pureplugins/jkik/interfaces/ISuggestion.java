package ca.pureplugins.jkik.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import ca.pureplugins.jkik.exception.GetUserException;

public interface ISuggestion
{
	ISuggestion addButton(String text);

	JSONObject build(IChat chat, int delay) throws JSONException, GetUserException;
}