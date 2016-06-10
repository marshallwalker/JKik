#JKik
JKik is a simple Java kik api for creating bots.

# Auth key
To get an auth key visit https://dev.kik.com/#/home


# List of Events

- ChatEvent
- CommandEvent

#Creating an instance of the api

```java

KikAPI api = new KikAPI("username", "auth-key");
api.start("http://your-address", 8686, "/webhook");

```

# Registering a listener

This evenbus is thread-safe & reflectionless

```java

api.getEventBus().register(ChatEvent.class, event ->
{
	event.getChat().sendMessage("Hello " + event.getChat().getSender());
});

```

#Creating suggestions

Suggestions will replace the keyboard with buttons,
then you click these buttons, it will send the defined text.

```java
Suggestion suggestions = new Suggestion("How are you?");

suggestions.addButton("Good :)");
suggestions.addButton("Not so good :(");

getChat().sendSuggestion(suggestions);

```

# Creating a command

```java

public class TestCommand extends Command
{
	public TestCommand()
	{
		super("test");
	}
	
	@Override
	public void execute()
	{
		getChat().sendMessage("Hello world!");
	}
}

public class Main
{
	public static void main(String[] args) throws Exception
	{
		KikAPI api = new KikAPI("username", "auth-key");
		api.start("http://your-address", 8686, "/webhook");
		
		api.getCommandBus().register(new TestCommand());
	}
}

```