package com.hendisantika.controller;

import com.hendisantika.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Đăng ký người dùng
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        if (userService.register(username, email, password)) {
            return "redirect:/login"; // Redirect to login after successful registration
        } else {
            model.addAttribute("error", "Registration failed");
            return "register";
        }
    }

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Đăng nhập người dùng
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (userService.login(username, password)) {
            session.setAttribute("username", username); // Lưu tên người dùng vào session
            return "redirect:/"; // Redirect to homepage after successful login
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session
        return "redirect:/"; // Redirect to homepage after logout
    }
}
