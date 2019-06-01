package com.GeorgeV22.VoteRewards.Utilities;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Events.TitleEvent;
import com.GeorgeV22.VoteRewards.Events.VotePartyStartEvent;

public class VoteParty {

	public void countdown(Player p, int cd) {
		m.addMessage("Vote Party countdown started");
		new BukkitRunnable() {
			@Override
			public void run() {
				if (m.getConfig().getBoolean("VoteParty.Crate.Sound")) {
					m.getUtils().PlaySound(p,
							Sound.valueOf(m.getConfig().getString("Sounds.VotePartyStart")).playSound(), 1000, 1);
				}
				if (m.getConfig().getBoolean("VoteParty.Crate.enable")) {
					crate(p);
					m.addMessage(p.getName() + " received voteparty crate");
				} else {
					rewards(p);
					m.addMessage(p.getName() + " received voteparty rewards");
				}
			}
		}.runTaskLater(m, cd * 20);
	}

	private Main m = Main.getPlugin(Main.class);

	public void rewards(Player p1) {
		if (m.getConfig().getBoolean("VoteParty.Random Rewards")) {
			executeCommands(m.getUtils().chooseRandom().replace("%player%", p1.getName()).replace("&", "§"));
			m.getUtils().sendMessage(p1, m.getUtils().color(m.getMessages().getString("VoteParty.Crate.Open")));
		} else {
			List<String> rewards = m.getConfig().getStringList("VoteParty.Commands");
			for (String s : rewards)
				executeCommands(s.replace("%player%", p1.getName()).replace("&", "§"));
			m.getUtils().sendMessage(p1, m.getUtils().color(m.getMessages().getString("VoteParty.Crate.Open")));
		}
	}

	private void executeCommands(String command) {
		TaskChain.newChain().add(new TaskChain.GenericTask() {
			public void run() {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}
		}).execute();
	}

	public void voteParty() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (m.getData().getInt("VoteParty_Votes") >= m.getConfig().getInt("VoteParty.Votes")) {
					m.getData().set("VoteParty_Votes", 0);
					m.getData().save();
					Bukkit.getLogger().warning("[VoteRewards] Starting VoteParty");
					for (Player all : Bukkit.getOnlinePlayers()) {
						m.getUtils().sendMessage(all,
								m.getUtils().color(m.getMessages().getString("VoteParty.Cooldown start").replace(
										"%secs%", String.valueOf(m.getConfig().getInt("VoteParty.Crate.Secs")))));
						if ((m.getConfig().getBoolean("VoteParty.Participate"))
								&& (!m.getVotes().playerList.contains(all))) {
							m.getUtils().sendMessage(all,
									m.getUtils().color(m.getMessages().getString("VoteParty.Not Voted")));
						}
					}
					if (m.getConfig().getBoolean("VoteParty.Participate")) {
						for (Player player : m.getVotes().playerList) {
							if (player.isOnline()) {
								if (m.getConfig().getBoolean("VoteParty.Crate.Cooldown")) {
									int secs = m.getConfig().getInt("VoteParty.Crate.Secs");

									if (m.getConfig().getBoolean("Options.Title")) {
										TitleEvent event = new TitleEvent(player,
												m.getUtils().color(m.getMessages().getString("VoteParty.Title"))
														.replace("%secs%", String.valueOf(secs)),
												m.getUtils().color(m.getMessages().getString("VoteParty.Subtitle"))
														.replace("%secs%", String.valueOf(secs)),
												4, 20, 4);
										Bukkit.getPluginManager().callEvent(event);
										if (event.isCancelled())
											return;
										m.getUtils().sendTitle(player,
												m.getUtils().color(m.getMessages().getString("VoteParty.Title"))
														.replace("%secs%", String.valueOf(secs)),
												m.getUtils().color(m.getMessages().getString("VoteParty.Subtitle"))
														.replace("%secs%", String.valueOf(secs)));
									}
									countdown(player, secs);

								} else {
									if (m.getConfig().getBoolean("VoteParty.Crate.Sound")) {
										m.getUtils().PlaySound(player, Sound
												.valueOf(m.getConfig().getString("Sounds.VotePartyStart")).playSound(),
												1000, 1);
									}
									if (m.getConfig().getBoolean("VoteParty.Crate.enable")) {
										crate(player);
										m.addMessage(player.getName() + " received voteparty crate");
									} else {
										rewards(player);
										m.addMessage(player.getName() + " received voteparty rewards");
									}
								}
								for (Player all : Bukkit.getOnlinePlayers()) {
									VotePartyStartEvent event = new VotePartyStartEvent(all, m.getVotes().playerList);
									Bukkit.getPluginManager().callEvent(event);
								}
								if (!hasAvaliableSlot(player)) {
									if (!m.MySQL()) {
										int f = m.getData()
												.getInt("Players." + player.getUniqueId().toString() + ".voteparty");
										m.getData().set("Players." + player.getUniqueId().toString() + ".voteparty",
												f + 1);
										m.getData().save();

									} else {
										try {
											PreparedStatement stmt = m.getConnection()
													.prepareStatement("UPDATE users SET `voteparty` = "
															+ m.getVotes().getVoteParty(player) + " WHERE `uuid` = '"
															+ player.getUniqueId().toString() + "'");
											stmt.executeUpdate();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
									m.addMessage(player.getName() + " has full inventory");
								}
							} else {
								if (!m.MySQL()) {
									int f = m.getData()
											.getInt("Players." + player.getUniqueId().toString() + ".voteparty");
									m.getData().set("Players." + player.getUniqueId().toString() + ".voteparty", f + 1);
									m.getData().save();

								} else {
									try {
										PreparedStatement stmt = m.getConnection()
												.prepareStatement("UPDATE users SET `voteparty` = "
														+ m.getVotes().getVoteParty(player) + " WHERE `uuid` = '"
														+ player.getUniqueId().toString() + "'");
										stmt.executeUpdate();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								m.addMessage(player.getName() + " is offline");
							}
						}

						m.getVotes().playerList.clear();
						m.addMessage("Cleared VoteParty list");
					} else {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (hasAvaliableSlot(player)) {
								if (m.getConfig().getBoolean("VoteParty.Crate.Cooldown")) {
									int secs = m.getConfig().getInt("VoteParty.Crate.Secs");

									if (m.getConfig().getBoolean("Options.Title")) {
										TitleEvent event = new TitleEvent(player,
												m.getUtils().color(m.getMessages().getString("VoteParty.Title"))
														.replace("%secs%", String.valueOf(secs)),
												m.getUtils().color(m.getMessages().getString("VoteParty.Subtitle"))
														.replace("%secs%", String.valueOf(secs)),
												4, 20, 4);
										Bukkit.getPluginManager().callEvent(event);
										if (event.isCancelled())
											return;
										m.getUtils().sendTitle(player,
												m.getUtils().color(m.getMessages().getString("VoteParty.Title"))
														.replace("%secs%", String.valueOf(secs)),
												m.getUtils().color(m.getMessages().getString("VoteParty.Subtitle"))
														.replace("%secs%", String.valueOf(secs)));
									}
									countdown(player, secs);

								} else {
									if (m.getConfig().getBoolean("VoteParty.Crate.Sound")) {
										m.getUtils().PlaySound(player, Sound
												.valueOf(m.getConfig().getString("Sounds.VotePartyStart")).playSound(),
												1000, 1);
									}
									if (m.getConfig().getBoolean("VoteParty.Crate.enable")) {
										crate(player);
										m.addMessage(player.getName() + " received crate");
									} else {
										rewards(player);
										m.addMessage(player.getName() + " received voteparty rewards");
									}

								}
								for (Player all : Bukkit.getOnlinePlayers()) {
									VotePartyStartEvent event = new VotePartyStartEvent(all, m.getVotes().playerList);
									Bukkit.getPluginManager().callEvent(event);
								}
							} else {
								if (!m.MySQL()) {
									int f = m.getData()
											.getInt("Players." + player.getUniqueId().toString() + ".voteparty");
									m.getData().set("Players." + player.getUniqueId().toString() + ".voteparty", f + 1);
									m.getData().save();

								} else {
									try {
										PreparedStatement stmt = m.getConnection()
												.prepareStatement("UPDATE users SET `voteparty` = "
														+ m.getVotes().getVoteParty(player) + " WHERE `uuid` = '"
														+ player.getUniqueId().toString() + "'");
										stmt.executeUpdate();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								m.addMessage(player.getName() + " has full inventory");
							}
						}

					}
				}
			}
		}.runTaskLaterAsynchronously(m, 5);
	}

	public void crate(Player p) {
		ItemStack d = new ItemStack(Material.PISTON_BASE);
		ItemMeta f = d.getItemMeta();
		f.setDisplayName(m.getUtils().color(m.getConfig().getString("VoteParty.Crate.Display Name")));
		List<String> a = m.getConfig().getStringList("VoteParty.Crate.Lores");
		List<String> b = new ArrayList<>();
		for (String s : a) {
			b.add(m.getUtils().color(s));

		}
		f.setLore(b);
		// f.setLore(a);
		d.setItemMeta(f);
		p.getInventory().addItem(d);

		m.getUtils().sendMessage(p, m.getUtils().color(m.getMessages().getString("VoteParty.Crate.Give")));
	}

	public void crate(Player p, int amount) {
		ItemStack d = new ItemStack(Material.PISTON_BASE);
		ItemMeta f = d.getItemMeta();
		f.setDisplayName(m.getUtils().color(m.getConfig().getString("VoteParty.Crate.Display Name")));
		List<String> a = m.getConfig().getStringList("VoteParty.Crate.Lores");
		List<String> b = new ArrayList<>();
		for (String s : a) {
			b.add(m.getUtils().color(s));
		}
		f.setLore(b);
		d.setAmount(amount);
		d.setItemMeta(f);
		p.getInventory().addItem(d);

		m.getUtils().sendMessage(p, m.getUtils().color(m.getMessages().getString("VoteParty.Crate.Give")));
	}

	private boolean hasAvaliableSlot(Player p) {
		Inventory inv = p.getInventory();
		for (ItemStack item : inv.getContents()) {
			if (item == null) {
				return true;
			}
		}
		return false;
	}

}
