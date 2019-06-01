package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

import net.md_5.bungee.api.ChatColor;

public class Debug implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("voterewards.debug")) {
			return true;
		}
		if (args.length == 0) {
			m.getUtils().sendMessage(sender, m.getUtils().color("&cDo not use this command!"));
			return true;
		}
		if (args[0].equalsIgnoreCase("gui")) {
			// new AdminGUI(((Player) sender)).openGUI();
		} else if (args[0].equalsIgnoreCase("votetop")) {
			try {
				if (m.getConfig().getBoolean("Options.VoteTop.Header")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("Basics.VoteTop.Header"))
							.send();
				}
				if (m.MySQL()) {
					m.getVotes().getTopPlayers(sender, 4);
				} else {
					for (int i = 1; i <= 5; i++) {
						String top = m.getVotes().getTopVotePlayer(i);
						if (top != null) {
							for (String s : m.getMessages().getStringList("Basics.VoteTop.Body")) {
								new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%top_players_name%", top)
										.replace("%top_player_votes%", String.valueOf(m.getVotes().getTopVoteInt(i))))
												.send();

							}

						} else {
							for (String s : m.getMessages().getStringList("Basics.VoteTop.Body")) {
								new Message(sender, MessageType.CHAT_WITH_COLOR, s.replace("%top_players_name%", "None")
										.replace("%top_player_votes%", String.valueOf(0))).send();

							}

						}
					}
				}
				if (m.getConfig().getBoolean("Options.VoteTop.Footer")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("Basics.VoteTop.Footer"))
							.send();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (args[0].equalsIgnoreCase("percentage")) {
			if (args.length == 1) {
				double goal = m.getConfig().getDouble("Options.Vote Goal.goal");
				double b6 = m.getData().getDouble("Total_Votes");
				sender.sendMessage("§6§lYour goal: §8[§r" + color(progress(b6, goal, 50)) + "§8]");
				double percent = b6 * 100 / goal;
				double b = Math.round(percent * 10.0) / 10.0;
				sender.sendMessage("§6§lYour goal in percentage: " + b + "§6§l%");
				return true;
			}
			long b = Long.valueOf(args[1]);
			m.getData().set("Total_Votes", b);
			m.getData().save();
		}

		return true;
	}

	private String progress(double current, double max, double totalBars) {
		final double progressBars = (double) ((double) totalBars * (float) current / max);
		final StringBuilder sb = new StringBuilder("&a");
		for (double i = 0; i < progressBars; i++) {
			sb.append("|");
		}
		sb.append("&c");
		for (double i = 0; i < totalBars - progressBars; i++) {
			sb.append("|");
		}
		return sb.toString();
	}

	private String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}
