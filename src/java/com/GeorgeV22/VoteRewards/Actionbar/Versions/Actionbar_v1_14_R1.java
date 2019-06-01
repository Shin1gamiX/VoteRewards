package com.GeorgeV22.VoteRewards.Actionbar.Versions;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;

import com.GeorgeV22.VoteRewards.Actionbar.Actionbar;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;

public class Actionbar_v1_14_R1 implements Actionbar {

	@Override
	public void sendActionbar(Object p, String msg) {
		PacketPlayOutChat packet = new PacketPlayOutChat(
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg + "\"}"));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
