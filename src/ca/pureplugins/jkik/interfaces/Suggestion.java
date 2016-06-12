package ca.pureplugins.jkik.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import ca.pureplugins.jkik.exception.GetUserException;

public interface Suggestion
{
	Suggestion addButton(String text);

	JSONObject build(Chat chat) throws JSONException, GetUserException;
}