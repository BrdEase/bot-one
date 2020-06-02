package botone.events;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import botone.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BlitzEvent extends ListenerAdapter {
	private static String comando = "$blitz";

	public BlitzEvent() {
		Bot.addComand(BlitzEvent.comando);
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (Bot.isToMe(event.getMessage())) {
//			TextChannel channel = event.getChannel();
			Message message = event.getMessage();
			List<String> msg = Bot.splitMessage(event.getMessage());

			if (msg.size() >= 1 && msg.get(0).equalsIgnoreCase(BlitzEvent.comando)) {

				if (msg.size() >= 2) {

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					Member policial = event.getMember();
					List<Member> membros = event.getMessage().getMentionedMembers();

					for (Member member : membros) {

						EmbedBuilder eb = new EmbedBuilder();
						String avatar = member.getUser().getAvatarUrl();
						eb.setTitle("Ficha do: " + member.getUser().getName());
						eb.setColor(new Color(124, 230, 25));
						eb.addField("Nome", member.getUser().getName(), true);
						eb.addField("Tag", member.getUser().getAsTag(), true);
						eb.addField("Status Online", member.getOnlineStatus().toString().toLowerCase(), true);
						eb.addField("Foto ", "Aí em baixo: ", true);
						eb.addField("Menção", member.getAsMention(), true);
						eb.setImage(avatar);
						eb.setFooter("Solicitado por: " + policial.getUser().getName() + ". " + formatter.format(date));
						message.getChannel().sendMessage(eb.build()).queue();

						System.out.println("Dei no blitz no: " + member.getUser().getAsTag() + ", a mando do: "
								+ policial.getUser().getAsTag());

					}
				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Não entendi");
					eb.setDescription("Para usar esse comando use: $blitz @membro");
					eb.setFooter("senão não funciona...");
					eb.setColor(new Color(230, 25, 46));
					message.getChannel().sendMessage(eb.build()).queue();
				}

			}

		}
	}
}
