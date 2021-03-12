package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TweetDao {
	public Connection con = null;
	public PreparedStatement ps = null;
	private String tweet;
	private String email;

	public TweetDao() {
		super();

	}

	public TweetDao(String tweet, String email) {
		super();
		this.tweet = tweet;
		this.email = email;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean postTweet(String email, String tweet) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			ps = con.prepareStatement("insert into tweets values(?,?)");
			ps.setString(1, tweet);
			ps.setString(2, email);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	public ResultSet myTweets(String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			ps = con.prepareStatement("select * from tweets where email=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			return rs;
//			while (rs.next()) {
//				System.out.println("You posted : ");
//				System.out.println(rs.getString(1));

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public ResultSet viewAllTweets(String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			ps = con.prepareStatement("select logged_in from users where email=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String loggedIn = rs.getString(1);
			if (loggedIn.equalsIgnoreCase("true")) {
				ps = con.prepareStatement("select * from tweets");
				rs = ps.executeQuery();
//				while (rs.next()) {
//					System.out.println(rs.getString(2) + " posted:\n " + rs.getString(1));
//				}
				return rs;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
}
