package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

import java.util.List;

public class Vote implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!sender.hasPermission("voterewards.vote")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return;
				}
				List<String> vote = m.getMessages().getStringList("Basics.Vote");

				for (String s : vote) {
					if (!(sender instanceof Player)) {
						ConsoleCommandSender consoleCommandSender = (ConsoleCommandSender) sender;
						new Message(consoleCommandSender, MessageType.CHAT_WITH_COLOR,
								s.replace("%player%", "Console").replace("%votes%", String.valueOf(0))).send();
						return;
					}
					Player p = (Player) sender;
					if (m.getData().get("Players." + p.getUniqueId().toString()) == null) {
						new Message(sender, MessageType.CHAT_WITH_COLOR,
								s.replace("%player%", sender.getName()).replace("%votes%", String.valueOf(0))).send();
						return;
					} else {
						new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%player%", sender.getName())
								.replace("%votes%", String.valueOf(m.getVotes().getVotes(p)))).send();
					}
				}
			}
		}).start();

		return true;

	}

}
