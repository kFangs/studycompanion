/**
 * 
 */
package controllers;

/**
 * @author saroj-gautam
 *
 */

import models.RegisterUser;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Register extends Controller {
	
	public static void register() {
        render();
    }
	
	public static void index() {
        register();
    }
	
	/**
	 * A method that controls the login form
	 * @param username
	 * @param password
	 */
	public static void login(String email, String password) {
		System.out.println("In Login Method");
		System.out.println("Email	 =: " + email);
    	System.out.println("Password =: " + password);
		RegisterUser registerUser = RegisterUser.find("byEmailAndPassword",email, password).first();
		System.out.println("In Login, registerUser: " + registerUser);
		if (registerUser != null) {
			session.put("firstName", registerUser.getFirstName());
			flash.success("Welcome, " + registerUser.getFirstName());
			Profile.index();
		}
		System.out.println("Login Failed...");
//		flash.put("email", registerUser.getEmail());
//		flash.error("Login failed");
		index();
	}
	
	/**
	 * 
	 * @param registerUser
	 */
	public static void registerUser(@Valid RegisterUser registerUser) {
		validation.required(registerUser.getRepeatPassword());
//		validation.required(registerUser.getSex());
        validation.equals(registerUser.getRepeatPassword(), registerUser.getPassword()).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@register", registerUser, registerUser.getRepeatPassword());
        }
        try {
        	System.out.println("Username => " + registerUser.getEmail());
        	System.out.println("Password => " + registerUser.getPassword());
        	System.out.println("Sex => " + registerUser.getSex());
        	System.out.println("dateOfBirth " + registerUser.getDateOfBirth());
			// registering the user in the database
			registerUser.create();
		} catch (Exception e) {
			e.printStackTrace();
			renderJSON("{\"error\": true, \"message\": \"Registration failed! Something went wrong.\"}");
		}
		session.put("email", registerUser.getEmail());
		session.put("registerUser", registerUser);
        flash.success("Welcome, " + registerUser.getFirstName());
        Profile.completeProfile();
	}
}
