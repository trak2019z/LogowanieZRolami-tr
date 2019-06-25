package com.roles.ai.demo.Controllers;

import com.roles.ai.demo.Components.UserValidator;
import com.roles.ai.demo.Entities.User;
import com.roles.ai.demo.Services.SecurityService;
import com.roles.ai.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/register")
    public String registrationModule(Model model){
        model.addAttribute("userModel", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registrationModule(@ModelAttribute("userModel")User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return "register";
        }
        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession s, HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        if (s != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        return "redirect:/login";
    }

    @GetMapping({"/", "/home"})
    public String welcome(Model model) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", loggedInUser.getName());
        model.addAttribute("authority", loggedInUser.getAuthorities());
        return "home";
    }



}
