package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	private String firstName;
	private String lastName;
	private String gender;
	private String dateOfBirth;
	private String email;
	private String password;
	private String loggedIn;
//	public Connection con = null;
//	public PreparedStatement ps = null;

	public UserDao() {
		super();
	}

	public UserDao(String firstName, String lastName, String gender, String dateOfBirth, String email, String password,
			String loggedIn) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.loggedIn = loggedIn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(String loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean registerUser(String fn, String ln, String g, String dob, String email, String password) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			PreparedStatement ps = con.prepareStatement("select count(email) as total from users where email=?");
//			int c= con.prepareStatement("select count(email) as total from users where email=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int c=rs.getInt("total");
			System.out.println(c);
			if (c==0) {
				ps = con.prepareStatement(
						"insert into users(first_name,last_name,gender,date_of_birth,email,password) values(?,?,?,?,?,?)");
				ps.setString(1, fn);
				ps.setString(2, ln);
				ps.setString(3, g);
				ps.setString(4, dob);
				ps.setString(5, email);
				ps.setString(6, password);
				ps.executeUpdate();
				return true;

			} else {
				System.out.println(
						"Email already in use. Try registering with another email or login using the same email.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean login(String email, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from users where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String pass = rs.getString(6);
			if (pass.equals(password)) {
				ps = con.prepareStatement("update users set logged_in = ? where email=?");
				ps.setString(1, "TRUE");
				ps.setString(2, email);
				ps.executeUpdate();
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Incorrect Details. Please try again.");
		return false;
	}

	public boolean forgotPassword(String email, String dob, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			PreparedStatement ps = con.prepareStatement("select * from users where email = ? ");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs != null) {
				if (rs.getString(4).equals(dob)) {
					ps = con.prepareStatement("update users set password =? where email=?");
					ps.setString(1, password);
					ps.setString(2, email);
					ps.executeUpdate();
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public ResultSet viewAllUsers(String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			PreparedStatement ps = con.prepareStatement("select logged_in from users where email=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String loggedIn = rs.getString(1);
			if (loggedIn.equalsIgnoreCase("true")) {
				ps = con.prepareStatement("select * from users");
				rs = ps.executeQuery();
				return rs;
//				System.out.println("List Of Users :");
//				while (rs.next()) {
//					System.out.println(rs.getString(5));
//				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public boolean resetPassword(String email, String oldPassword, String newPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			PreparedStatement ps = con.prepareStatement("update users set password =? where email=?");
			ps.setString(1, newPassword);
			ps.setString(2, email);
			ps.executeUpdate();
			return true;

		} catch (Exception e) {
			System.out.println(e);
		}
		return false;

	}

	public void logout(String email) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweetapp", "root", "root");
			PreparedStatement ps = con.prepareStatement("update users set logged_in = ? where email=?");
			ps.setString(1, "false");
			ps.setString(2, email);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
