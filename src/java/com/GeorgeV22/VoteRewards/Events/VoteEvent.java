package com.GeorgeV22.VoteRewards.Events;

import com.vexsoftware.votifier.model.Vote;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VoteEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;

	private Vote vote;
	private Player player;

	public VoteEvent(Vote vote) {
		this.vote = vote;
		this.player = Bukkit.getPlayer(vote.getUsername());
	}

	public VoteEvent(Vote vote, Player p) {
		this.vote = vote;
		this.player = p;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean b) {
		cancel = b;
	}

	public Vote getVote() {
		return vote;
	}

	public String getUsername() {
		return vote.getUsername();
	}

	public String getServiceName() {
		return vote.getServiceName();
	}

	public String getTimeStamp() {
		return vote.getTimeStamp();
	}

	public String getAddress() {
		return vote.getAddress();
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
