package com.GeorgeV22.VoteRewards.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.GeorgeV22.VoteRewards.Main;
import com.GeorgeV22.VoteRewards.Utilities.XMaterial;

public class AdminGUI implements Listener {

	private Main m = Main.getPlugin(Main.class);

	public Inventory getInventory() {
		Inventory inventory = Bukkit.createInventory(null, 27, "§9VoteRewards §cAdmin");
		ItemStack item1 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item1Meta = item1.getItemMeta();
		item1Meta.setDisplayName("§8» §6§lOptions");
		item1.setItemMeta(item1Meta);

		ItemStack item2 = new ItemStack(XMaterial.PAPER.parseMaterial());
		ItemMeta item2Meta = item2.getItemMeta();
		item2Meta.setDisplayName("§8» §6§lReload");
		item2.setItemMeta(item2Meta);

		ItemStack item3 = new ItemStack(XMaterial.STICKY_PISTON.parseMaterial());
		ItemMeta item3Meta = item3.getItemMeta();
		item3Meta.setDisplayName("§8» §6§lVote Party");
		item3.setItemMeta(item3Meta);

		ItemStack item4 = new ItemStack(XMaterial.CAKE.parseMaterial());
		ItemMeta item4Meta = item4.getItemMeta();
		String chatmessagetype = m.getConfig().getString("Options.ChatMessageType");
		item4Meta.setDisplayName("§8» §6§lChat Message Type: §5" + chatmessagetype);
		item4.setItemMeta(item4Meta);

		ItemStack border = new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15);
		ItemMeta borderMeta = border.getItemMeta();
		borderMeta.setDisplayName("§8 ");
		border.setItemMeta(borderMeta);

		inventory.setItem(10, item1);
		inventory.setItem(12, item2);
		inventory.setItem(14, item3);
		inventory.setItem(16, item4);
		for (int i = 0; i <= 9; i++) {
			inventory.setItem(i, border);
		}
		for (int i = 17; i <= 26; i++) {
			inventory.setItem(i, border);
		}

		return inventory;
	}

	private Inventory getOptionsInventory() {
		Inventory inv = Bukkit.getServer().createInventory(null, 45, "§cAdmin - Options");

		ItemStack item1 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item1Meta = item1.getItemMeta();
		if (m.getConfig().getBoolean("Options.Updater"))
			item1Meta.setDisplayName("§8» §6§lUpdater §e- §a§lON");
		else
			item1Meta.setDisplayName("§8» §6§lUpdater §e- §c§lOFF");
		item1.setItemMeta(item1Meta);

		ItemStack item2 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item2Meta = item2.getItemMeta();
		if (m.getConfig().getBoolean("Options.Offline"))
			item2Meta.setDisplayName("§8» §6§lOffline §e- §a§lON");
		else
			item2Meta.setDisplayName("§8» §6§lOffline §e- §c§lOFF");
		item2.setItemMeta(item2Meta);

		ItemStack item3 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item3Meta = item3.getItemMeta();
		if (m.getConfig().getBoolean("Options.FakeVote"))
			item3Meta.setDisplayName("§8» §6§lFake Vote §e- §a§lON");
		else
			item3Meta.setDisplayName("§8» §6§lFake Vote §e- §c§lOFF");
		item3.setItemMeta(item3Meta);

		ItemStack item4 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item4Meta = item4.getItemMeta();
		if (m.getConfig().getBoolean("Options.VoteParty"))
			item4Meta.setDisplayName("§8» §6§lVote Party §e- §a§lON");
		else
			item4Meta.setDisplayName("§8» §6§lVote Party §e- §c§lOFF");
		item4.setItemMeta(item4Meta);

		ItemStack item5 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item5Meta = item5.getItemMeta();
		if (m.getConfig().getBoolean("Options.PermVote"))
			item5Meta.setDisplayName("§8» §6§lPerm Vote §e- §a§lON");
		else
			item5Meta.setDisplayName("§8» §6§lPerm Vote §e- §c§lOFF");
		item5.setItemMeta(item5Meta);

		ItemStack item6 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item6Meta = item6.getItemMeta();
		if (m.getConfig().getBoolean("Options.LuckyVote"))
			item6Meta.setDisplayName("§8» §6§lLucky Vote §e- §a§lON");
		else
			item6Meta.setDisplayName("§8» §6§lLucky Vote §e- §c§lOFF");
		item6.setItemMeta(item6Meta);

		ItemStack item7 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item7Meta = item7.getItemMeta();
		if (m.getConfig().getBoolean("Options.Cumulative"))
			item7Meta.setDisplayName("§8» §6§lCumulative Vote §e- §a§lON");
		else
			item7Meta.setDisplayName("§8» §6§lCumulative Vote §e- §c§lOFF");
		item7.setItemMeta(item7Meta);

		ItemStack item8 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item8Meta = item8.getItemMeta();
		if (m.getConfig().getBoolean("Options.ChatMessage"))
			item8Meta.setDisplayName("§8» §6§lChat Message §e- §a§lON");
		else
			item8Meta.setDisplayName("§8» §6§lChat Message §e- §c§lOFF");
		item8.setItemMeta(item8Meta);

		ItemStack item9 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item9Meta = item9.getItemMeta();
		if (m.getConfig().getBoolean("Options.Title"))
			item9Meta.setDisplayName("§8» §6§lTitle §e- §a§lON");
		else
			item9Meta.setDisplayName("§8» §6§lTitle §e- §c§lOFF");
		item9.setItemMeta(item9Meta);

		ItemStack item10 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item10Meta = item10.getItemMeta();
		if (m.getConfig().getBoolean("Options.Actionbar"))
			item10Meta.setDisplayName("§8» §6§lActionbar §e- §a§lON");
		else
			item10Meta.setDisplayName("§8» §6§lActionbar §e- §c§lOFF");
		item10.setItemMeta(item10Meta);

		ItemStack item11 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item11Meta = item11.getItemMeta();
		if (m.getConfig().getBoolean("Options.Reminder.Enabled"))
			item11Meta.setDisplayName("§8» §6§lReminder §e- §a§lON");
		else
			item11Meta.setDisplayName("§8» §6§lReminder §e- §c§lOFF");
		item11.setItemMeta(item11Meta);

		ItemStack item12 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item12Meta = item12.getItemMeta();
		if (m.getConfig().getBoolean("Options.Metrics"))
			item12Meta.setDisplayName("§8» §6§lMetrics §e- §a§lON");
		else
			item12Meta.setDisplayName("§8» §6§lMetrics §e- §c§lOFF");
		item12.setItemMeta(item12Meta);

		ItemStack item13 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item13Meta = item13.getItemMeta();
		if (m.getConfig().getBoolean("Options.MySQL"))
			item13Meta.setDisplayName("§8» §6§lMySQL §e- §a§lON");
		else
			item13Meta.setDisplayName("§8» §6§lMySQL §e- §c§lOFF");
		item13.setItemMeta(item13Meta);

		ItemStack item14 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item14Meta = item14.getItemMeta();
		if (m.getConfig().getBoolean("Options.VoteTop.Header") || m.getConfig().getBoolean("Options.VoteTop.Footer"))
			item14Meta.setDisplayName("§8» §6§lVote Top §e- §a§lON");
		else
			item14Meta.setDisplayName("§8» §6§lVote Top §e- §c§lOFF");
		item14.setItemMeta(item14Meta);

		ItemStack item15 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item15Meta = item15.getItemMeta();
		if (m.getConfig().getBoolean("Options.Daily"))
			item15Meta.setDisplayName("§8» §6§lDaily §e- §a§lON");
		else
			item15Meta.setDisplayName("§8» §6§lDaily §e- §c§lOFF");
		item15.setItemMeta(item15Meta);

		ItemStack item16 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item16Meta = item16.getItemMeta();
		if (m.getConfig().getBoolean("Options.Vote Goal.enable"))
			item16Meta.setDisplayName("§8» §6§lVote Goal §e- §a§lON");
		else
			item16Meta.setDisplayName("§8» §6§lVote Goal §e- §c§lOFF");
		item16.setItemMeta(item16Meta);

		ItemStack border = new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15);
		ItemMeta borderMeta = border.getItemMeta();
		borderMeta.setDisplayName("§8 ");
		border.setItemMeta(borderMeta);

		for (int i = 0; i <= 9; i++) {
			inv.setItem(i, border);
		}
		inv.setItem(10, item1);
		inv.setItem(11, item2);
		inv.setItem(12, item3);
		inv.setItem(13, item4);
		inv.setItem(14, item5);
		inv.setItem(15, item6);
		inv.setItem(16, item7);
		inv.setItem(17, border);
		inv.setItem(18, border);
		inv.setItem(19, item8);
		inv.setItem(20, item9);
		inv.setItem(21, item10);
		inv.setItem(22, item11);
		inv.setItem(23, item12);
		inv.setItem(24, item13);
		inv.setItem(25, item14);

		for (int i = 26; i <= 44; i++) {
			inv.setItem(i, border);
		}
		inv.setItem(30, item15);
		inv.setItem(32, item16);
		return inv;
	}

	private Inventory getVotePartyInventory() {
		Inventory inv = Bukkit.getServer().createInventory(null, 36, "§cAdmin - VoteParty Options");

		ItemStack item1 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item1Meta = item1.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Sound"))
			item1Meta.setDisplayName("§8» §6§lSound §e- §a§lON");
		else
			item1Meta.setDisplayName("§8» §6§lSound §e- §c§lOFF");
		item1.setItemMeta(item1Meta);

		ItemStack item2 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item2Meta = item2.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Random Rewards"))
			item2Meta.setDisplayName("§8» §6§lRandom Rewards §e- §a§lON");
		else
			item2Meta.setDisplayName("§8» §6§lRandom Rewards §e- §c§lOFF");
		item2.setItemMeta(item2Meta);

		ItemStack item3 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item3Meta = item3.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Participate"))
			item3Meta.setDisplayName("§8» §6§lParticipate §e- §a§lON");
		else
			item3Meta.setDisplayName("§8» §6§lParticipate §e- §c§lOFF");
		item3.setItemMeta(item3Meta);

		ItemStack item4 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item4Meta = item4.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Chat Message"))
			item4Meta.setDisplayName("§8» §6§lChat Message §e- §a§lON");
		else
			item4Meta.setDisplayName("§8» §6§lChat Message §e- §c§lOFF");
		item4.setItemMeta(item4Meta);

		ItemStack item5 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item5Meta = item5.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Crate.enable"))
			item5Meta.setDisplayName("§8» §6§lCrate §e- §a§lON");
		else
			item5Meta.setDisplayName("§8» §6§lCrate §e- §c§lOFF");
		item5.setItemMeta(item5Meta);

		ItemStack item6 = new ItemStack(XMaterial.BOOK.parseMaterial());
		ItemMeta item6Meta = item6.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Firework"))
			item6Meta.setDisplayName("§8» §6§lFirework §e- §a§lON");
		else
			item6Meta.setDisplayName("§8» §6§lFirework §e- §c§lOFF");
		item6.setItemMeta(item6Meta);

		ItemStack item7 = new ItemStack(XMaterial.STICKY_PISTON.parseMaterial());
		ItemMeta item7Meta = item7.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Crate.Sound"))
			item7Meta.setDisplayName("§8» §6§lCrate Sound §e- §a§lON");
		else
			item7Meta.setDisplayName("§8» §6§lCrate Sound §e- §c§lOFF");
		item7.setItemMeta(item7Meta);

		ItemStack item8 = new ItemStack(XMaterial.STICKY_PISTON.parseMaterial());
		ItemMeta item8Meta = item8.getItemMeta();
		if (m.getConfig().getBoolean("VoteParty.Crate.Cooldown"))
			item8Meta.setDisplayName("§8» §6§lCrate Cooldown §e- §a§lON");
		else
			item8Meta.setDisplayName("§8» §6§lCrate Cooldown §e- §c§lOFF");
		item8.setItemMeta(item8Meta);

		ItemStack border = new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15);
		ItemMeta borderMeta = border.getItemMeta();
		borderMeta.setDisplayName("§8 ");
		border.setItemMeta(borderMeta);

		for (int i = 0; i <= 9; i++) {
			inv.setItem(i, border);
		}
		inv.setItem(10, item1);
		inv.setItem(12, item2);
		inv.setItem(14, item3);
		inv.setItem(16, item4);
		inv.setItem(19, item5);
		inv.setItem(21, item6);
		inv.setItem(23, item7);
		inv.setItem(17, border);
		inv.setItem(18, border);
		inv.setItem(25, item8);
		for (int i = 26; i <= 35; i++) {
			inv.setItem(i, border);
		}

		return inv;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getView().getTitle().equals("§cAdmin - VoteParty Options")) {
			if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial())) {
				return;
			}

			if (e.getRawSlot() == 10) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Sound");
				m.getConfig().set("VoteParty.Sound", !bol);
			}
			if (e.getRawSlot() == 12) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Random Rewards");
				m.getConfig().set("VoteParty.Random Rewards", !bol);
			}

			if (e.getRawSlot() == 14) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Participate");
				m.getConfig().set("VoteParty.Participate", !bol);
			}

			if (e.getRawSlot() == 16) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Chat Message");
				m.getConfig().set("VoteParty.Chat Message", !bol);
			}

			if (e.getRawSlot() == 19) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Crate.enable");
				m.getConfig().set("VoteParty.Crate.enable", !bol);
			}

			if (e.getRawSlot() == 21) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Firework");
				m.getConfig().set("VoteParty.Firework", !bol);
			}

			if (e.getRawSlot() == 23) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Crate.Sound");
				m.getConfig().set("VoteParty.Crate.Sound", !bol);
			}

			if (e.getRawSlot() == 25) {
				boolean bol = m.getConfig().getBoolean("VoteParty.Crate.Cooldown");
				m.getConfig().set("VoteParty.Crate.Cooldown", !bol);
			}

			Player player = (Player) e.getWhoClicked();
			player.openInventory(getVotePartyInventory());
			e.setCancelled(true);
			return;
		}
		if (e.getView().getTitle().equals("§9VoteRewards §cAdmin")) {
			if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial())) {
				return;
			}
			if (e.getRawSlot() == 10) {
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 12) {
				m.getData().reload();
				m.getConfig().reload();
				m.getMessages().reload();
			}
			if (e.getRawSlot() == 14) {
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getVotePartyInventory());
			}

			if (e.getRawSlot() == 16) {
				if (m.getConfig().getString("Options.ChatMessageType").equals("BROADCAST")) {
					m.getConfig().set("Options.ChatMessageType", "PRIVATE");
					m.getConfig().save();
				} else if (m.getConfig().getString("Options.ChatMessageType").equals("PRIVATE")) {
					m.getConfig().set("Options.ChatMessageType", "BROADCAST");
					m.getConfig().save();
				} else {
					m.getConfig().set("Options.ChatMessageType", "BROADCAST");
					m.getConfig().save();
				}
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getInventory());
			}
			e.setCancelled(true);
			return;
		}
		if (e.getView().getTitle().equals("§cAdmin - Options")) {
			if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial())) {
				return;
			}
			if (e.getRawSlot() == 10) {
				boolean bool = m.getConfig().getBoolean("Options.Updater");
				m.getConfig().set("Options.Updater", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 11) {
				boolean bool = m.getConfig().getBoolean("Options.Offline");
				m.getConfig().set("Options.Offline", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 12) {
				boolean bool = m.getConfig().getBoolean("Options.FakeVote");
				m.getConfig().set("Options.FakeVote", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 13) {
				boolean bool = m.getConfig().getBoolean("Options.VoteParty");
				m.getConfig().set("Options.VoteParty", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 14) {
				boolean bool = m.getConfig().getBoolean("Options.PermVote");
				m.getConfig().set("Options.PermVote", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 15) {
				boolean bool = m.getConfig().getBoolean("Options.LuckyVote");
				m.getConfig().set("Options.LuckyVote", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 16) {
				boolean bool = m.getConfig().getBoolean("Options.Cumulative");
				m.getConfig().set("Options.Cumulative", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 19) {
				boolean bool = m.getConfig().getBoolean("Options.ChatMessage");
				m.getConfig().set("Options.ChatMessage", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 20) {
				boolean bool = m.getConfig().getBoolean("Options.Title");
				m.getConfig().set("Options.Title", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 21) {
				boolean bool = m.getConfig().getBoolean("Options.Actionbar");
				m.getConfig().set("Options.Actionbar", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 22) {
				boolean bool = m.getConfig().getBoolean("Options.Reminder.Enabled");
				m.getConfig().set("Options.Reminder.Enabled", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 23) {
				boolean bool = m.getConfig().getBoolean("Options.Metrics");
				m.getConfig().set("Options.Metrics", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 24) {
				boolean bool = m.getConfig().getBoolean("Options.MySQL");
				m.getConfig().set("Options.MySQL", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 25) {
				boolean bool = m.getConfig().getBoolean("Options.VoteTop.Header");
				m.getConfig().set("Options.VoteTop.Header", !bool);
				m.getConfig().set("Options.VoteTop.Footer", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}

			if (e.getRawSlot() == 30) {
				boolean bool = m.getConfig().getBoolean("Options.Daily");
				m.getConfig().set("Options.Daily", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			if (e.getRawSlot() == 32) {
				boolean bool = m.getConfig().getBoolean("Options.Vote Goal.enable");
				m.getConfig().set("Options.Daily", !bool);
				m.getConfig().save();
				Player player = (Player) e.getWhoClicked();
				player.openInventory(getOptionsInventory());
			}
			e.setCancelled(true);
			return;

		}

	}

}
