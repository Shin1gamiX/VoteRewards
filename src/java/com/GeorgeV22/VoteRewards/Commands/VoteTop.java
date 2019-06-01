package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

public class VoteTop implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("voterewards.votetop")) {
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
			return true;
		}
		if (m.getConfig().getBoolean("Options.VoteTop.Header")) {
			new Message(sender, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("Basics.VoteTop.Header")).send();
		}
		if (m.MySQL()) {
			m.getVotes().getTopPlayers(sender, m.getConfig().getInt("Options.VoteTop.Top") - 1);
		} else {
			for (int i = 1; i <= m.getConfig().getInt("Options.VoteTop.Top"); i++) {

				String top = m.getVotes().getTopVotePlayer(i);
				if (top != null) {
					for (String s : m.getMessages().getStringList("Basics.VoteTop.Body")) {
						new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%top_players_name%", top)
								.replace("%top_player_votes%", String.valueOf(m.getVotes().getTopVoteInt(i)))).send();

					}

				} else {
					for (String s : m.getMessages().getStringList("Basics.VoteTop.Body")) {
						new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%top_players_name%", "None")
								.replace("%top_player_votes%", String.valueOf(0))).send();

					}

				}

			}
		}

		if (!m.MySQL())
			if (m.getConfig().getBoolean("Options.VoteTop.Footer")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("Basics.VoteTop.Footer"))
						.send();
			}
		return true;
	}

}