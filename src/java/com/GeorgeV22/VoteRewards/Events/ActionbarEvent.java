package com.GeorgeV22.VoteRewards.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActionbarEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;
	private Player player;
	private String message;

	public ActionbarEvent(Player player, String message) {
		this.player = player;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Player getPlayer() {
		return player;
	}

	public static HandlerList getHandlerList() {
		return handlers;
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
