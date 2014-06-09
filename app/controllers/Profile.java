/**
 * 
 */
package controllers;

import javax.validation.Valid;

import models.SubmitProfile;
import play.mvc.Controller;

/**
 * @author saroj-gautam
 *
 */
public class Profile extends Controller {
	
	public static void index() {
        render();
    }
	
	public static void completeProfile() {
		System.out.println("Inside completeProfile....");
		render();
	}
	
	/**
	 * Method that handles data required for Profile Completion
	 * @param submitProfile
	 */
	public static void fillProfile(@Valid SubmitProfile submitProfile){
		validation.required(submitProfile.getEmail());
		if(validation.hasErrors()){
			//render("@profile", submitProfile, submitProfile.getEmail());
		}
		try {
        	System.out.println("Email => " + submitProfile.getEmail());
        	System.out.println("FirstName => " + submitProfile.getFirstName());
        	System.out.println("University => " + submitProfile.getUniversity());
        	System.out.println("Semester => " + submitProfile.getSemester());
        	System.out.println("City => " + submitProfile.getCity());
        	// saving profile for users
        	submitProfile.create();
		} catch (Exception e) {
			e.printStackTrace();
			renderJSON("{\"error\": true, \"message\": \"Completing Profile failed! Something went wrong.\"}");
		}
		Profile.index();
	}
}
