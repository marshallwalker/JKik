package ca.pureplugins.jkik.interfaces;

import org.json.JSONException;

import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.exception.MessageNotSentException;
import ca.pureplugins.jkik.exception.PictureNotSentException;
import ca.pureplugins.jkik.exception.SuggestionNotSentException;
import ca.pureplugins.jkik.model.Suggestion;

public abstract interface IChat
{
	String getId();

	void sendMessage(String message, int delayInMilis) throws MessageNotSentException;

	void sendMessage(String message) throws MessageNotSentException;

	void sendSuggestion(Suggestion suggestion, int delayInMilis) throws SuggestionNotSentException, JSONException, GetUserException;

	void sendSuggestion(Suggestion suggestion) throws SuggestionNotSentException, JSONException, GetUserException;

	void sendPicture(String url, int delayInMilis) throws PictureNotSentException;

	void sendPicture(String url) throws PictureNotSentException;

	IUser getSender() throws GetUserException;
}