package com.jpa.everythingshop.controller;

import com.jpa.everythingshop.logTrace.Trace;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@Trace
@RequestMapping("/user")
public class UserController {
    @GetMapping("/signIn")
    public String saveSignIn(Model model, HttpSession session) {
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            //세션정보 담은 후 세션은 삭제
            session.removeAttribute("errorMessage");
        }
        return "signIn";
    }
}
