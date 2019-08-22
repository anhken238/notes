package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public ModelAndView login() {
        return new ModelAndView("/login/login");
    }

    @PostMapping("/login")
    public ModelAndView handlingFormLogin(@ModelAttribute("user") User users, HttpSession session) throws Exception {
        try {
            User user = userService.findByName(users.getName());
            if ((user != null) && (user.getName().equals(users.getName())) &&
                    (user.getPassword().equals(users.getPassword()))) {

                ModelAndView modelAndView = new ModelAndView("redirect:/list");
                session.setAttribute("myUser", user.getName());
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("/login/login");
                modelAndView.addObject("message", "login fail ... !");
                return modelAndView;
            }

        } catch (Exception e) {
            return new ModelAndView("/error.404");
        }
    }

        @GetMapping(value = "/logout",produces = "application/json;charset=UTF-8")
        public String logout (HttpServletRequest request){
            HttpSession session = request.getSession();
            session.invalidate();
            return "redirect:/login";
        }
    }
