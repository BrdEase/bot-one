package botone.events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import botone.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

public class AdmEvent extends ListenerAdapter {

	private static String comando = "$adm";
	private static List<String> adms = new ArrayList<String>(
			Arrays.asList("cadeirante eh xingamento SIM#3017", "TheMortal666#7133", "opora lusco#6056",
					"O N L Y O U#7093", "justking2#0288", "pastel#8256", "Verme Asmático#3601"));
	private static List<String> subComandos = new ArrayList<String>(Arrays.asList("sair", "fale", "cancele", "libere"));
	private static List<CanceladorEvent> canceladores = new ArrayList<CanceladorEvent>();

	public AdmEvent() {
		Bot.addComand(AdmEvent.comando);
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (Bot.isToMe(event.getMessage())) {
			TextChannel channel = event.getChannel();
			Message message = event.getMessage();

			List<String> msg = Bot.splitMessage(event.getMessage());

			if (msg.size() >= 1 && msg.get(0).equalsIgnoreCase(AdmEvent.comando)) {

//				if (Bot.getIgnoreAdm() || AdmEvent.adms.contains(event.getAuthor().getAsTag())) {
				if (Bot.getIgnoreAdm() || event.getMember().getRoles().contains(Bot.admRole)) {

					if (msg.size() >= 2 && AdmEvent.subComandos.contains(msg.get(1).toLowerCase())) {

						if (msg.get(1).equalsIgnoreCase(subComandos.get(0))) {
							sairAction(event, channel);

						} else if (msg.get(1).equalsIgnoreCase(subComandos.get(1))) {
							faleAction(channel, message, msg);

						} else if (msg.get(1).equalsIgnoreCase(subComandos.get(2))) {
							canceleAction(event, msg);

						} else if (msg.get(1).equalsIgnoreCase(subComandos.get(3))) {
							libereAction(event, msg);
						}

					} else {
						EmbedBuilder eb = new EmbedBuilder();
						System.out.println(msg.get(1));
						System.out.println(subComandos.get(1));
						eb.setTitle("Não entendi a segunda palavra");
						eb.setColor(new Color(230, 25, 46));
						channel.sendMessage(eb.build()).queue();
					}

				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Você não é ADM. Não te obedeço");
					eb.setColor(new Color(230, 25, 46));
					channel.sendMessage(eb.build()).queue();
				}
			}
		}
	}

	private void libereAction(GuildMessageReceivedEvent event, List<String> msg) {
		if (msg.size() >= 3) {

			List<Member> members = event.getMessage().getMentionedMembers();

			for (Member member : members) {

				if (!member.getUser().equals(event.getJDA().getSelfUser())) {

					Boolean liberado = false;
					List<CanceladorEvent> toRemove = new ArrayList<CanceladorEvent>();

					for (CanceladorEvent cancelador : AdmEvent.canceladores) {
						if (cancelador.getMember().equals(member)) {
							cancelador.muteMember(member, false);
							liberado = true;
							event.getJDA().removeEventListener(cancelador);
							toRemove.add(cancelador);
						}
					}

					for (CanceladorEvent c : toRemove) {
						AdmEvent.canceladores.remove(c);
					}

					if (liberado) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Liberei o/a: " + member.getUser().getName());
						eb.setDescription("para cancelar use: $adm cancele @membro");
						eb.setFooter("twitter morre...");
						eb.setColor(new Color(124, 230, 25));
						event.getChannel().sendMessage(eb.build()).queue();
						System.out.println("Liberei o/a: " + member.getUser().getAsTag());
					} else {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Não pude liberar o/a: " + member.getUser().getName());
						eb.setDescription("Membro ainda não foi cancelado");
						eb.setFooter("deixe o twitter agir...");
						eb.setColor(new Color(230, 25, 46));
						event.getChannel().sendMessage(eb.build()).queue();
					}

				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Não pude liberar o/a: " + event.getJDA().getSelfUser().getName());
					eb.setDescription("Eu não posso me liberar");
					eb.setFooter("se é tonto?...");
					eb.setColor(new Color(230, 25, 46));
					event.getChannel().sendMessage(eb.build()).queue();
				}
			}

		} else {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Falha!");
			eb.setDescription("Você não marcou ninguém");
			eb.setFooter("tente novamente");
			eb.setColor(new Color(230, 25, 46));
			event.getChannel().sendMessage(eb.build()).queue();
		}

	}

	private void canceleAction(GuildMessageReceivedEvent event, List<String> msg) {

		if (msg.size() >= 3) {

			List<Member> members = event.getMessage().getMentionedMembers();

			for (Member member : members) {

				if (!member.getUser().equals(event.getJDA().getSelfUser())) {

					Boolean estaNaLista = AdmEvent.estaCancelado(member);

					if (estaNaLista) {

						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Não pude cancelar o/a: " + member.getUser().getName());
						eb.setDescription("Membro já foi cancelado");
						eb.setFooter("devemos perdoar...");
						eb.setColor(new Color(230, 25, 46));
						event.getChannel().sendMessage(eb.build()).queue();

					} else {

						CanceladorEvent canceladorEvent = new CanceladorEvent(member);
						AdmEvent.canceladores.add(canceladorEvent);
						event.getJDA().addEventListener(canceladorEvent);

						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Cancelei o/a: " + member.getUser().getName());
						eb.setDescription("para liberar use: $adm libere @membro");
						eb.setFooter("twitter vive...");
						eb.setColor(new Color(124, 230, 25));
						event.getChannel().sendMessage(eb.build()).queue();
						System.out.println("Cancelei o/a: " + member.getUser().getAsTag());
					}

				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Não pude cancelar o/a: " + event.getJDA().getSelfUser().getName());
					eb.setDescription("Eu não posso me cancelar");
					eb.setFooter("se é tonto?...");
					eb.setColor(new Color(230, 25, 46));
					event.getChannel().sendMessage(eb.build()).queue();
				}

			}

		} else {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Falha!");
			eb.setDescription("Você não marcou ninguém");
			eb.setFooter("tente novamente");
			eb.setColor(new Color(230, 25, 46));
			event.getChannel().sendMessage(eb.build()).queue();
		}

	}

	public static Boolean estaCancelado(Member m) {
		Boolean estaNaLista = false;
		for (CanceladorEvent canceladorEvent : AdmEvent.canceladores) {
			if (canceladorEvent.getMember().equals(m)) {
				estaNaLista = true;
				break;
			}
		}
		return estaNaLista;
	}

	private void faleAction(TextChannel channel, Message message, List<String> msg) {

		int mentions = message.getMentionedUsers().size();

		String falar = "";
		for (int i = 2 + mentions; i < msg.size(); i++) {
			falar = falar + " " + msg.get(i);
		}
		falar = falar.replaceFirst(" ", "");

		for (User user : message.getMentionedUsers()) {
			if (!user.equals(message.getJDA().getSelfUser())) {

				RestAction<PrivateChannel> restPrivateChannel = user.openPrivateChannel();
				PrivateChannel pc = restPrivateChannel.complete();

				if (user.hasPrivateChannel()) {
					pc.sendMessage(falar).queue();
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Falei com: " + user.getName());
					eb.setDescription("eu disse: \"" + falar + "\"");
					eb.setFooter("que medo...");
					eb.setColor(new Color(124, 230, 25));
					message.getChannel().sendMessage(eb.build()).queue();
					System.out.println("Falei '" + falar + "' pro: " + user.getAsTag());

				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Não foi possível falar com: " + user.getName());
					eb.setDescription("Esse membro não permitiu abrir conversa privada");
					eb.setFooter("a vida é triste...");
					eb.setColor(new Color(230, 25, 46));
					message.getChannel().sendMessage(eb.build()).queue();
				}

			} else {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Não foi possível falar com: " + message.getJDA().getSelfUser().getName());
				eb.setDescription("Eu não posso falar comigo mesmo");
				eb.setFooter("a vida é triste...");
				eb.setColor(new Color(230, 25, 46));
				message.getChannel().sendMessage(eb.build()).queue();
			}
		}
	}

	private void sairAction(GuildMessageReceivedEvent event, TextChannel channel) {
//		EmbedBuilder eb = new EmbedBuilder();
//		eb.setTitle("Ok, vou sair. tchau!");
//		eb.setColor(new Color(124, 230, 25));
//		channel.sendMessage(eb.build()).queue();
//		System.out.println("Me mandaram sair");
//		event.getJDA().shutdownNow();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("NÃO vou sair!");
		eb.setDescription("Eu estou rodando em um raspberry, se eu for parado não iniciarei novamente. "
				+ "Seila pede ajuda do criador que ele tira o raspberry da tomada");
		eb.setColor(new Color(230, 25, 46));
		channel.sendMessage(eb.build()).queue();
		System.out.println("Me mandaram sair, mas não vou.");
	}
}
