package in.smit.crm.controller;

import in.smit.crm.entity.Student;
import in.smit.crm.security.CustomUserDetails;
import in.smit.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Student Controller
 * Handles all student-specific pages
 * Only accessible to users with STUDENT role
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private EnrollmentService enrollmentService;

    // @Autowired
    // private AttendanceService attendanceService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Student Dashboard
     * URL: http://localhost:8080/student/dashboard
     * Thymeleaf: templates/student/dashboard.html
     */
    @GetMapping("/dashboard")
    public String studentDashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // Get current logged-in student
        Student currentStudent = studentService.getStudentByUserId(userDetails.getUser().getId());

        if (currentStudent == null) {
            model.addAttribute("error", "Student profile not found. Please contact admin.");
            return "error";
        }

        model.addAttribute("student", currentStudent);

        // Get student's enrollments
        model.addAttribute("enrollments",
                enrollmentService.getEnrollmentsByStudentId(currentStudent.getId()));

        // Get recent notifications
        model.addAttribute("notifications", notificationService.getRecentNotifications());

        return "student/dashboard";
    }

    /**
     * My Courses - View enrolled courses
     * URL: http://localhost:8080/student/my-courses
     * Thymeleaf: templates/student/my-courses.html
     */
    @GetMapping("/my-courses")
    public String myCourses(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Student currentStudent = studentService.getStudentByUserId(userDetails.getUser().getId());

        if (currentStudent != null) {
            model.addAttribute("enrollments",
                    enrollmentService.getEnrollmentsByStudentId(currentStudent.getId()));
        }

        return "student/my-courses";
    }

    /**
     * My Attendance - View attendance records
     * URL: http://localhost:8080/student/my-attendance
     * Thymeleaf: templates/student/my-attendance.html
     */
    @GetMapping("/my-attendance")
    public String myAttendance(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Student currentStudent = studentService.getStudentByUserId(userDetails.getUser().getId());

        if (currentStudent != null) {
            // Get all enrollments for this student
            var enrollments = enrollmentService.getEnrollmentsByStudentId(currentStudent.getId());
            model.addAttribute("enrollments", enrollments);

            // For each enrollment, you can calculate attendance percentage in Thymeleaf
            // Or pass it here if needed
        }

        return "student/my-attendance";
    }

    /**
     * My Payments - View payment history
     * URL: http://localhost:8080/student/my-payments
     * Thymeleaf: templates/student/my-payments.html
     */
    @GetMapping("/my-payments")
    public String myPayments(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Student currentStudent = studentService.getStudentByUserId(userDetails.getUser().getId());

        if (currentStudent != null) {
            model.addAttribute("payments",
                    paymentService.getPaymentsByStudentId(currentStudent.getId()));
            model.addAttribute("totalPaid",
                    paymentService.calculateTotalPaymentsByStudent(currentStudent.getId()));
            model.addAttribute("pendingPayments",
                    paymentService.getPendingPaymentsByStudent(currentStudent.getId()));
        }

        return "student/my-payments";
    }

    /**
     * Notifications - View all notifications
     * URL: http://localhost:8080/student/notifications
     * Thymeleaf: templates/student/notifications.html
     */
    @GetMapping("/notifications")
    public String notifications(Model model) {
        model.addAttribute("notifications",
                notificationService.getAllNotificationsOrderByDate());

        return "student/notifications";
    }
}