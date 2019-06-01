package com.GeorgeV22.VoteRewards.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OfflineVoteEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;
	private String p;
	private String service;

	public OfflineVoteEvent(String p, String service_name) {
		this.p = p;
		this.service = service_name;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public String getOfflinePlayer() {
		return p;
	}

	public String getServiceName() {
		return service;
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
