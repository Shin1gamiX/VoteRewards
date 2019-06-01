package com.GeorgeV22.VoteRewards.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.GeorgeV22.VoteRewards.Main;

import java.util.List;
import java.util.Random;

public class Utils {
	private Main m = Main.getPlugin(Main.class);

	public void sendMessage(Object object, String msg) {
		CommandSender sender = (CommandSender) object;
		if (sender instanceof Player) {
			sender.sendMessage(msg);
		} else {
			ConsoleCommandSender con = (ConsoleCommandSender) object;
			con.sendMessage(msg);
		}
	}

	public String color(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}

	public void PlaySound(Player p, Sound sound, int volume, int pitch) {
		p.playSound(p.getEyeLocation(), sound, volume, pitch);
	}

	String chooseRandom() {
		List<String> list = m.getConfig().getStringList("VoteParty.Commands");
		Random random = new Random();
		int selector = random.nextInt(list.size());
		return list.get(selector);
	}

	public void firework(Player p) {
		Firework fireWork = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwMeta = fireWork.getFireworkMeta();

		fwMeta.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.BALL).with(Type.BALL_LARGE)
				.with(Type.BURST).with(Type.STAR).withColor(Color.ORANGE).withTrail().withColor(Color.YELLOW)
				.withFade(Color.PURPLE).withFade(Color.RED).build());

		fireWork.setFireworkMeta(fwMeta);
	}

	public void sendTitle(Object player, String title, String subtitle) {
		if (player instanceof Player) {
			Player p = (Player) player;
			m.getTitle().sendTitle(p, title, subtitle, 4, 23, 4);
		} else {
			System.out.println("Error");
		}
	}

	public void sendActionbar(Object object, String msg) {
		m.getActionbar().sendActionbar(object, msg);
	}

}