package com.tweetapp.controller;

import java.util.Scanner;

import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;

public class MainMenuController {
	UserService userService = new UserService();
	TweetService tweetService = new TweetService();
	Scanner sc = new Scanner(System.in);
	public void beforeLogin() {
		int choice = -1;
		String firstName, lastName, gender, dob, email, password;
		do {

			System.out.println(
					"Hi!\nChoose from the below options and enter your choice:\n1.Register\n2.Login\n3.Forgot Password\n4.Exit");
			Scanner sc = new Scanner(System.in);
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid Choice");
			}

			switch (choice) {
			case 1:
				sc.nextLine();
				System.out.println("Enter your First Name :");
				firstName = sc.nextLine();
				System.out.println("Enter your Last Name :");
				lastName = sc.nextLine();
				// sc.nextLine();
				System.out.println("Enter your Gender(M or F) :");
				gender = sc.nextLine();
				System.out.println("Enter your Date Of Birth(dd/mm/YYYY) :");
				dob = sc.nextLine();
				System.out.println("Enter your Email :");
				email = sc.nextLine();
				System.out.println(
						"Enter your password (It must be 8-20 characters, must contain digits, one upper case and one lower case alphabet) :");
				password = sc.nextLine();
				userService.registerUser(firstName, lastName, gender, dob, email, password);
				break;

			case 2:
				sc.nextLine();
				System.out.println("Enter your Email :");
				email = sc.nextLine();
				System.out.println("Enter your password :");
				password = sc.nextLine();
				if (userService.login(email, password)) {
					System.out.println("Log in Successful.");
					afterLogin(email, password);
				} else {
					System.out.println("Incorrect Details.");
				}
				break;
			case 3:
				sc.nextLine();
				System.out.println("Enter your Email :");
				email = sc.nextLine();
				System.out.println("Enter your Date Of Birth(dd/mm/YYYY) :");
				dob = sc.nextLine();
				System.out.println("Enter your New Password :");
				password = sc.nextLine();
				userService.forgotPassword(email, dob, password);
				break;
			case 4:
				System.out.println("Thank you!");
				sc.close();
				break;
			}

		} while (choice != 4);
	}

	public void afterLogin(String email, String password) {
		int choice = -1;

		do {
			System.out.println("Hi " + email + "!");
			System.out.println(
					"Below are the options you can choose from \n1.Post a tweet.\n2.View my tweets.\n3.View all tweets.\n4.View all users.\n5.Reset Password\n6.Logout");
			System.out.println("Enter your choice:");
//			Scanner sc = new Scanner(System.in);
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid Choice");
			}
			switch (choice) {
			case 1:
				sc.nextLine();
				System.out.println("Enter the tweet you would like to post:");
				String tweet = sc.nextLine();
				tweetService.postTweet(email, tweet);
				break;
			case 2:
				tweetService.myTweets(email);
				break;
			case 3:
				tweetService.viewAllTweets(email);
				break;
			case 4:
				userService.viewAllUsers(email);
				break;
			case 5:
				sc.nextLine();
				System.out.println("Enter old password: ");
				String oldPassword = sc.nextLine();
				System.out.println("Enter new password:");
				String newPassword = sc.nextLine();
				userService.resetPassword(email, password, oldPassword, newPassword);
				break;
			case 6:
				userService.logout(email);
				break;
			}
		} while (choice != 6);

	}

}
