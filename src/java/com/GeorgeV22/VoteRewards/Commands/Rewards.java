package com.GeorgeV22.VoteRewards.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;

import java.util.List;

public class Rewards implements CommandExecutor {

	private Main m = Main.getPlugin(Main.class);

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!sender.hasPermission("voterewards.rewards")) {
					new Message(sender, MessageType.CHAT_WITH_COLOR, "&8[&3VoteRewards&8] &4No Permissions!").send();
					return;
				}
				List<String> rewards = m.getMessages().getStringList("Basics.Rewards");
				for (String msg : rewards) {
					if (!(sender instanceof Player)) {
						new Message(sender, MessageType.CHAT_WITH_COLOR,
								msg.replace("%votes%", String.valueOf("0").replace("%player%", "Console"))).send();
						return;
					}
					new Message(sender, MessageType.CHAT_WITH_COLOR,
							msg.replace("%votes%", String.valueOf(m.getConfig().getInt("Votes." + sender.getName()))
									.replace("%player%", sender.getName()))).send();

				}
			}
		}).start();

		return true;
	}

}
