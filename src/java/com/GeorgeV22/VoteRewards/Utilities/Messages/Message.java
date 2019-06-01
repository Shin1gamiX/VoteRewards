package com.GeorgeV22.VoteRewards.Utilities.Messages;

import com.GeorgeV22.VoteRewards.Main;

public class Message {

	private Main m = Main.getPlugin(Main.class);

	private MessageType type;
	private Object p;
	private String message;
	private String subtitle;

	public Message(Object p, MessageType type, String message) {
		this.type = type;
		this.p = p;
		this.message = message;
	}

	public Message(Object p, MessageType type, String message, String message2) {
		this.type = type;
		this.p = p;
		this.message = message;
		this.subtitle = message2;
	}

	public void send() {
		switch (type) {
		case CHAT:
			m.getUtils().sendMessage(p, message);
			break;
		case TITLE:
			m.getUtils().sendTitle(p, m.getUtils().color(message), null);
			break;
		case TITLE_WITH_SUBTITLE:
			m.getUtils().sendTitle(p, message, subtitle);
			break;
		case SUBTITLE:
			m.getUtils().sendTitle(p, null, subtitle);
			break;
		case ACTIONBAR:
			m.getUtils().sendActionbar(p, m.getUtils().color(message));
			break;
		case CHAT_WITH_COLOR:
			m.getUtils().sendMessage(p, m.getUtils().color(message));
			break;
		}
	}

}
