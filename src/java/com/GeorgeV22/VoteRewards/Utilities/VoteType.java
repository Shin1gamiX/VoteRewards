package com.GeorgeV22.VoteRewards.Utilities;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

public class VoteType {

	private Main m = Main.getPlugin(Main.class);

	public void processVote(Player p, String service_name) {
		if (m.getConfig().getBoolean("Options.ChatMessage"))

			if (m.getConfig().getString("Options.ChatMessageType").equals("BROADCAST"))
				Bukkit.getServer().broadcastMessage(m.getUtils().color(m.getMessages().getString("Basics.Vote Rewards"))
						.replace("%servicename%", service_name).replace("%player%", p.getName()));
			else if (m.getConfig().getString("Options.ChatMessageType").equals("PRIVATE"))
				new Message(p, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("Basics.Vote Rewards")
						.replace("%servicename%", service_name).replace("%player%", p.getName())).send();
			else
				Bukkit.getServer().broadcastMessage(m.getUtils().color(m.getMessages().getString("Basics.Vote Rewards"))
						.replace("%servicename%", service_name).replace("%player%", p.getName()));

		if (m.getConfig().get("Rewards.services." + service_name) == null) {

			for (String s : m.getConfig().getStringList("Rewards.services.default"))
				executeCommands(s.replace("%player%", p.getName()));
		} else {
			for (String s : m.getConfig().getStringList("Rewards.services." + service_name))
				executeCommands(s.replace("%player%", p.getName()));
		}
		m.addMessage("New vote by " + p.getName() + " Service Name: " + service_name);
		processComulativeVote(p);
		processLuckyVote(p);
		processDailyVote(p);
		processPermVote(p);
		addPlayerToList(p);
	}

	private void processComulativeVote(Player player) {
		if (m.getConfig().getBoolean("Options.Cumulative")) {
			for (String s : m.getConfig().getConfigurationSection("Rewards.cumulative").getKeys(false)) {
				if (m.getVotes().getVotes(player).equals(Integer.valueOf(s))) {
					for (String b : m.getConfig().getStringList("Rewards.cumulative." + s)) {
						executeCommands(b.replace("%player%", player.getName()));
					}
					m.addMessage("Processed comulative vote by " + player.getName() + " with " + s + " votes");
				}
			}

		}
	}

	private void processDailyVote(Player player) {
		if (m.getConfig().getBoolean("Options.Daily")) {
			int b1 = m.getData().getInt("Players." + player.getUniqueId().toString() + ".dailyvotes");
			m.getData().set("Players." + player.getUniqueId().toString() + ".dailyvotes", b1 + 1);
			m.getData().save();
			for (String s : m.getConfig().getConfigurationSection("Rewards.daily").getKeys(false)) {
				if (b1 == Integer.valueOf(s)) {
					for (String b : m.getConfig().getStringList("Rewards.daily." + s)) {
						executeCommands(b.replace("%player%", player.getName()));
					}
					m.addMessage("Processed daily vote by" + player.getName() + " with " + s + " votes");
				}
			}
		}

	}

	private void processLuckyVote(Player p) {
		if (m.getConfig().getBoolean("Options.Lucky")) {
			for (String s : m.getConfig().getConfigurationSection("Rewards.lucky").getKeys(false)) {
				Random rand = new Random();

				for (String b : m.getConfig().getStringList("Rewards.lucky." + s)) {
					if (rand.nextInt(100) < m.getConfig().getInt("Rewards.lucky" + s)) {
						executeCommands(b.replace("%player%", p.getName()));
					}
				}
				m.addMessage("Processed lucky vote by " + p.getName());

			}
		}
	}

	private void processPermVote(Player p) {
		if (m.getConfig().getBoolean("Options.PermVote")) {
			for (String s : m.getConfig().getConfigurationSection("Rewards.perm").getKeys(false)) {
				if (p.hasPermission("voterewards.reward." + s)) {
					for (String cmds : m.getConfig().getStringList("Rewards.perm." + s))
						executeCommands(cmds.replace("%player%", p.getName()));
					m.addMessage("Processed perm vote by " + p.getName() + " with permission: " + s);
				}
			}
		}
	}

	private void addPlayerToList(Player p) {
		if (m.getConfig().getBoolean("VoteParty.Participate")) {
			if (!m.getVotes().playerList.contains(p)) {
				m.getVotes().playerList.add(p);
				m.addMessage("Added " + p.getName() + " to paticipate voteparty list");
			}
		}
	}

	private void executeCommands(String command) {
		TaskChain.newChain().add(new TaskChain.GenericTask() {
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}
		}).execute();
	}

}
