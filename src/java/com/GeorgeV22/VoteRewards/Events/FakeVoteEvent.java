package com.GeorgeV22.VoteRewards.Events;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FakeVoteEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;
	private Player p;
	private Player target;
	private String offline_player;
	private String service_name;
	private ConsoleCommandSender sender;

	public FakeVoteEvent(Player p, String offline_player, String service_name) {
		this.p = p;
		this.offline_player = offline_player;
		this.service_name = service_name;
	}

	public FakeVoteEvent(Player p, Player target, String service_name) {
		this.p = p;
		this.target = target;
		this.service_name = service_name;
	}

	public FakeVoteEvent(ConsoleCommandSender sender, Player target, String service_name) {
		this.sender = sender;
		this.target = target;
		this.service_name = service_name;
	}

	public FakeVoteEvent(ConsoleCommandSender sender, String offline_player, String service_name) {
		this.sender = sender;
		this.offline_player = offline_player;
		this.service_name = service_name;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return p;
	}

	public ConsoleCommandSender getSender() {
		return sender;
	}

	public String getServiceName() {
		return service_name;
	}

	public String getOfflineTarget() {
		return offline_player;
	}

	public Player getTarget() {
		return target;
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
