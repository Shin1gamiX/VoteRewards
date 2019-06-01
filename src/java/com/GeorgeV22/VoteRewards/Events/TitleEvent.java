package com.GeorgeV22.VoteRewards.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TitleEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;
	private Player p;
	private String title;
	private String subtitle;
	private int var1;
	private int var2;
	private int var3;

	public TitleEvent(Player p, String title, String subtitle, int var1, int var2, int var3) {
		this.p = p;
		this.title = title;
		this.subtitle = subtitle;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return p;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public int getVar1() {
		return var1;
	}

	public int getVar2() {
		return var2;
	}

	public int getVar3() {
		return var3;
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
