package com.GeorgeV22.VoteRewards;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.GeorgeV22.VoteRewards.API.API;
import com.GeorgeV22.VoteRewards.Actionbar.Actionbar;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_10_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_11_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_12_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_13_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_13_R2;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_14_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_8_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_8_R2;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_8_R3;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_9_R1;
import com.GeorgeV22.VoteRewards.Actionbar.Versions.Actionbar_v1_9_R2;
import com.GeorgeV22.VoteRewards.Commands.Debug;
import com.GeorgeV22.VoteRewards.Commands.FakeVote;
import com.GeorgeV22.VoteRewards.Commands.Rewards;
import com.GeorgeV22.VoteRewards.Commands.Vote;
import com.GeorgeV22.VoteRewards.Commands.VoteParty;
import com.GeorgeV22.VoteRewards.Commands.VoteRewards;
import com.GeorgeV22.VoteRewards.Commands.VoteTop;
import com.GeorgeV22.VoteRewards.Database.DB;
import com.GeorgeV22.VoteRewards.Database.DatabaseType;
import com.GeorgeV22.VoteRewards.Database.MySQL.MySQL;
import com.GeorgeV22.VoteRewards.GUI.AdminGUI;
import com.GeorgeV22.VoteRewards.Hooks.MVdWPlaceholder;
import com.GeorgeV22.VoteRewards.Hooks.PAPI;
import com.GeorgeV22.VoteRewards.Listeners.CustomEventListeners;
import com.GeorgeV22.VoteRewards.Listeners.VoteListeners;
import com.GeorgeV22.VoteRewards.Title.Title;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_10_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_11_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_12_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_13_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_13_R2;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_14_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_8_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_8_R2;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_8_R3;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_9_R1;
import com.GeorgeV22.VoteRewards.Title.Versions.Title_v1_9_R2;
import com.GeorgeV22.VoteRewards.Utilities.UUIDFetcher;
import com.GeorgeV22.VoteRewards.Utilities.Utils;
import com.GeorgeV22.VoteRewards.Utilities.Votes;
import com.GeorgeV22.VoteRewards.Utilities.Config.ConfigManager;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

public class Main extends JavaPlugin implements Listener {

	private ConfigManager config = new ConfigManager(this, "config.yml", "config.yml");
	private ConfigManager messages = new ConfigManager(this, "messages.yml", "messages.yml");
	private ConfigManager offline = new ConfigManager(this, "offline_votes.yml");
	private ConfigManager data = new ConfigManager(this, "data.yml", "data.yml");
	File log = new File(getDataFolder(), "voterewards.log");
	private Title title;
	private Actionbar actionbar;
	private API api;
	private Utils utils;
	private MySQL MySQL = (MySQL) new DB(DatabaseType.MySQL).connect(config.getString("MySQL.IP"),
			config.getString("MySQL.Port"), config.getString("MySQL.Database"), config.getString("MySQL.User"),
			config.getString("MySQL.Password"));
	private Connection c = null;
	public BukkitTask b;
	public BukkitTask c1;
	private Votes votes;
	private String ver1 = "1.6.4";
	private String ver2 = "1.2";
	private boolean oldconfig = false;
	private boolean oldmessages = false;

	@Override
	public void onEnable() {
		try {
			api = new API();
			votes = new Votes();
			utils = new Utils();
			Listeners();
			commands();
			hooks();
			setupVersion();
			config();

			addMessage("Plugin successfully loaded");
		} catch (Exception e) {
			addMessage("Plugin throws error: " + e.getStackTrace().toString());
		}
	}

	@Override
	public void onDisable() {
		if (MySQL()) {
			try {
				if (!getConnection().isClosed())
					getConnection().close();
			} catch (SQLException e) {
				addMessage("Plugin unloaded under error: " + e.getStackTrace());
			}
		}
		if (getConfig().getBoolean("Options.Reminder.Enabled"))
			reminder().cancel();
		if (getConfig().getBoolean("Options.Daily"))
			onDaily().cancel();
		addMessage("Plugin successfully unloaded");
	}

	public BukkitTask reminder() {
		b = new BukkitRunnable() {
			@Override
			public void run() {
				if (MySQL()) {
					try {
						for (Player player : Bukkit.getOnlinePlayers()) {
							PreparedStatement stmt = c.prepareStatement(
									"SELECT time FROM users WHERE uuid = " + player.getUniqueId().toString());
							ResultSet rs = stmt.executeQuery();
							if (rs.next()) {
								long b = rs.getLong("time");
								Date date = new Date(b);
								Date current = new Date(System.currentTimeMillis());
								if (date.getTime() <= current.getTime() - 86400000L) {
									if (getConfig().getBoolean("Debug.Reminder"))
										Bukkit.broadcastMessage("DEBUG: REMINDER");
									for (String s : getMessages().getStringList("Basics.Reminder"))
										new Message(player, MessageType.CHAT_WITH_COLOR,
												s.replace("%player%", player.getName()).replace("%votes%",
														String.valueOf(getVotes().getVotes(player)))).send();

								}

							}
						}
					} catch (Exception e) {
						addMessage(String.valueOf(e.getStackTrace()));
					}

				}
				for (Player player : Bukkit.getOnlinePlayers()) {
					Date date = new Date(data.getLong("Players." + player.getUniqueId().toString() + ".time"));
					Date current = new Date(System.currentTimeMillis());
					if (date.getTime() <= current.getTime() - 86400000L) {
						if (getConfig().getBoolean("Debug.Reminder"))
							Bukkit.broadcastMessage("DEBUG: REMINDER");
						for (String s : getMessages().getStringList("Basics.Reminder"))
							new Message(player, MessageType.CHAT_WITH_COLOR, s.replace("%player%", player.getName())
									.replace("%votes%", String.valueOf(getVotes().getVotes(player)))).send();

					}

				}
			}
		}.runTaskTimerAsynchronously(this, 40, getConfig().getInt("Options.Reminder.Seconds") * 20);
		return b;
	}

	private BukkitTask onDaily() {
		c1 = new BukkitRunnable() {

			@Override
			public void run() {
				if (getConfig().getBoolean("Options.Daily")) {
					if (MySQL()) {
						try {
							for (String s : data.getConfigurationSection("Players").getKeys(false)) {
								PreparedStatement stmt = c.prepareStatement("SELECT time FROM users WHERE uuid = " + s);
								ResultSet rs = stmt.executeQuery();
								if (rs.next()) {
									long b = rs.getLong("time");
									Date date = new Date(b);
									Date current = new Date(System.currentTimeMillis());

									if (date.getTime() - current.getTime() >= 43200000) {
										if (getData().getInt("Players." + s + ".dailyvotes") != 0) {
											getData().set("Players." + s + ".dailyvotes", 0);
											getData().save();
											addMessage("Daily votes for " + s + " changed to 0");
										}
									}
								}
								rs.close();
							}

						} catch (Exception e) {
							addMessage(String.valueOf(e.getStackTrace()));
						}

					} else {
						if (data.get("Players") != null)
							for (String s : data.getConfigurationSection("Players").getKeys(false)) {
								// date.getTime() - current.getTime() >= 43200000
								long configTime = data.getLong("Players." + s + ".time");
								long timeNow = System.currentTimeMillis();
								if (timeNow >= configTime) {
									if (getData().getInt("Players." + s + ".dailyvotes") != 0) {
										getData().set("Players." + s + ".dailyvotes", 0);
										getData().save();
										addMessage("Daily votes for " + s + " changed to 0");
									}
								}
							}
					}
				}
			}
		}.runTaskTimerAsynchronously(this, 40, 20 * 20);
		return c1;
	}

	private void Listeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new VoteListeners(), this);
		pm.registerEvents(new CustomEventListeners(), this);
		pm.registerEvents(new AdminGUI(), this);
	}

	private void config() throws Exception {
		updateConfigs();
		if (Bukkit.getOnlineMode()) {
			if (data.isSet("Players"))
				try {
					new BukkitRunnable() {
						@Override
						public void run() {
							for (String s : data.getConfigurationSection("Players").getKeys(false)) {
								int a = data.getInt("Players." + s + ".votes");
								long b = data.getLong("Players." + s + ".time");
								int c = data.getInt("Players." + s + ".voteparty");
								if (s.length() <= 16) {
									data.set("Players." + UUIDFetcher.getUUID(s).toString() + ".votes", a);
									data.set("Players." + UUIDFetcher.getUUID(s).toString() + ".time", b);
									data.set("Players." + UUIDFetcher.getUUID(s).toString() + ".voteparty", c);
									data.set("Players." + s, null);
									data.save();
								}
							}
						}
					}.runTaskLaterAsynchronously(this, 60);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		if (config.getBoolean("Options.Metrics")) {
			new Metrics(this);
		}
		if (config.getBoolean("Options.MySQL")) {

			synchronized (this) {
				if (getConnection() != null && !getConnection().isClosed()) {
					return;
				}
				c = MySQL.openConnection();
				createTable();
				new BukkitRunnable() {

					@Override
					public void run() {
						Bukkit.getLogger().info(utils.color("[VoteRewards] MySQL connected!"));
						addMessage("MySQL connected!");
					}
				}.runTaskLaterAsynchronously(this, 60);

				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							if (getConnection() == null || getConnection().isClosed()) {
								c = MySQL.openConnection();
								addMessage("MySQL connected!");
							}
						} catch (SQLException | ClassNotFoundException e) {
							e.printStackTrace();
						}
					};
				}.runTaskTimerAsynchronously(this, 30, 60 * 60 * 20);
			}
		}
		if (config.getBoolean("Options.Reminder.Enabled"))
			reminder();
		if (config.getBoolean("Options.Daily"))
			onDaily();
		if (config.getBoolean("Options.Updater"))
			new Updater();
		if (!log.exists()) {
			log.getParentFile().mkdirs();
			log.createNewFile();
		}

	}

	@Deprecated
	public boolean updateConfigs() throws Exception {
		if (!config.getString("Options.ConfigVersion").equals(ver1)) {
			File backup = new File(getDataFolder(), "backups");
			if (!backup.exists()) {
				backup.mkdirs();
			}
			int year = new Date().getYear();
			int month = new Date().getMonth();
			int date = new Date().getDate();
			int hour = new Date().getHours();
			int minutes = new Date().getMinutes();
			int seconds = new Date().getSeconds();
			String value = year + "-" + month + "-" + date + "-" + hour + "-" + minutes + "-" + seconds;
			File oldfile = new File(getDataFolder(), "config.yml");
			File newfile = new File(getDataFolder() + "/backups/", "config_" + value + ".yml");
			Files.copy(oldfile.toPath(), newfile.toPath());
			config.set("Options.ConfigVersion", ver1);
			for (String s : config.getDefault().getConfigurationSection("").getKeys(true)) {
				config.checkPath(s);
			}
			config.save();
			oldconfig = false;
			addMessage("config.yml updated to version " + ver1);
			return true;
		}
		if (!messages.getString("ConfigVersion").equals(ver2)) {
			int min = 0;
			int max = 5000;
			Random rand = new Random();
			int value = rand.nextInt((max - min) + 1) + min;
			File oldfile = new File(getDataFolder(), "messages.yml");
			File newfile = new File(getDataFolder() + "/backups/", "messages_" + value + ".yml");
			Files.copy(oldfile.toPath(), newfile.toPath());
			if (messages.getStringList("Vote.Goal") == null) {
				List<String> a = new ArrayList<String>();
				a.add("&aVote Goal: {goal}");
				a.add("&aVote Goal in percentage: {percentage}");
				messages.set("Vote.Goal", a);
			}
			messages.set("ConfigVersion", ver2);
			messages.save();
			oldmessages = false;
			addMessage("messages.yml updated to version " + ver2);
			return true;
		}
		return false;
	}

	private void cmd(String cmd, CommandExecutor executor) {
		getCommand(cmd).setExecutor(executor);
	}

	private void hooks() {
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			MVdWPlaceholder MVdWPlaceholderAPI = new MVdWPlaceholder();
			MVdWPlaceholderAPI.hook();
		}

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new PAPI(this).register();
		}

	}

	private void commands() {
		cmd("vote", new Vote());
		cmd("rewards", new Rewards());
		cmd("votetop", new VoteTop());
		cmd("fakevote", new FakeVote());
		cmd("voterewards", new VoteRewards());
		cmd("voteparty", new VoteParty());
		cmd("votes", new com.GeorgeV22.VoteRewards.Commands.Votes());
		cmd("votedebug", new Debug());

	}

	private void createTable() throws SQLException {
		String sqlCreate = "CREATE TABLE IF NOT EXISTS users (uuid VARCHAR(255), votes INTEGER(255), time VARCHAR(255), voteparty INTEGER(255))";
		PreparedStatement stmt = c.prepareStatement(sqlCreate);
		stmt.execute();
	}

	private void setupVersion() {
		try {
			String string = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			switch (string) {
			case "v1_14_R1": {
				this.title = new Title_v1_14_R1();
				this.actionbar = new Actionbar_v1_14_R1();
				break;
			}
			case "v1_13_R2": {
				this.title = new Title_v1_13_R2();
				this.actionbar = new Actionbar_v1_13_R2();
				break;
			}
			case "v1_13_R1": {
				this.title = new Title_v1_13_R1();
				this.actionbar = new Actionbar_v1_13_R1();
				break;
			}
			case "v1_12_R1": {
				this.title = new Title_v1_12_R1();
				this.actionbar = new Actionbar_v1_12_R1();
				break;
			}
			case "v1_11_R1": {
				this.title = new Title_v1_11_R1();
				this.actionbar = new Actionbar_v1_11_R1();
				break;
			}
			case "v1_10_R1": {
				this.title = new Title_v1_10_R1();
				this.actionbar = new Actionbar_v1_10_R1();
				break;
			}
			case "v1_9_R2": {
				this.title = new Title_v1_9_R2();
				this.actionbar = new Actionbar_v1_9_R2();
				break;
			}
			case "v1_9_R1": {
				this.title = new Title_v1_9_R1();
				this.actionbar = new Actionbar_v1_9_R1();
				break;
			}
			case "v1_8_R3": {
				this.title = new Title_v1_8_R3();
				this.actionbar = new Actionbar_v1_8_R3();
				break;
			}
			case "v1_8_R2": {
				this.title = new Title_v1_8_R2();
				this.actionbar = new Actionbar_v1_8_R2();
				break;
			}
			case "v1_8_R1": {
				this.title = new Title_v1_8_R1();
				this.actionbar = new Actionbar_v1_8_R1();
				break;
			}
			}
		} catch (Exception e) {
			utils.sendMessage(Bukkit.getConsoleSender(),
					utils.color("&cException Title or Actionbar. " + e.getMessage()));
		}
	}

	public void addMessage(String msg) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String date = dateFormat.format(new Date());
			String finalMessage = "[" + date + "]" + " - " + "[VoteRewards]: " + msg + "\n";
			BufferedWriter bw = new BufferedWriter(new FileWriter(getDataFolder() + "/voterewards.log", true));
			bw.append(finalMessage);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Title getTitle() {
		return this.title;
	}

	public Connection getConnection() {
		return c;
	}

	public Actionbar getActionbar() {
		return this.actionbar;
	}

	@Override
	public ConfigManager getConfig() {
		return config;
	}

	public ConfigManager getMessages() {
		return messages;
	}

	public ConfigManager getData() {
		return data;
	}

	public ConfigManager getOffline() {
		return offline;
	}

	public Utils getUtils() {
		return utils;
	}

	public API getAPI() {
		return api;
	}

	public boolean getA() {
		return oldconfig;
	}

	public boolean getB() {
		return oldmessages;
	}

	public Votes getVotes() {
		return votes;
	}

	public File getLog() {
		return log;
	}

	public boolean MySQL() {
		return config.getBoolean("Options.MySQL");
	}
}