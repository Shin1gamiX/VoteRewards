package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Sound;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

import java.util.Collections;
import java.util.List;

public class VoteParty implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (m.getConfig().getBoolean("Options.VoteParty")) {
			if (!sender.hasPermission("voterewards.voteparty.use")) {
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
				return true;
			}
			if (args.length == 0) {
				new Message(sender, MessageType.CHAT_WITH_COLOR,
						m.getMessages().getString("VoteParty.Need").replace("%votes%", String.valueOf(
								m.getConfig().getInt("VoteParty.Votes") - m.getData().getInt("VoteParty_Votes"))))
										.send();
				return true;
			} else if (args[0].equalsIgnoreCase("start")) {
				if (!sender.hasPermission("voterewards.voteparty.start") | !!sender.getName().equals("Moosey74")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return true;
				}
				ItemStack d = new ItemStack(Material.PISTON_BASE);
				ItemMeta f = d.getItemMeta();
				f.setDisplayName(m.getUtils().color(m.getConfig().getString("VoteParty.Crate.Display Name")));
				List<String> a = m.getConfig().getStringList("VoteParty.Crate.Lores");
				for (String s : a) {
					f.setLore(Collections.singletonList(m.getUtils().color(s)));
				}
				d.setItemMeta(f);
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.getInventory().addItem(d);
					m.getUtils().PlaySound(all, Sound.valueOf(m.getConfig().getString("Sounds.CrateGive")).playSound(),
							1, 1);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("give")) {
				if (!sender.hasPermission("voterewards.voteparty.give")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return true;
				}
				if (args.length == 1) {
					new Message(sender, MessageType.CHAT_WITH_COLOR,
							"&8[&3VoteRewards&8] &f/voteparty give <player> <amount>").send();
					return true;
				} else if (args.length == 2) {
					new Message(sender, MessageType.CHAT_WITH_COLOR,
							"&8[&3VoteRewards&8] &f/voteparty give <player> <amount>").send();
					return true;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if (target == null) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4Offline Player").send();
					return true;
				}

				try {
					int b = Integer.valueOf(args[2]);
					new com.GeorgeV22.VoteRewards.Utilities.VoteParty().crate(target, b);
					if (b == 1) {
						new Message(target, MessageType.CHAT_WITH_COLOR,
								m.getMessages().getString("VoteParty.Give").replace("%amount%", String.valueOf(b)))
										.send();
					} else {
						new Message(target, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("VoteParty.Give")
								.replace("%amount%", String.valueOf(b)).replace("VoteCrate", "VoteCrates")).send();
					}
					m.getUtils().PlaySound(target,
							Sound.valueOf(m.getConfig().getString("Sounds.CrateGive")).playSound(), 1, 1);

				} catch (Exception e) {
					e.printStackTrace();
					return true;
				}

			} else if (args[0].equalsIgnoreCase("crate")) {

				if (!sender.hasPermission("voterewards.voteparty.crate")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return true;
				}
				if (args.length == 1) {
					Player p = (Player) sender;
					new com.GeorgeV22.VoteRewards.Utilities.VoteParty().crate(p);
					m.getUtils().PlaySound(p, Sound.valueOf(m.getConfig().getString("Sounds.CrateGive")).playSound(), 1,
							1);
					return true;
				}
				if (args[1].equalsIgnoreCase("all")) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						new com.GeorgeV22.VoteRewards.Utilities.VoteParty().crate(all);
						m.getUtils().PlaySound(all,
								Sound.valueOf(m.getConfig().getString("Sounds.CrateGive")).playSound(), 1, 1);
					}
					return true;

				} else {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &c/voteparty help").send();
					return true;
				}

			} else if (args[0].equalsIgnoreCase("help")) {
				if (!sender.hasPermission("voterewards.voteparty.help")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return true;
				}
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8 ==== &6VoteParty &8====").send();
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&7/voteparty give <player> [amount]").send();
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&7/voteparty start").send();
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&7/voteparty claim").send();
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&7/voteparty crate [all]").send();
				new Message(sender, MessageType.CHAT_WITH_COLOR, "&8 ==== &6VoteParty &8====").send();
				return true;
			} else if (args[0].equalsIgnoreCase("claim")) {
				if (!sender.hasPermission("voterewards.voteparty.claim")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return true;
				}
				Player p = (Player) sender;
				int f = m.getData().getInt("Players." + p.getName() + ".voteparty");
				if (f > 0) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("VoteParty.Claim")
							.replace("%player%", p.getName()).replace("%crates%", String.valueOf(f))).send();
					new com.GeorgeV22.VoteRewards.Utilities.VoteParty().crate((Player) sender, f);
					m.getData().set("Players." + p.getName() + ".voteparty", 0);
					m.getData().save();
				} else {
					new Message(sender, MessageType.CHAT_WITH_COLOR,
							m.getMessages().getString("VoteParty.NothingToClaim").replace("%player%", p.getName()))
									.send();
				}

			}
			return true;
		} else {
			new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4VoteParty is not enabled").send();
			return true;
		}

	}

}
