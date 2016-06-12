package ca.pureplugins.jkik.interfaces;

import java.util.List;

import org.json.JSONException;

import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.exception.MessageNotSentException;
import ca.pureplugins.jkik.exception.SuggestionNotSentException;

public abstract interface Chat
{
	String getId();

	void sendMessage(String message) throws MessageNotSentException;

	void sendSuggestion(Suggestion suggestion) throws SuggestionNotSentException, JSONException, GetUserException;

	User getSender() throws GetUserException;

	List<String> getParticipants();
}