#JKik
a java kik api

# Auth key
To get an auth key visit https://dev.kik.com/#/home

#Creating an instance of the api

```java

KikAPI api = new KikAPI("username", "auth-token");
api.startWebhook("http://address", "/webhook", port);

```

# Registering a listener

```java

api.getEventBus().register(ChatMessageEvent.class, event ->
{
	event.getChat().sendMessage("Hello " + event.getChat().getSender());
});

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
		KikAPI api = new KikAPI("username", "auth-token");
		api.startWebhook("http://address", "/webhook", port);
		
		api.getCommandBus().register(new TestCommand());
	}
}

```