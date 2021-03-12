package com.tweetapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tweetapp.dao.TweetDao;

public class TweetService {
	public Connection con = null;
	public PreparedStatement ps = null;
	public TweetDao tweetDao = new TweetDao();

	public void postTweet(String email, String tweet) {

		if (tweet.length() < Integer.MAX_VALUE - 1) {
			if (tweetDao.postTweet(email, tweet))
				System.out.println("Tweet posted Successfully.");
			else
				System.out.println("Something went wrong. Please try again.");
		} else
			System.out.println("Size of the tweet has exceeded. Please try again.");

	}

	public void myTweets(String email) {

		try {
			ResultSet rs = tweetDao.myTweets(email);
			while (rs.next()) {
				System.out.println("You posted : ");
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void viewAllTweets(String email) {
		try {
			ResultSet rs = tweetDao.viewAllTweets(email);
			if (rs != null) {
				while (rs.next()) {
					System.out.println(rs.getString(2) + " posted:\n " + rs.getString(1));
				}
			} else {
				System.out.println("Tweets do not exist. Go ahaead and post a tweet.");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
