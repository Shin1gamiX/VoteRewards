package com.GeorgeV22.VoteRewards.Hooks;

import be.maximvdw.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;

import com.GeorgeV22.VoteRewards.Main;

public class MVdWPlaceholder {

	private Main m = Main.getPlugin(Main.class);

	public void hook() {
		MVdWPlaceholderAPI();
	}

	private void MVdWPlaceholderAPI() {
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			PlaceholderAPI.registerPlaceholder(m, "voterewards_total_votes",
					event -> String.valueOf(m.getData().getInt("VoteParty_Votes")));

			PlaceholderAPI.registerPlaceholder(m, "voterewards_votes_needed",
					event -> String.valueOf(m.getConfig().getInt("VoteParty.Votes")));

			PlaceholderAPI.registerPlaceholder(m, "voterewards_votes_until", event -> String
					.valueOf(m.getConfig().getInt("VoteParty.Votes") - m.getData().getInt("VoteParty_Votes")));

			PlaceholderAPI.registerPlaceholder(m, "voterewards_player_votes",
					event -> String.valueOf(m.getVotes().getVotes(event.getPlayer())));

			PlaceholderAPI.registerPlaceholder(m, "voterewards_top_voter",
					event -> String.valueOf(m.getVotes().getTopVotePlayer(1)));
		}
	}

}
