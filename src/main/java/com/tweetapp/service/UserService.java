package com.tweetapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tweetapp.dao.UserDao;

public class UserService {

	public UserDao userDao = new UserDao();
	public Connection con = null;
	public PreparedStatement ps = null;

	public boolean registerUser(String fn, String ln, String g, String dob, String email, String password) {

		boolean validFirstName = isValidName(fn);
		boolean validLastName = isValidName(ln);
		boolean validGender = false;
		if (g.equalsIgnoreCase("M") || g.equalsIgnoreCase("F"))
			validGender = true;
		boolean validDOB = isValidDateOfBirth(dob);
		boolean validEmail = isValidEmail(email);
		boolean validPassword = isValidPassword(password);

		if (validFirstName && validLastName && validEmail && validPassword && validDOB && validGender) {
			if (userDao.registerUser(fn, ln, g, dob, email, password)) {
				System.out.println("Registration Successful. Please login using the same details.");
				return true;
			} else {
				System.out.println("Registration Unsuccessful.");
				System.out.println(fn+ln+g+dob+email+password);
			}
		}
		if (!validFirstName) {
			System.out.println("Invalid First Name");
		}
		if (!validLastName) {
			System.out.println("Invalid Last Name");
		}
		if (!validEmail) {
			System.out.println("Invalid Email");
		}
		if (!validDOB) {
			System.out.println("Invalid Date Of Birth. Please enter in dd/mm/YYYY format.");
		}
		if (!validPassword) {
			System.out.println(
					"Invalid Password(It must be 8-20 characters, must contain digits, one upper case and one lower case alphabet)");
		}
		if (!validGender) {
			System.out.println("Invalid Gender");
		}
		return false;

	}

	public boolean login(String email, String password) {

		if (isValidEmail(email)) {
			if (userDao.login(email, password)) {
				return true;
			}
		}
		if (!isValidEmail(email)) {
			System.out.println("Invalid Email");
		}
		return false;

	}

	public boolean forgotPassword(String email, String dob, String password) {

		if (isValidEmail(email) && isValidDateOfBirth(dob) && isValidPassword(password)) {
			if (userDao.forgotPassword(email, dob, password)) {
				System.out.println("Updated password succesfully. Login using new password.");
				return true;
			} else
				System.out.println("Incorrect Details");
		}
		if (!isValidEmail(email)) {
			System.out.println("Invalid Password. Please try again.");
		}
		if (!isValidDateOfBirth(dob))
			System.out.println("Invalid Date Of Birth. Please enter in dd/mm/YYYY format.");
		if (!isValidPassword(password))
			System.out.println(
					"Invalid Password(It must be 8-20 characters, must contain digits, one upper case and one lower case alphabet)");

		return false;

	}

	public void viewAllUsers(String email) {

		try {
			ResultSet rs = userDao.viewAllUsers(email);

			if (rs != null) {
				System.out.println("List Of Users :");
				while (rs.next()) {
					System.out.println(rs.getString(5));
				}
			} else {
				System.out.println("Users do not exist.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void resetPassword(String email, String password, String oldPassword, String newPassword) {

		if (oldPassword.equals(password)) {
			if (isValidPassword(newPassword)) {
				if (userDao.resetPassword(email, oldPassword, newPassword))
					System.out.println("Password Reset Successful");
				else
					System.out.println("Incorrect details. Please try again.");
			} else
				System.out.println("Invalid Password");
		}

	}

	public void logout(String email) {
		System.out.println("Logged out");
		userDao.logout(email);
	}

	public static boolean isValidPassword(String password) {

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Pattern p = Pattern.compile(regex);
		if (password == null) {
			return false;
		}
		Matcher m = p.matcher(password);
		return m.matches();
	}

	public static boolean isValidEmail(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(email);
		return m.matches();

	}

	public static boolean isValidDateOfBirth(String dob) {
		String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(dob);
		return m.matches();
	}

	public static boolean isValidName(String name) {

		String regex = "^[A-Za-z]\\w{5,29}$";
		Pattern p = Pattern.compile(regex);
		if (name == null) {
			return false;
		}
		Matcher m = p.matcher(name);
		return m.matches();
	}
}
