package com.GeorgeV22.VoteRewards.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Events.ActionbarEvent;
import com.GeorgeV22.VoteRewards.Events.OfflineVoteEvent;
import com.GeorgeV22.VoteRewards.Events.TitleEvent;
import com.GeorgeV22.VoteRewards.Events.VoteEvent;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;
import com.google.common.collect.Maps;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class Votes {

	private Main m = Main.getPlugin(Main.class);
	List<Player> playerList = new ArrayList<>();

	public void sendFakeVote(CommandSender sender, String p, String service_name) {
		if (service_name != null) {
			Vote fakeVote = new Vote();
			fakeVote.setUsername(p);
			fakeVote.setServiceName(service_name);
			fakeVote.setAddress("127.0.0.1");
			fakeVote.setTimeStamp(String.valueOf(System.currentTimeMillis()));
			m.getServer().getPluginManager().callEvent(new VotifierEvent(fakeVote));
			m.getUtils().sendMessage(sender, "Sent fake vote!");
			Bukkit.getLogger().info("Sent fake vote: " + fakeVote.toString());
		} else {
			Vote fakeVote = new Vote();
			fakeVote.setUsername(p);
			fakeVote.setServiceName("fakeVote");
			fakeVote.setAddress("127.0.0.1");
			fakeVote.setTimeStamp(String.valueOf(System.currentTimeMillis()));
			m.getServer().getPluginManager().callEvent(new VotifierEvent(fakeVote));
			m.getUtils().sendMessage(sender, "Sent fake vote!");
			Bukkit.getLogger().info("Sent fake vote: " + fakeVote.toString());
		}
	}

	public void processVote(Player p, Vote v) {
		try {
			String service_name = v.getServiceName();
			VoteEvent vote = new VoteEvent(v);
			if (vote.isCancelled())
				return;

			addVotes(p, 1);

			new VoteType().processVote(p, service_name);
			m.getUtils().PlaySound(p, Sound.valueOf(m.getConfig().getString("Sounds.Vote")).playSound(), 1000, 1);

			int f = m.getData().getInt("VoteParty_Votes");
			m.getData().set("VoteParty_Votes", f + 1);
			long fb = System.currentTimeMillis() + (1000 * 3600 * 12);
			m.getData().set("Players." + p.getUniqueId().toString() + ".time", fb);
			new BukkitRunnable() {

				@Override
				public void run() {
					m.getData().save();
				}
			}.runTaskLater(m, 20 * 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processOfflineVoteOnJoin(Player p, String service_name) {
		new VoteType().processVote(p, service_name);
		m.getUtils().PlaySound(p, Sound.valueOf(m.getConfig().getString("Sounds.Vote")).playSound(), 1000, 1);
	}

	public void processOfflineVote(String service_name, String uuid) {
		if (m.getConfig().getBoolean("Options.Offline")) {
			List<String> s = m.getOffline().getStringList("Offline." + uuid + ".service_names");
			s.add(service_name);

			OfflineVoteEvent event = new OfflineVoteEvent(uuid, s.toString());
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled())
				return;

			if (m.getOffline().get("Offline." + uuid) == null) {
				m.getOffline().set("Offline." + uuid + ".times", 1);
				m.getOffline().set("Offline." + uuid + ".service_names", s);
				m.getOffline().save();

			}
			if (m.getData().get("Players." + uuid) == null) {
				m.getData().set("Players." + uuid + ".votes", 1);
				long fb = System.currentTimeMillis() + (1000 * 3600 * 12);
				m.getData().set("Players." + uuid + ".time", fb);
				m.getData().save();
			}
			int times = m.getOffline().getInt("Offline." + uuid + ".times");
			m.getOffline().set("Offline." + uuid + ".times", times + 1);
			m.getOffline().set("Offline." + uuid + ".service_names", s);
			m.getOffline().save();

			addVotes(uuid, 1);

			int f = m.getData().getInt("VoteParty_Votes");
			m.getData().set("VoteParty_Votes", f + 1);
			long fb = System.currentTimeMillis() + (1000 * 3600 * 12);
			m.getData().set("Players." + uuid + ".time", fb);
			m.getData().save();
			m.addMessage("Processed Offline vote for " + uuid);
		} else {
			Bukkit.getLogger().warning("Settings: offline vote is disabled");
		}

	}

	public void sendVoteTitle(Player p, String service_name) {
		if (m.getConfig().getBoolean("Options.Title")) {
			TitleEvent event = new TitleEvent(p,
					m.getUtils().color(m.getMessages().getString("Vote.Title")).replace("%player%", p.getName())
							.replace("%service_name%", service_name).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + p.getName() + ".votes"))),
					m.getUtils().color(m.getMessages().getString("Vote.Subtitle")).replace("%player%", p.getName())
							.replace("%service_name%", service_name).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + p.getName() + ".votes"))),
					4, 20, 4);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled())
				return;
			new Message(p, MessageType.TITLE_WITH_SUBTITLE,
					m.getUtils().color(m.getMessages().getString("Vote.Title")).replace("%player%", p.getName())
							.replace("%service_name%", service_name).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + p.getName() + ".votes"))),
					m.getUtils().color(m.getMessages().getString("Vote.Subtitle")).replace("%player%", p.getName())
							.replace("%service_name%", service_name).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + p.getName() + ".votes")))).send();
			m.addMessage("Send vote title to " + p.getName());
		}
	}

	public void sendVoteActionbar(Player p, String service_name) {
		if (m.getConfig().getBoolean("Options.Actionbar")) {
			ActionbarEvent event = new ActionbarEvent(p,
					m.getUtils().color(m.getMessages().getString("Actionbar")).replace("%player%", p.getName())
							.replace("%service_name%", service_name).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + p.getName() + ".votes"))));
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled())
				return;
			m.getUtils().sendActionbar(p,
					m.getUtils().color(m.getMessages().getString("Actionbar")).replace("%player%", p.getName())
							.replace("%service_name%", service_name).replace("%votes%",
									String.valueOf(m.getData().getInt("Players." + p.getName() + ".votes"))));

			m.addMessage("Send vote actionbar to " + p.getName());
		}
	}

	public void getTopPlayers(CommandSender sender, int amount) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (m.MySQL()) {
						PreparedStatement s = m.getConnection()
								.prepareStatement("SELECT * FROM `users` ORDER BY `votes`");
						ResultSet rs = s.executeQuery();
						rs.findColumn("uuid");

						if (rs.last()) {
							for (String s1 : m.getMessages().getStringList("Basics.VoteTop.Body")) {
								new Message(sender, MessageType.CHAT_WITH_COLOR,
										s1.replace("%top_players_name%", UUIDFetcher.getName(rs.getString("uuid")))
												.replace("%top_player_votes%", String.valueOf(rs.getInt("votes"))))
														.send();
							}
						}
						for (int i = 0; i < amount; i++) {
							if (rs.previous()) {
								for (String s1 : m.getMessages().getStringList("Basics.VoteTop.Body")) {
									new Message(sender, MessageType.CHAT_WITH_COLOR,
											s1.replace("%top_players_name%", UUIDFetcher.getName(rs.getString("uuid")))
													.replace("%top_player_votes%", String.valueOf(rs.getInt("votes"))))
															.send();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (m.getConfig().getBoolean("Options.VoteTop.Footer")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, m.getMessages().getString("Basics.VoteTop.Footer"))
							.send();
				}
			}
		}).start();
	}

	public String getTopVotePlayer(int amount) {
		if (!m.MySQL()) {
			Map<String, Integer> votes = Maps.newHashMap();
			if (m.getData().get("Players") == null)
				return null;
			m.getData().getConfigurationSection("Players").getKeys(false)
					.forEach(namex -> votes.put(namex, m.getData().getInt("Players." + namex + ".votes")));

			LinkedHashMap<String, Integer> voteSorted = votes.entrySet().stream()
					.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
							LinkedHashMap::new));

			if (amount == 1) {
				return UUIDFetcher.getName(voteSorted.keySet().iterator().next());
			}

			Iterator<String> itr = voteSorted.keySet().iterator();

			int count = 0;
			while (itr.hasNext()) {
				UUID top = UUID.fromString(itr.next());
				if (++count == amount) {
					return UUIDFetcher.getName(top);
				}
			}

			return null;

		} else {
			try {
				PreparedStatement s = m.getConnection().prepareStatement("SELECT * FROM `users` ORDER BY `votes`");
				ResultSet rs = s.executeQuery();
				rs.findColumn("uuid");

				if (rs.last()) {
					return UUIDFetcher.getName(rs.getString("uuid"));
				}

				for (int x = 2; x <= amount; x++) {
					if (rs.previous()) {
						return UUIDFetcher.getName(rs.getString("uuid"));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int getTopVoteInt(int amount) {
		ArrayList<String> ignore = new ArrayList<>();
		int maxVotes = -1;
		String topP;
		for (int x = 1; x <= amount; x++) {
			topP = null;
			maxVotes = -1;
			for (String playerName : m.getData().getConfigurationSection("Players").getKeys(false)) {

				int getVotes = m.getData().getInt("Players." + playerName + ".votes");

				if (getVotes >= maxVotes && !ignore.contains(playerName)) {
					maxVotes = getVotes;
					topP = playerName;
				}

			}
			ignore.add(topP);
		}
		return maxVotes;
	}

	public Integer getVotes(Object object) {
		if (object instanceof Player) {
			Player player = (Player) object;
			if (m.MySQL()) {
				try {
					String name = player.getUniqueId().toString();
					PreparedStatement statement = m.getConnection()
							.prepareStatement("SELECT * FROM users WHERE uuid = '" + name + "';");
					ResultSet res = statement.executeQuery();
					res.next();
					if (res.getString("uuid") == null) {
						return 0;
					} else {
						return res.getInt("votes");
					}
				} catch (Exception e) {
					m.addMessage(e.getMessage());
					return 0;
				}
			} else {
				return m.getData().getInt("Players." + player.getUniqueId().toString() + ".votes");
			}
		} else if (object instanceof String) {
			String name = (String) object;
			if (Bukkit.getOnlineMode()) {
				if (!name.contains("-")) {
					UUID uuid = UUIDFetcher.getUUID((String) object);
					name = String
							.valueOf(uuid != null ? uuid.toString() : Bukkit.getPlayer(name).getUniqueId().toString());
				}
			} else {
				name = Bukkit.getPlayer((String) object).getUniqueId().toString();
			}
			if (m.MySQL()) {
				try {
					PreparedStatement statement = m.getConnection()
							.prepareStatement("SELECT * FROM users WHERE uuid = '" + name + "';");
					ResultSet res = statement.executeQuery();
					res.next();
					if (res.getString("uuid") == null) {
						return 0;
					} else {
						return res.getInt("votes");
					}
				} catch (Exception e) {
					m.addMessage(e.getMessage());
					return 0;
				}
			} else {
				return m.getData().getInt("Players." + name + ".votes");
			}
		} else if (object instanceof OfflinePlayer) {
			OfflinePlayer player = (OfflinePlayer) object;
			if (m.MySQL()) {
				try {
					String name = player.getUniqueId().toString();
					PreparedStatement statement = m.getConnection()
							.prepareStatement("SELECT * FROM users WHERE uuid = '" + name + "';");
					ResultSet res = statement.executeQuery();
					res.next();
					if (res.getString("uuid") == null) {
						return 0;
					} else {
						return res.getInt("votes");
					}
				} catch (Exception e) {
					m.addMessage(e.getMessage());
					return 0;
				}
			} else {
				return m.getData().getInt("Players." + player.getUniqueId().toString() + ".votes");
			}
		}
		return 0;
	}

	public void setVotes(Object object, int votes) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int total_votes = m.getData().getInt("Total_Votes");
				m.getData().set("Total_Votes", total_votes += 1);
				m.getData().save();
				if (object instanceof Player) {
					Player player = (Player) object;
					if (m.MySQL()) {
						try {
							String name = player.getUniqueId().toString();
							// String test = "UPDATE `users` SET `votes`='11',`time`='194783784785' WHERE
							// `uuid` = 'a4f5cd7f-362f-4044-931e-7128b4e6bad9' ";
							PreparedStatement statement = m.getConnection()
									.prepareStatement("UPDATE `users` SET `votes` = '" + votes + "', `time` = '"
											+ System.currentTimeMillis() + "' WHERE `uuid` = '" + name + "'");
							statement.executeUpdate();

						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						m.getData().set("Players." + player.getUniqueId().toString() + ".votes", votes);
						m.getData().save();
					}
				} else if (object instanceof String) {
					String name = (String) object;
					if (m.MySQL()) {
						try {
							PreparedStatement statement = m.getConnection()
									.prepareStatement("UPDATE `users` SET `votes` = '" + votes + "', `time` = '"
											+ System.currentTimeMillis() + "' WHERE `uuid` = '" + name + "'");
							statement.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						m.getData().set("Players." + name + ".votes", votes);
						m.getData().save();
					}
				}
			}
		}).start();

	}

	public void createUser(Player player) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String name = player.getUniqueId().toString();
				if (m.MySQL()) {
					try {
						if (playerExist(name)) {
							Bukkit.getLogger().warning("[VoteRewards] Player " + player.getName() + " with uuid " + name
									+ " already exists!");
							m.addMessage("Player " + player.getName() + " with uuid " + name + " already exists!");
							return;
						}
						PreparedStatement statement1 = m.getConnection()
								.prepareStatement("INSERT INTO users (`uuid`, `votes`, `time`, `voteparty`) VALUES ('"
										+ name + "', '0', '0', '0');");
						statement1.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (m.getData().get("Players." + name) == null) {
						m.getData().set("Players." + name + ".votes", 0);
						m.getData().save();

					}
				}
			}
		}).start();
	}

	public boolean playerExist(String uuid) throws SQLException {
		PreparedStatement ps = m.getConnection().prepareStatement("SELECT * FROM `users` WHERE `uuid` = ?");
		ps.setString(1, uuid);
		try (ResultSet rs = ps.executeQuery()) {
			boolean b = rs.next();
			return b;
		}
	}

	public void addVotes(Player player, int votes) {
		setVotes(player, getVotes(player) + votes);
	}

	public void addVotes(String name, int votes) {
		setVotes(name, getVotes(name) + votes);
	}

	public void removeVotes(Player player, int votes) {
		setVotes(player, getVotes(player) - votes);
	}

	public void removeVotes(String name, int votes) {
		setVotes(name, getVotes(name) - votes);
	}

	public void clearVotes() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (m.MySQL()) {
					try {
						PreparedStatement statement = m.getConnection().prepareStatement("DELETE FROM users");
						statement.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					for (String s : m.getData().getConfigurationSection("Players").getKeys(false)) {
						m.getData().set("Players." + s + ".votes", 0);
						m.getData().save();
					}
				}
			}
		}).start();

	}

	public Integer getVoteParty(Player player) throws SQLException {
		if (m.MySQL()) {
			PreparedStatement stmt = m.getConnection()
					.prepareStatement("SELECT voteparty FROM users WHERE uuid = " + player.getUniqueId().toString());
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return res.getInt("voteparty");
			}
		}
		return 0;
	}

	public void resetVote(Object object) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (object instanceof Player) {
					Player player = (Player) object;
					if (m.MySQL()) {
						try {
							String name = player.getUniqueId().toString();
							PreparedStatement statement = m.getConnection()
									.prepareStatement("SELECT * FROM users WHERE uuid = '" + name + "';");
							ResultSet res = statement.executeQuery();
							if (res.next()) {
								if (res.getString("uuid") == null) {
									return;
								} else {
									statement.executeUpdate(
											"UPDATE `users` SET `votes` = " + 0 + " WHERE `uuid` = '" + name + "'");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						m.getData().set("Players." + player.getUniqueId().toString() + ".votes", 0);
						m.getData().save();
					}
				} else if (object instanceof String) {
					String name = (String) object;
					if (Bukkit.getOnlineMode()) {
						UUID uuid = UUIDFetcher.getUUID((String) object);
						name = String.valueOf(
								uuid != null ? uuid.toString() : Bukkit.getPlayer(name).getUniqueId().toString());
					} else
						name = Bukkit.getPlayer(name).getUniqueId().toString();
					if (m.MySQL()) {
						try {
							PreparedStatement statement = m.getConnection()
									.prepareStatement("SELECT * FROM users WHERE uuid = '" + name + "';");
							ResultSet res = statement.executeQuery();
							res.next();
							if (res.getString("uuid") == null) {
								Bukkit.getLogger().info("[VoteRewards] Error: Player not found");
							} else {
								statement.executeUpdate(
										"UPDATE `users` SET `votes` = " + 0 + " WHERE `uuid` = '" + name + "'");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						m.getData().set("Players." + name + ".votes", 0);
						m.getData().save();
					}
				}
			}
		}).start();
	}

}
