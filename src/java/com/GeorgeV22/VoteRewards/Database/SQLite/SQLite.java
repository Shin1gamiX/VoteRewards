package com.GeorgeV22.VoteRewards.Database.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.GeorgeV22.VoteRewards.Database.MySQL.Database;

public class SQLite extends Database {

	private final String file;

	public SQLite(String file) {
		this.file = file;
	}

	@Override
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		if (checkConnection()) {
			return connection;
		}

		String connectionURL = "jdbc:sqlite:" + file;

		connection = DriverManager.getConnection(connectionURL);
		connection.createStatement().setQueryTimeout(Integer.MAX_VALUE);
		return connection;
	}
}
