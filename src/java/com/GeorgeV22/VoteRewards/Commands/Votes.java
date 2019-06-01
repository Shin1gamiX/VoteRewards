package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

import java.util.List;

public class Votes implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!(sender instanceof Player)) {
					if (args.length == 0) {
						new Message(sender, MessageType.CHAT_WITH_COLOR, "&c/votes <player>").send();
						return;
					}
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						List<String> f = m.getMessages().getStringList("Basics.Votes.Target");

						for (String s : f) {
							new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%player%", target.getName())
									.replace("%votes%", String.valueOf(m.getVotes().getVotes(target)))).send();
						}
						return;
					}

					List<String> f = m.getMessages().getStringList("Basics.Votes.Target");
					for (String s : f)
						new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%player%", args[0])
								.replace("%votes%", String.valueOf(m.getVotes().getVotes(args[0])))).send();

					return;
				}
				if (!sender.hasPermission("voterewards.votes")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return;
				}
				if (args.length == 0) {
					Player p = (Player) sender;
					if (m.getData().get("Players." + sender.getName() + ".votes") != null) {
						List<String> f = m.getMessages().getStringList("Basics.Votes.Sender");
						for (String s : f) {
							new Message(sender, MessageType.CHAT_WITH_COLOR,
									s.replace("%votes%", String.valueOf(m.getVotes().getVotes(p)))).send();
						}
					} else {
						new Message(sender, MessageType.CHAT_WITH_COLOR, "&cPlayer not found!").send();
						return;
					}
					return;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					List<String> f = m.getMessages().getStringList("Basics.Votes.Target");
					for (String s : f) {
						new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%player%", target.getName())
								.replace("%votes%", String.valueOf(m.getVotes().getVotes(target)))).send();
					}
					return;
				}
				if (m.getData().get("Players." + args[0] + ".votes") != null) {
					List<String> f = m.getMessages().getStringList("Basics.Votes.Target");
					for (String s : f) {
						new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%player%", args[0])
								.replace("%votes%", String.valueOf(m.getVotes().getVotes(args[0])))).send();
					}
				} else {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&cPlayer not found!").send();
					return;
				}
			}
		}).start();

		return true;
	}

}
