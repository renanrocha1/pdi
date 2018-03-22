package com.renan;

import java.sql.*;
import java.util.*;

import org.sqlite.*;
import org.sqlite.SQLiteConfig.*;

public class MainTest {
	
	private static Connection c;
	
	public static void main(String[] args) throws SQLException {
		Statement stmt = null;
		try {
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			String sql = "SELECT time('now')";
			stmt = c.createStatement();
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			System.out.println(rs.getString(1));
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
		}
	}

}
