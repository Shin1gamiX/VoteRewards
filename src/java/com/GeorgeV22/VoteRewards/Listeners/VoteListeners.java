package com.GeorgeV22.VoteRewards.Listeners;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Updater;
import com.GeorgeV22.VoteRewards.Utilities.Sound;
import com.GeorgeV22.VoteRewards.Utilities.UUIDFetcher;
import com.GeorgeV22.VoteRewards.Utilities.VoteParty;
import com.GeorgeV22.VoteRewards.Utilities.XMaterial;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VoteListeners implements Listener {

	private Main m = Main.getPlugin(Main.class);

	// OTAN KANEI VOTE ENAS PLAYER
	@EventHandler
	public void onPlayerVote(VotifierEvent e) {
		new Thread() {
			public void run() {
				Vote v = e.getVote();
				Player p = Bukkit.getPlayer(v.getUsername());
				UUID uuid = UUIDFetcher.getUUID(v.getUsername());
				if (p == null) {
					m.getVotes().processOfflineVote(v.getServiceName(), uuid != null ? uuid.toString()
							: Bukkit.getPlayer(v.getUsername()).getUniqueId().toString());
					if (m.getConfig().getBoolean("Options.VoteParty")) {
						if (m.getConfig().getBoolean("VoteParty.Chat Message")) {
							for (Player all : Bukkit.getOnlinePlayers()) {
								m.getUtils().sendMessage(all,
										m.getUtils()
												.color(m.getMessages().getString("VoteParty.Chat").replace("%votes%",
														String.valueOf(m.getConfig().getInt("VoteParty.Votes")
																- m.getData().getInt("VoteParty_Votes")))));
							}
						}
						new VoteParty().voteParty();
					}
					System.out.println("PROCESS OFFLINE VOTE FOR " + v.getUsername());
					if (m.getConfig().getBoolean("Options.Vote Goal.enable")) {
						double goal = m.getConfig().getDouble("Options.Vote Goal.goal");
						double b6 = m.getData().getDouble("Total_Votes");
						double percent = b6 * 100 / goal;
						double b = Math.round(percent * 10.0) / 10.0;
						double bars = m.getConfig().getDouble("Options.Vote Goal.bars");
						for (String s : m.getMessages().getStringList("Vote.Goal")) {
							for (Player all : Bukkit.getOnlinePlayers()) {
								m.getUtils().sendMessage(all,
										m.getUtils().color(s).replace("{goal}", progress(b6, goal, bars))
												.replace("{percentage}", String.valueOf(b)));

							}
						}

					}
					return;
				}
				m.getVotes().processVote(p, e.getVote());
				m.getVotes().sendVoteTitle(p, v.getServiceName());
				m.getVotes().sendVoteActionbar(p, v.getServiceName());
				if (m.getConfig().getBoolean("Options.VoteParty")) {
					new VoteParty().voteParty();
					if (m.getConfig().getBoolean("VoteParty.Chat Message")) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							m.getUtils().sendMessage(all,
									m.getUtils()
											.color(m.getMessages().getString("VoteParty.Chat").replace("%votes%",
													String.valueOf(m.getConfig().getInt("VoteParty.Votes")
															- m.getData().getInt("VoteParty_Votes")))));
						}
					}
				}
				if (m.getConfig().getBoolean("Options.Vote Goal.enable")) {
					double goal = m.getConfig().getDouble("Options.Vote Goal.goal");
					double b6 = m.getData().getDouble("Total_Votes");
					double percent = b6 * 100 / goal;
					double b = Math.round(percent * 10.0) / 10.0;
					double bars = m.getConfig().getDouble("Options.Vote Goal.bars");
					for (String s : m.getMessages().getStringList("Vote.Goal")) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							m.getUtils().sendMessage(all,
									m.getUtils().color(s).replace("{goal}", progress(b6, goal, bars))
											.replace("{percentage}", String.valueOf(b)));
						}
					}
				}
			};
		}.run();

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
		return m.getUtils().color(sb.toString());
	}

	// ON PLAYER JOIN
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		new BukkitRunnable() {
			@Override
			public void run() {

				List<String> join_player = m.getMessages().getStringList("Join Player");
				m.getVotes().createUser(e.getPlayer());
				for (String s : join_player) {
					m.getUtils().sendMessage(player,
							m.getUtils().color(s).replace("%player%", player.getName()).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + player.getName() + ".votes"))));
				}
				if (m.getConfig().getBoolean("Options.VoteParty")) {
					if (m.getData().getInt("Players." + player.getName() + ".voteparty") > 0) {
						new Message(player, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("VoteParty.Unclaim"),
								null).send();
					}
				}
			}
		}.runTaskLaterAsynchronously(m, 30);
		if (m.getConfig().getBoolean("Options.Reminder.Enabled")) {
			Date date = new Date(m.getData().getLong("Players." + player.getName() + ".time"));
			Date current = new Date(System.currentTimeMillis());
			if (date.getTime() <= current.getTime() - 86400000L) {
				if (m.getConfig().getBoolean("Debug.Reminder"))
					Bukkit.broadcastMessage("DEBUG: REMINDER");
				for (String s : m.getMessages().getStringList("Basics.Reminder"))
					new Message(player, MessageType.CHAT_WITH_COLOR, s.replace("%player%", player.getName())
							.replace("%votes%", String.valueOf(m.getVotes().getVotes(player)))).send();
			}
		}

		if (player.isOp()) {
			if (m.getA() || m.getB()) {
				new Message(player, MessageType.CHAT_WITH_COLOR,
						"&cYou are using old configs! Please use /voterewards update").send();
			}
			if (m.getConfig().getBoolean("Options.Updater")) {
				new Updater(player);
			}
		}

	}

	// OFFLINE VOTE
	@EventHandler
	public void offlineVote(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		List<String> services = m.getOffline().getStringList("Offline." + p.getName() + ".service_names");
		if (m.getOffline().getInt("Offline." + p.getName() + ".times") > 0) {
			int f = m.getOffline().getInt("Offline." + p.getName() + ".times");
			for (int i = 0; i < f; i++) {
				for (String service_name : services) {
					m.getVotes().processOfflineVoteOnJoin(p, service_name);
				}
				if (m.getConfig().getBoolean("Options.VoteParty")) {
					new VoteParty().voteParty();
				}
			}
			m.getOffline().set("Offline." + p.getName() + ".times", 0);
			m.getOffline().set("Offline." + p.getName() + ".service_names", null);
			m.getOffline().save();
		}
	}

	// VOTEPARTY CRATE INTERACT
	@EventHandler
	public void onPlayerInteractCrate(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInHand();

		if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.LEFT_CLICK_BLOCK)
				|| (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || (e.getAction().equals(Action.LEFT_CLICK_AIR))))) {
			if (item != null && item.getType() == XMaterial.PISTON.parseMaterial()) {
				if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
					if (item.getItemMeta().getDisplayName()
							.equals(m.getUtils().color(m.getConfig().getString("VoteParty.Crate.Display Name")))) {
						int i = e.getPlayer().getInventory().getItemInHand().getAmount();
						if (i == 1) {
							e.getPlayer().getInventory().removeItem(item);
						} else {
							e.getPlayer().getInventory().getItemInHand().setAmount(i - 1);
						}
						if (m.getConfig().getBoolean("VoteParty.Sound")) {
							m.getUtils().PlaySound(p,
									Sound.valueOf(m.getConfig().getString("Sounds.CrateOpen")).playSound(), 1, 1);
						}
						if (m.getConfig().getBoolean("VoteParty.Firework")) {
							m.getUtils().firework(p);
						}
						new VoteParty().rewards(p);
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
