package com.GeorgeV22.VoteRewards.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class VotePartyStartEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean cancel;
	private List<Player> list;

	public VotePartyStartEvent(Player p) {
		this.player = p;
	}

	public VotePartyStartEvent(List<Player> p) {
		this.list = p;
	}

	public VotePartyStartEvent(Player p, List<Player> list) {
		this.player = p;
		this.list = list;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Player> getList() {
		return list;
	}

	public boolean isCancelled() {
		return this.cancel;
	}

	public void setCancelled(boolean bl) {
		this.cancel = bl;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
