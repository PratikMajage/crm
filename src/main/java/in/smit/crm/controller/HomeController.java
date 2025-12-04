package in.smit.crm.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller
 * Handles login, home page, and common pages
 */
@Controller
public class HomeController {

    /**
     * Home page - accessible to everyone
     * URL: http://localhost:8080/
     */
    @GetMapping("/")
    public String home() {
        return "index"; // returns templates/index.html
    }

    /**
     * Login page
     * URL: http://localhost:8080/login
     * Spring Security handles the actual login process
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // returns templates/login.html
    }

    /**
     * Dashboard - redirects based on user role
     * URL: http://localhost:8080/dashboard
     * After login, user is redirected here
     */
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails) {
        // Check user's role and redirect accordingly
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            return "redirect:/student/dashboard";
        }

        // Default fallback
        return "redirect:/";
    }

    /**
     * Access Denied page
     * URL: http://localhost:8080/access-denied
     * Shown when user tries to access unauthorized page
     */
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message", "You don't have permission to access this page");
        return "access-denied"; // returns templates/access-denied.html
    }
}