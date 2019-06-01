package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Events.FakeVoteEvent;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

public class FakeVote implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (m.getConfig().getBoolean("Options.FakeVote")) {
			if (!(sender instanceof Player)) {
				ConsoleCommandSender con = (ConsoleCommandSender) sender;
				if (args.length == 0) {
					new Message(con, MessageType.CHAT_WITH_COLOR, "&c/fakevote <player> [servicename]").send();
					return true;
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						String p = args[0];
						if (m.getConfig().getBoolean("Options.Offline")) {
							FakeVoteEvent event = new FakeVoteEvent(con, p, null);
							Bukkit.getPluginManager().callEvent(event);

							if (event.isCancelled())
								return true;
							m.getVotes().sendFakeVote(con, p, null);
							return true;
						} else {
							new Message(con, MessageType.CHAT_WITH_COLOR,
									"&8[&3VoteRewards&8] &4Offline Voting is not enabled!").send();
							return true;
						}
					}
					FakeVoteEvent event = new FakeVoteEvent(con, target, null);
					Bukkit.getPluginManager().callEvent(event);

					if (event.isCancelled())
						return true;
					m.getVotes().sendFakeVote(con, target.getName(), null);
					return true;
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[0]);
					String service = args[1];
					if (target == null) {
						String p = args[0];
						if (m.getConfig().getBoolean("Options.Offline")) {
							FakeVoteEvent event = new FakeVoteEvent(con, p, service);
							Bukkit.getPluginManager().callEvent(event);

							if (event.isCancelled())
								return true;
							m.getVotes().sendFakeVote(sender, p, service);
						} else {
							new Message(con, MessageType.CHAT_WITH_COLOR,
									"&8[&3VoteRewards&8] &4Offline Voting is not enabled!").send();

						}
						return true;
					}
					m.getVotes().sendFakeVote(sender, target.getName(), service);
					return true;
				} else if (args.length <= 3) {
					new Message(con, MessageType.CHAT_WITH_COLOR, "&c/fakevote <player> [servicename]").send();
					return true;
				}
				return true;
			}
			if (!sender.hasPermission("voterewards.fakevote")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {

				FakeVoteEvent event = new FakeVoteEvent(p, p, null);
				Bukkit.getPluginManager().callEvent(event);

				if (event.isCancelled())
					return true;
				m.getVotes().sendFakeVote(sender, sender.getName(), null);
				return true;
			} else if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					String offline_target = args[0];
					if (m.getConfig().getBoolean("Options.Offline")) {
						FakeVoteEvent event = new FakeVoteEvent(p, offline_target, null);
						Bukkit.getPluginManager().callEvent(event);

						if (event.isCancelled())
							return true;
						m.getVotes().sendFakeVote(sender, offline_target, null);
						return true;
					} else {
						new Message(sender, MessageType.CHAT_WITH_COLOR,
								"&8[&3VoteRewards&8] &4Offline Voting is not enabled!").send();
						return true;
					}
				}
				FakeVoteEvent event = new FakeVoteEvent(p, target, null);
				Bukkit.getPluginManager().callEvent(event);

				if (event.isCancelled())
					return true;
				m.getVotes().sendFakeVote(sender, target.getName(), null);
				return true;
			} else if (args.length == 2) {

				Player target = Bukkit.getPlayer(args[0]);

				String service = args[1];
				if (target == null) {
					String offline_target = args[0];
					if (m.getConfig().getBoolean("Options.Offline")) {
						FakeVoteEvent event = new FakeVoteEvent(p, offline_target, service);
						Bukkit.getPluginManager().callEvent(event);

						if (event.isCancelled())
							return true;

						m.getVotes().sendFakeVote(sender, offline_target, service);

						return true;
					} else {
						new Message(sender, MessageType.CHAT_WITH_COLOR,
								"&8[&3VoteRewards&8] &4Offline Voting is not enabled!").send();
						return true;
					}
				}
				FakeVoteEvent event = new FakeVoteEvent(p, target, service);
				Bukkit.getPluginManager().callEvent(event);

				if (event.isCancelled())
					return true;

				m.getVotes().sendFakeVote(sender, target.getName(), service);
				return true;
			} else if (args.length <= 3) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&c/fakevote [player] [servicename]").send();
				return true;
			}
			return true;
		} else {
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4FakeVote is not enabled!").send();
		}

		return true;
	}

}
