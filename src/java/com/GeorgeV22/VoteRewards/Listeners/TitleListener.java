package com.GeorgeV22.VoteRewards.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Events.TitleEvent;

public class TitleListener implements Listener {

	private Main m = Main.getPlugin(Main.class);

	@EventHandler
	public void onTitle(TitleEvent e) {
		Player p = e.getPlayer();
		if (m.getConfig().getBoolean("Debug.Events")) {
			if (p.isOp()) {
				m.getUtils().sendMessage(p, m.getUtils().color("&4&lTitleEvent called!"));
			}
		}

	}

}
