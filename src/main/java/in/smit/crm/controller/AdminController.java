package in.smit.crm.controller;

import in.smit.crm.security.CustomUserDetails;
import in.smit.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Admin Controller
 * Handles admin dashboard ONLY
 * Other pages are handled by their specific controllers
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Admin Dashboard - Main admin page with statistics
     * URL: http://localhost:8080/admin/dashboard
     * Thymeleaf: templates/admin/dashboard.html
     */
    @GetMapping("/dashboard")
    public String adminDashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // Get current admin user
        model.addAttribute("currentUser", userDetails.getUser());

        // Dashboard statistics
        model.addAttribute("totalStudents", studentService.getTotalStudents());
        model.addAttribute("totalCourses", courseService.getTotalCourses());
        model.addAttribute("activeEnrollments", enrollmentService.countActiveEnrollments());
        model.addAttribute("totalRevenue", paymentService.calculateTotalRevenue());
        model.addAttribute("pendingAmount", paymentService.calculatePendingAmount());

        // Recent notifications
        model.addAttribute("recentNotifications", notificationService.getRecentNotifications());

        return "admin/dashboard";
    }
}