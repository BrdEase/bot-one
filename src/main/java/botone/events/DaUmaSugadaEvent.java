package botone.events;

import java.awt.Color;
import java.util.List;

import botone.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DaUmaSugadaEvent extends ListenerAdapter {

	private static String comando = "$suga";

	public DaUmaSugadaEvent() {
		Bot.addComand(DaUmaSugadaEvent.comando);
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (Bot.isToMe(event.getMessage())) {

			TextChannel channel = event.getChannel();
//			String message = event.getMessage().getContentRaw();
			User author = event.getAuthor();

			List<String> msg = Bot.splitMessage(event.getMessage());

			if (msg.get(0).equalsIgnoreCase(DaUmaSugadaEvent.comando)) {

				List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

				if (mentionedMembers.isEmpty()) {

					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Sugado: " + event.getMember().getNickname() + " , Ta OK!?");
					eb.addField("", event.getMember().getAsMention(), true);
					eb.setColor(new Color(124, 230, 25));
					channel.sendMessage(eb.build()).queue();
//					channel.sendMessage("Suguei o " + author.getAsMention() + ", Ta OK!?").queue();

					System.out.println("Suguei o: " + author.getAsTag());
				} else {
					for (Member member : mentionedMembers) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Sugado: " + member.getNickname() + " , Ta OK!?");
						eb.addField("", member.getAsMention(), true);
						eb.setColor(new Color(124, 230, 25));
						channel.sendMessage(eb.build()).queue();
						System.out.println(
								"Suguei o: " + member.getUser().getAsTag() + " a mando do: " + author.getAsTag());
					}

				}

			}
		}
	}
}
