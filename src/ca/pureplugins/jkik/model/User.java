package ca.pureplugins.jkik.model;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ca.pureplugins.jkik.KikAPI;
import lombok.Getter;

public class User
{
	@Getter
	private final KikAPI api;
	
	@Getter
	private final String username;
	
	private JSONObject content;
	
	public User(KikAPI api, String username)
	{
		this.api = api;
		this.username = username; 
		
		try
		{
			HttpResponse<JsonNode> req = Unirest.get("https://api.kik.com/v1/user/" + username)
			.header("Content-Type", "application/json")
			.header("Authorization", api.getToken())
			.asJson();
			
			content = req.getBody().getObject();
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isAdmin()
	{
		return false;
	}
	
	public String getFirstName()
	{
		return content.getString("firstName");
	}
	
	public String getLastName()
	{
		return content.getString("lastName");
	}
	
	public String getPictureUrl()
	{
		return content.getString("profilePicUrl");
	}
	
	public long getPictureLastModified()
	{
		return content.getLong("profilePicLastModified");
	}
}