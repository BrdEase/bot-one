package botone.events;

import botone.Bot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CanceladorEvent extends ListenerAdapter {

	private Member member;

	public CanceladorEvent(Member member) {
		this.member = member;
		muteMember(member, true);
	}

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		if (Bot.getIgnoreChannel()
				|| event.getChannel().getName().contentEquals("canal-do-bot-que-um-dia-vai-dominar-o-mundo")) {

			if (event.getAuthor().equals(member.getUser())) {
				Message message = event.getMessage();
				message.delete().queue();
			}

		}

	}

	public void onGuildVoiceMute(GuildVoiceMuteEvent event) {
		muteMember(member, true);
	}

	public void muteMember(Member member, Boolean mutar) {
		if (member.getVoiceState().getChannel() != null) {
			member.mute(mutar).complete();
		}
	}

	public Member getMember() {
		return member;
	}

}
