package com.GeorgeV22.VoteRewards;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

public class Updater {

	private Main m = Main.getPlugin(Main.class);

	public Updater() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String BASE_URL = "https://raw.githubusercontent.com/GeorgeV22-ArtlieX/VoteRewards/master/version.md";
				m.getLogger().info("Checking for Updates ... ");

				String onlineVersion;

				try {

					HttpsURLConnection con = (HttpsURLConnection) new URL(BASE_URL).openConnection();

					con.setDoOutput(true);

					con.setRequestMethod("GET");

					onlineVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

				} catch (Exception ex) {

					m.getLogger().warning("Failed to check for an update on GitHub.");

					m.getLogger().warning("Either GitHub or you are offline or are slow to respond.");

					return;

				}
				if (!m.getDescription().getVersion().equalsIgnoreCase(onlineVersion)) {
					if (m.getDescription().getVersion().contains("CustomBuild")) {
						return;
					} else if (onlineVersion.contains("Beta")) {
						m.getLogger().warning("New beta version availiable!");

						m.getLogger().warning("Beta Version: " + onlineVersion + ". You are running version: "
								+ m.getDescription().getVersion());
						m.getLogger().warning("Update at: https://www.mc-market.org/resources/9094/");
					} else {
						m.getLogger().warning("New stable version availiable!");

						m.getLogger().warning("Stable Version: " + onlineVersion + ". You are running version: "
								+ m.getDescription().getVersion());
						m.getLogger().warning("Update at: https://www.mc-market.org/resources/9094/");
					}
				} else {
					if (m.getDescription().getVersion().contains("Beta")) {
						m.getLogger().info("You are running the newest beta build.");
					} else {
						m.getLogger().info("You are running the newest stable build.");
					}
				}
			}
		}).run();

	}

	public Updater(Player player) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String BASE_URL = "https://raw.githubusercontent.com/GeorgeV22-ArtlieX/VoteRewards/master/version.md";
				new Message(player, MessageType.CHAT_WITH_COLOR, "&e&lUpdater &8» &6Checking for Updates ...").send();

				String onlineVersion;

				try {

					HttpsURLConnection con = (HttpsURLConnection) new URL(BASE_URL).openConnection();

					con.setDoOutput(true);

					con.setRequestMethod("GET");

					onlineVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

				} catch (Exception ex) {
					new Message(player, MessageType.CHAT_WITH_COLOR,
							"&e&lUpdater &8» &cFailed to check for an update on GitHub.").send();
					new Message(player, MessageType.CHAT_WITH_COLOR,
							"&e&lUpdater &8» &cEither GitHub or you are offline or are slow to respond.").send();
					return;

				}
				if (!m.getDescription().getVersion().equalsIgnoreCase(onlineVersion)) {
					if (m.getDescription().getVersion().contains("CustomBuild")) {
						return;
					} else if (onlineVersion.contains("Beta")) {
						new Message(player, MessageType.CHAT_WITH_COLOR,
								"&e&lUpdater &8» &6New beta version availiable!").send();
						new Message(player, MessageType.CHAT_WITH_COLOR, "&e&lUpdater &8» &6Beta Version: &c"
								+ onlineVersion + ". &6You are running version: &c" + m.getDescription().getVersion())
										.send();
						new Message(player, MessageType.CHAT_WITH_COLOR,
								"&e&lUpdater &8» &6Update at: https://www.mc-market.org/resources/9094/").send();
					} else {
						new Message(player, MessageType.CHAT_WITH_COLOR,
								"&e&lUpdater &8» &6New stable version availiable!").send();
						new Message(player, MessageType.CHAT_WITH_COLOR, "&e&lUpdater &8» &6Stable Version: &c"
								+ onlineVersion + ". &6You are running version: &c" + m.getDescription().getVersion())
										.send();
						new Message(player, MessageType.CHAT_WITH_COLOR,
								"&e&lUpdater &8» &6Update at: https://www.mc-market.org/resources/9094/").send();

					}
				} else {
					new Message(player, MessageType.CHAT_WITH_COLOR,
							"&e&lUpdater &8» &6You are running the newest stable build.").send();
				}
			}
		}).run();

	}

}