package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signupUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "loginOrSignup";
		}
		if(userRepository.findByUsername(user.getUserName()) != null) {
			model.addAttribute("error", "User name already exists");
			return "login";
		}
		UserRepository.save(user.getUserName());
		return "redirect:/login?signupSuccess";
	}
	@GetMapping("/login")
	public String showLoginPage(@RequestParam(required = false) String e, Model model) {
		if(e != null) {
			model.addAttribute("error", "Invalid user name or password");
		}
		return "login";
	}
}
