package com.GeorgeV22.VoteRewards.API;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.Messages.Message;
import com.GeorgeV22.VoteRewards.Utilities.Messages.MessageType;
import com.vexsoftware.votifier.model.Vote;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class API {

	private Vote vote;
	private Main m = Main.getPlugin(Main.class);

	public API() {
	}

	@Deprecated
	public API(Vote vote) {
		this.vote = vote;
	}

	@Deprecated
	public int getVotes(String string) {
		return m.getVotes().getVotes(Bukkit.getPlayer(string));
	}

	public int getVotes(Player player) {
		return m.getVotes().getVotes(player);
	}

	@Deprecated
	public int getVotes(OfflinePlayer offlinePlayer) {
		return m.getVotes().getVotes(offlinePlayer);
	}

	public String getServiceName(Vote vote, String service_name) {
		service_name = vote.getServiceName();
		return service_name;
	}

	@Deprecated
	public String getServiceName(String service_name) {
		service_name = vote.getServiceName();
		return service_name;
	}

	public String getUsername(Vote vote) {
		return vote.getUsername();
	}

	public String getTimeStamp(Vote vote) {
		return vote.getTimeStamp();
	}

	public String getAddress(Vote vote) {
		return vote.getAddress();
	}

	@Deprecated
	public String getUsername() {
		return vote.getUsername();
	}

	@Deprecated
	public String getTimeStamp() {
		return vote.getTimeStamp();
	}

	public String getAddress() {
		return vote.getAddress();
	}

	public void sendVote(Vote vote, Player player, String service_name, boolean offlineVote, String offline_name) {
		if (offlineVote) {
			m.getVotes().processVote(player, vote);
		} else {
			m.getVotes().processOfflineVote(service_name, offline_name);
		}
	}

	@Deprecated
	public void sendVote(Player player, String service_name, boolean offlineVote, String offline_name) {
		if (offlineVote) {
			m.getVotes().processVote(player, vote);
		} else {
			m.getVotes().processOfflineVote(service_name, offline_name);
		}
	}

	public void sendOfflineVote() {
		m.getVotes().processOfflineVote(vote.getServiceName(), vote.getUsername());
	}

	@Deprecated
	public void sendOfflineVote(Vote vote) {
		m.getVotes().processOfflineVote(vote.getServiceName(), vote.getUsername());
	}

	public void resetVotes(Player p) {
		m.getVotes().resetVote(p);
	}

	@Deprecated
	public void resetVotes(String s) {
		m.getVotes().resetVote(s);
	}

	public void setVotes(Player p, int amount) {
		m.getVotes().setVotes(p, amount);
	}

	@Deprecated
	public void setVotes(String s, int amount) {
		m.getVotes().setVotes(s, amount);
	}

	@Deprecated
	public void removeVotes(String s, int amount) {
		m.getVotes().removeVotes(s, amount);
	}

	public void removeVotes(Player p, int amount) {

		m.getVotes().removeVotes(p, amount);
	}

	@Deprecated
	public void addVotes(String s, int amount) {
		m.getVotes().addVotes(s, amount);
	}

	public void addVotes(Player p, int amount) {
		m.getVotes().addVotes(p, amount);
	}

	public void sendTitle(Object p, String title, String subtitle) {
		new Message(p, MessageType.TITLE_WITH_SUBTITLE, title, subtitle).send();
	}

	public void sendActionbar(Object p, String msg) {
		new Message(p, MessageType.ACTIONBAR, msg).send();
	}

	public void sendColoredMessage(Player p, String msg) {
		new Message(p, MessageType.CHAT_WITH_COLOR, msg).send();
	}

	@Deprecated
	public void sendMessage(Player p, String msg) {
		new Message(p, MessageType.CHAT, msg).send();
	}

}
