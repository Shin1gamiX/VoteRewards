package com.GeorgeV22.VoteRewards.Database;

import com.GeorgeV22.VoteRewards.Database.MySQL.Database;
import com.GeorgeV22.VoteRewards.Database.MySQL.MySQL;
import com.GeorgeV22.VoteRewards.Database.SQLite.SQLite;

public class DB {

	private DatabaseType type;

	public DB(DatabaseType type) {
		this.type = type;
	}

	public Database connect(String hostname, String port, String database, String username, String password) {
		switch (type) {
		case MySQL:
			return new MySQL(hostname, port, database, username, password);
		case SQLite:
			return new SQLite(hostname);
		default:
			break;
		}
		return null;
	}

}
