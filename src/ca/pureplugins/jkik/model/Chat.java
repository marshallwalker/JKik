package ca.pureplugins.jkik.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.util.Logger.Level;
import lombok.Data;

@Data
public class Chat
{
	private final KikAPI api;
	private final String chatId;
	private final String sender;

	public void sendMessage(String message)
	{
		try
		{
			URL url = new URL("https://api.kik.com/v1/message");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.addRequestProperty("Authorization", api.getToken());
			con.setDoOutput(true);

			DataOutputStream writer = new DataOutputStream(con.getOutputStream());

			String json = "{\"messages\": [{\"body\": \"" + message + "\", \"to\": \"" + sender + "\", \"type\": \"text\", \"chatId\": \"" + chatId + "\"}]}";

			writer.write(json.getBytes());
			writer.flush();
			writer.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			StringBuffer response = new StringBuffer();

			String line;
			while ((line = in.readLine()) != null)
				response.append(line);

			in.close();
			// System.out.println(response.toString());
		}
		catch (IOException e)
		{
			api.getLogger().log(Level.ERROR, "Unable to send chat message, " + e.getLocalizedMessage());
		}
	}
}