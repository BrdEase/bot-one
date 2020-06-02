package botone.events;

import java.awt.Color;
import java.util.List;

import botone.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpEvent extends ListenerAdapter {

	private static String comando = "$help";

	public HelpEvent() {
		Bot.addComand(HelpEvent.comando);
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (Bot.isToMe(event.getMessage())) {
			TextChannel channel = event.getChannel();
//			User author = event.getAuthor();

			List<String> msg = Bot.splitMessage(event.getMessage());

			if (msg.get(0).equalsIgnoreCase(HelpEvent.comando) && msg.size() >= 2
					&& msg.get(1).equalsIgnoreCase("$adm")) {

				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Ok, vou te ajudar!");
				eb.setDescription("Comandos $adm:");
				eb.addField("**fale**", "*mande uma mensagem privada pra alguém*", true);
				eb.addField("**sair**", "*desligue o bot*", true);
				eb.addField("**cancele**", "*cancele alguém*", true);
				eb.addField("**libere**", "*descancele alguém*", true);
				eb.setColor(new Color(124, 230, 25));
				channel.sendMessage(eb.build()).queue();

			} else if (msg.get(0).equalsIgnoreCase(HelpEvent.comando)) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Ok, vou te ajudar!");
				eb.setDescription("Comandos e seus usos:");
				eb.addField("**$oi**", "*receba um oi*", true);
				eb.addField("**$help**", "*exibe essa mensagem de ajuda*", true);
				eb.addField("**$bolsonaro**", "*fotos do presidente*", true);
				eb.addField("**$adm**", "*fale, sair, cancele e libere*", true);
				eb.addField("**$suga**", "*receba uma sugada*", true);
				eb.addField("**$blitz**", "*faça uma blitz em alguém*", true);
				eb.setFooter("tente $help $adm");
				eb.setColor(new Color(124, 230, 25));
				channel.sendMessage(eb.build()).queue();
			}

		}

	}

}
