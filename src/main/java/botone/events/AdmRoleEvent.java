package botone.events;

import botone.Bot;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AdmRoleEvent extends ListenerAdapter {
	public void onGenericGuild(GenericGuildEvent event) {
		Role role = event.getGuild().getRoleById(Long.parseLong("716738378435002400"));

		if (role != null) {
			System.out.println("ADM Role detectada!");
			Bot.setAdmRole(role);
		} else {
			System.out.println("A ADM Role não foi encontrada!");
		}

	}
}
