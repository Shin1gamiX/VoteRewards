package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.GUI.AdminGUI;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

public class VoteRewards implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("voterewards.basic")) {
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
			return true;
		}
		if (args.length == 0) {
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8&l ==== &6&lVoteRewards &8&l====").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards reload").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards reset").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards clear <player>").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards admin").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/vote").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/rewards").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voteparty").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8&l ==== &6&lVoteRewards &8&l====").send();
			return true;
		}
		if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("voterewards.basic.reload")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
				return true;
			}
			m.getConfig().reload();
			m.getMessages().reload();
			m.getData().reload();
			m.getOffline().reload();
			if (m.getConfig().getBoolean("Options.Reminder.Enabled")) {
				m.reminder().cancel();
				m.reminder();
			}
			m.getServer().getPluginManager().disablePlugin(m);
			m.getServer().getPluginManager().enablePlugin(m);
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &aPlugin reloaded!").send();
			return true;
		} else if (args[0].equalsIgnoreCase("reset")) {
			if (!sender.hasPermission("voterewards.basic.reset")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
				return true;
			}
			m.getVotes().clearVotes();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &aAll player data has been cleared!")
					.send();
			return true;
		} else if (args[0].equalsIgnoreCase("clear")) {
			if (!sender.hasPermission("voterewards.basic.clear")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
				return true;
			}
			if (!(sender instanceof Player)) {
				if (args.length == 1) {
					new Message(sender, MessageType.CHAT_WITH_COLOR,
							"&8[&3VoteRewards&8] &c/voterewards clear <player>!").send();
					return true;
				}
				m.getVotes().resetVote(args[1]);
				new Message(sender, MessageType.CHAT_WITH_COLOR,
						"&8[&3VoteRewards&8] &aSuccessfully cleared " + args[1] + " votes").send();
				return true;
			}
			if (args.length == 1) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &c/voterewards clear <player>!")
						.send();
				return true;
			}
			m.getVotes().resetVote(args[1]);
			new Message(sender, MessageType.CHAT_WITH_COLOR,
					"&8[&3VoteRewards&8] &aSuccessfully cleared " + args[1] + " votes").send();
			return true;

		} else if (args[0].equalsIgnoreCase("set")) {
			if (!sender.hasPermission("voterewards.basic.set")) {
				return true;
			}
			if (args.length == 1) {
				m.getUtils().sendMessage(sender, m.getUtils().color("/votereards set <player>"));
				return true;
			}
			if (args.length == 2) {
				m.getUtils().sendMessage(sender, m.getUtils().color("/votereards set <player>"));
				return true;
			}
			Player target = Bukkit.getPlayer(args[1]);
			int f = Integer.valueOf(args[2]);
			if (target == null) {
				m.getVotes().setVotes(args[1], f);
				m.getUtils().sendMessage(sender,
						m.getUtils().color("&aSuccessfully set " + args[1] + " votes to " + f));
				return true;
			}
			m.getVotes().setVotes(target, f);
			m.getUtils().sendMessage(sender,
					m.getUtils().color("&aSuccessfully set " + target.getName() + " votes to " + f));
		} else if (args[0].equalsIgnoreCase("update")) {
			if (!sender.hasPermission("voterewards.basic.update")) {
				return true;
			}
			try {
				if (m.updateConfigs()) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&aFiles successfully updated!").send();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else if (args[0].equalsIgnoreCase("admin")) {
			if (!sender.hasPermission("voterewards.basic.gui.admin")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&cNo Permissions!");
				return true;
			}
			Player player = (Player) sender;
			player.openInventory(new AdminGUI().getInventory());
			return true;
		} else {
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8&l ==== &6&lVoteRewards &8&l====").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards reload").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards reset").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards clear <player>").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voterewards admin").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/vote").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/rewards").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "§8» &6/voteparty").send();
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8&l ==== &6&lVoteRewards &8&l====").send();
			return true;
		}

		return true;
	}

}
