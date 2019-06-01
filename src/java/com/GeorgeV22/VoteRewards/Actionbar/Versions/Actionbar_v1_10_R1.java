package com.GeorgeV22.VoteRewards.Actionbar.Versions;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;

import com.GeorgeV22.VoteRewards.Actionbar.Actionbar;

public class Actionbar_v1_10_R1 implements Actionbar {

	@Override
	public void sendActionbar(Object p, String msg) {
		PacketPlayOutChat packet = new PacketPlayOutChat(
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg + "\"}"), (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
