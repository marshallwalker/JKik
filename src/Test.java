import ca.pureplugins.jkik.KikAPI;
import ca.pureplugins.jkik.event.ChatEvent;

public class Test
{
	public static void main(String[] args)
	{
		KikAPI api = new KikAPI("purepluginsbot", "b0e83557-905a-4d91-aa48-5925ec59d9c1");
		api.start("http://24.108.215.161", 8685, "/abc");

		api.getEventBus().register(ChatEvent.class, event ->
		{
			System.out.println(event.getMessage());
		});
	}
}