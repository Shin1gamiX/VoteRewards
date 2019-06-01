package com.GeorgeV22.VoteRewards.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Events.*;

public class CustomEventListeners implements Listener {

	private Main m = Main.getPlugin(Main.class);

	@EventHandler
	public void onVotePartyStart(VotePartyStartEvent e) {
		Player p = e.getPlayer();
		for (Player list : e.getList())
			if (p.isOp()) {
				if (m.getConfig().getBoolean("Debug.Events")) {
					m.getUtils().sendMessage(p, m.getUtils().color("&4&lVotePartyEvent called!"));
					m.getUtils().sendMessage(p, m.getUtils().color("&6Players: &e" + list.getName()));
				}
			}

	}
	
	@EventHandler
	public void onTitle(TitleEvent e) {
		Player p = e.getPlayer();
		if (m.getConfig().getBoolean("Debug.Events")) {
			if (p.isOp()) {
				m.getUtils().sendMessage(p, m.getUtils().color("&4&lTitleEvent called!"));
			}
		}

	}

	@EventHandler
	public void onVote(VoteEvent e) {
		for (Player a : Bukkit.getOnlinePlayers()) {
			if (a.isOp()) {
				if (m.getConfig().getBoolean("Debug.Events")) {
					m.getUtils().sendMessage(a, m.getUtils().color("&4&lVoteEvent called!"));
					m.getUtils().sendMessage(a, m.getUtils().color(m.getUtils()
							.color("&6Player: &e" + e.getUsername() + " &6Service Name: &e" + e.getServiceName())));
				}
			}
		}
	}

	@EventHandler
	public void onOfflineVote(OfflineVoteEvent e) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isOp()) {
				if (m.getConfig().getBoolean("Debug.Events")) {
					m.getUtils().sendMessage(p, m.getUtils().color("&4&lOfflineVoteEvent called!"));
					m.getUtils().sendMessage(p, m.getUtils().color(m.getUtils().color(
							"&6Player: &e" + e.getOfflinePlayer() + " &6Service Name: &e" + e.getServiceName())));
				}
			}
		}
	}

	@EventHandler
	public void onFakeVote(FakeVoteEvent e) {
		Player p = e.getPlayer();
		Player target = e.getTarget();
		if (p == null) {
			ConsoleCommandSender con = e.getSender();
			if (target != null) {
				if (m.getConfig().getBoolean("Debug.Events")) {
					m.getUtils().sendMessage(con, m.getUtils().color("&4&lFakeVoteEvent called!"));
					m.getUtils().sendMessage(con, m.getUtils().color(m.getUtils()
							.color("&6Player: &e" + target.getName() + " &6Service Name: &e" + e.getServiceName())));
				}

			} else {
				if (m.getConfig().getBoolean("Debug.Events")) {
					m.getUtils().sendMessage(con, m.getUtils().color("&4&lFakeVoteEvent called!"));
					m.getUtils().sendMessage(con, m.getUtils().color(m.getUtils().color(
							"&6Player: &e" + e.getOfflineTarget() + " &6Service Name: &e" + e.getServiceName())));
				}
			}

		} else {
			if (target != null) {
				if (p.isOp()) {
					if (m.getConfig().getBoolean("Debug.Events")) {
						m.getUtils().sendMessage(p, m.getUtils().color("&4&lFakeVoteEvent called!"));
						m.getUtils().sendMessage(p, m.getUtils().color(m.getUtils().color(
								"&6Player: &e" + target.getName() + " &6Service Name: &e" + e.getServiceName())));
					}
				}
			} else {
				if (p.isOp()) {
					if (m.getConfig().getBoolean("Debug.Events")) {
						m.getUtils().sendMessage(p, m.getUtils().color("&4&lFakeVoteEvent called!"));
						m.getUtils().sendMessage(p, m.getUtils().color(m.getUtils().color(
								"&6Player: &e" + e.getOfflineTarget() + " &6Service Name: &e" + e.getServiceName())));
					}
				}
			}
		}
	}
}
