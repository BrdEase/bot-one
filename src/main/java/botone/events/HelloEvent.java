package botone.events;

import java.util.List;

import botone.Bot;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloEvent extends ListenerAdapter {

	private static String comando = "$oi";

	public HelloEvent() {
		Bot.addComand(HelloEvent.comando);
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (Bot.isToMe(event.getMessage())) {

			TextChannel channel = event.getChannel();
//			String message = event.getMessage().getContentRaw();
			User author = event.getAuthor();

			List<String> msg = Bot.splitMessage(event.getMessage());

			if (msg.get(0).equalsIgnoreCase(HelloEvent.comando)) {
				channel.sendMessage("oi " + author.getAsMention() + "!").queue();
				System.out.println("Dei oi pro: " + author.getAsTag());
			}
		}
	}

}
