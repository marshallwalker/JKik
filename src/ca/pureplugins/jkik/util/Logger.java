package ca.pureplugins.jkik.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
	public enum Level
	{
		COMMAND,
		INFO,
		ERROR,
	}

	public void log(Level level, Object object)
	{
		String date = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
		String log = String.format("%s | %s | %s", date, level.name(), object);

		switch (level)
		{
		case ERROR:
			System.err.println(log);
			break;

		case INFO:
		default:
			System.out.println(log);
			break;
		}
	}
}