package ca.pureplugins.jkik.model;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.exception.GetUserException;
import ca.pureplugins.jkik.interfaces.IUser;
import lombok.Getter;

public class UserImpl implements IUser
{
	@Getter
	private final KikAPI api;
	private final String username;
	private JSONObject content;

	public UserImpl(KikAPI api, String username) throws GetUserException
	{
		this.api = api;
		this.username = username;

		try
		{
			HttpResponse<JsonNode> req = Unirest
					.get("https://api.kik.com/v1/user/" + username)
					.header("Content-Type", "application/json")
					.header("Authorization", api.getToken())
					.asJson();

			content = req.getBody().getObject();
		}
		catch (UnirestException e)
		{
			throw new GetUserException();
		}
	}
	
	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public String getFirstName()
	{
		return content.getString("firstName");
	}

	@Override
	public String getLastName()
	{
		return content.getString("lastName");
	}

	@Override
	public String getProfilePicture()
	{
		return content.getString("profilePicUrl");
	}

	@Override
	public long getProfilePictureLastModified()
	{
		return content.getLong("profilePicLastModified");
	}
}