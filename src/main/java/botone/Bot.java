package botone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

import botone.events.AdmEvent;
import botone.events.AdmRoleEvent;
import botone.events.BlitzEvent;
import botone.events.BolsonaroComPicaNaBocaEvent;
import botone.events.DaUmaSugadaEvent;
import botone.events.HelloEvent;
import botone.events.HelpEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

public class Bot {

	private static List<String> comandos = new ArrayList<String>();

	private static Boolean ignoreAdm = false;
	private static Boolean ignoreChannel = false;
	public static Role admRole = null;

	public static void main(String[] args) throws LoginException, InterruptedException {

		// JDABuilder builder = JDABuilder.createDefault("TOKEN");
		builder.setActivity(Activity.of(ActivityType.WATCHING, "um homem morto"));

		AdmRoleEvent admRoleGetter = new AdmRoleEvent();
		builder.addEventListeners(admRoleGetter);

		JDA jda = builder.build();
		jda.awaitReady();

		while (Bot.getAdmRole() == null) {
		}
		jda.removeEventListener(admRoleGetter);

		jda.addEventListener(new HelloEvent());
		jda.addEventListener(new BolsonaroComPicaNaBocaEvent());
		jda.addEventListener(new DaUmaSugadaEvent());
		jda.addEventListener(new AdmEvent());
		jda.addEventListener(new BlitzEvent());
		jda.addEventListener(new HelpEvent());

	}

	public static Boolean isToMe(Message message) {
		if (!message.getAuthor().isBot()) {

			if (Bot.ignoreChannel
					|| message.getChannel().getName().contentEquals("canal-do-bot-que-um-dia-vai-dominar-o-mundo")) {

				List<String> splitMessage = splitMessage(message);
				if (!splitMessage.isEmpty()) {

					if (Bot.comandos.contains(splitMessage.get(0).toLowerCase())) {

						if (AdmEvent.estaCancelado(message.getMember())) {
							if (message.getContentRaw().contains("root~~")) {
								message.delete();
								return true;
							}
							return false;

						} else {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static List<String> splitMessage(Message message) {
		String parse = message.getContentRaw();
		while (parse.contains("  ")) {
			parse = parse.replaceAll("  ", " ");
		}
		String[] split = parse.split(" ");
		return Arrays.asList(split);
	}

	public static void addComand(String comando) {
		if (!comandos.contains(comando.toLowerCase())) {
			comandos.add(comando.toLowerCase());
		}
	}

	public static void addComand(String... comandos) {
		for (String comando : comandos) {
			if (!Bot.comandos.contains(comando.toLowerCase())) {
				Bot.comandos.add(comando.toLowerCase());
			}
		}
	}

	public static Boolean getIgnoreAdm() {
		return ignoreAdm;
	}

	public static Boolean getIgnoreChannel() {
		return ignoreChannel;
	}

	public static Role getAdmRole() {
		return admRole;
	}

	public static void setAdmRole(Role admRole) {
		Bot.admRole = admRole;
	}

}
