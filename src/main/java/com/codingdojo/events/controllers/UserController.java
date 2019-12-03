package com.codingdojo.events.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.events.models.User;
import com.codingdojo.events.services.EventService;
import com.codingdojo.events.services.UserService;
import com.codingdojo.events.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	private final String[] states = { "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "IA", "ID",
	        "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ",
	        "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV",
	        "WY" };
	
	//----------------------------------------------------------------
	// LoginRegistration Page - Get Route
	//----------------------------------------------------------------
	
	@GetMapping("/")
	  public String registerForm(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("states", states);  
        return "index.jsp";
    }
	
	//----------------------------------------------------------------
	// Registration Page - Post Route
	//----------------------------------------------------------------
    @PostMapping(value="/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
    	userValidator.validate(user, result);
		model.addAttribute("states", states);  
    	if(result.hasErrors()) {
    		return "index.jsp";
    	}else{
    	User u = userService.registerUser(user);
    	session.setAttribute("userId", u.getId());
    	return "redirect:/events";
    	}
    }
	
	//----------------------------------------------------------------
	// Login Page - Post Route
	//----------------------------------------------------------------
    @PostMapping(value="/login")
    public String loginUser(@RequestParam("email") String email, @ModelAttribute("user") User user, @RequestParam("password") String password, Model model, HttpSession session) {
        boolean isAuthenticated = userService.authenticateUser(email, password);
        if(isAuthenticated) {
        	User u =userService.findByEmail(email); 
        	session.setAttribute("userId", u.getId() );
        	return "redirect:/events";
        }else {
        	model.addAttribute("error", "Invalid credentials. Try again!");
        	model.addAttribute("states", states);
        	return "index.jsp";
        }
    }
	
	//----------------------------------------------------------------
	// Logout Page - Get Route
	//----------------------------------------------------------------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }

}
