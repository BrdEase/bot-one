package botone.events;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import botone.Bot;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BolsonaroComPicaNaBocaEvent extends ListenerAdapter {

	private static String comando = "$bolsonaro";

	public BolsonaroComPicaNaBocaEvent() {
		Bot.addComand(BolsonaroComPicaNaBocaEvent.comando);

		List<InputStream> fotos = new ArrayList<InputStream>();
		fotos.add(this.getClass().getClassLoader().getResourceAsStream("1.jpg"));
		fotos.add(this.getClass().getClassLoader().getResourceAsStream("2.jpg"));
		fotos.add(this.getClass().getClassLoader().getResourceAsStream("final.jpg"));
		fotos.add(this.getClass().getClassLoader().getResourceAsStream("frase.jpg"));

		String temp = System.getProperty("java.io.tmpdir");

		for (int i = 0; i < fotos.size(); i++) {

			File a = new File(temp + i + ".jpg");
			try {
				FileUtils.copyInputStreamToFile(fotos.get(i), a);
			} catch (IOException e) {
				e.printStackTrace();
			}
			bolsonaro.add(a);
		}

	}

	private static List<File> bolsonaro = new ArrayList<File>();

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (Bot.isToMe(event.getMessage())) {

			TextChannel channel = event.getChannel();
//			String message = event.getMessage().getContentRaw();
			User author = event.getAuthor();

			List<String> msg = Bot.splitMessage(event.getMessage());

			if (msg.get(0).equalsIgnoreCase(BolsonaroComPicaNaBocaEvent.comando)) {
				Collections.shuffle(bolsonaro);
				channel.sendFile(bolsonaro.get(0)).queue();
				System.out.println("O bolsonaro chupou uma pica a mando do: " + author.getAsTag());
			}
		}
	}
}
