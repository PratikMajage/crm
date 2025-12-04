package in.smit.crm.controller;

import in.smit.crm.entity.Attendance;
import in.smit.crm.service.AttendanceService;
import in.smit.crm.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Attendance Controller
 * Handles attendance marking and tracking
 * Admin only - marks attendance
 */
@Controller
@RequestMapping("/admin/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Show all attendance records
     * URL: GET /admin/attendance
     * Thymeleaf: templates/admin/attendance.html (list.html)
     */
    @GetMapping
    public String listAttendance(Model model) {
        model.addAttribute("attendanceList", attendanceService.getAllAttendance());
        return "admin/attendance";
    }

    /**
     * Show mark attendance form
     * URL: GET /admin/attendance/mark
     * Thymeleaf: templates/admin/attendance-mark.html (add.html)
     */
    @GetMapping("/mark")
    public String showMarkForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        // Get all active enrollments for dropdown
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        return "admin/attendance-mark";
    }

    /**
     * Save attendance record
     * URL: POST /admin/attendance/save
     * Redirects to: /admin/attendance
     */
    @PostMapping("/save")
    public String saveAttendance(@ModelAttribute("attendance") Attendance attendance,
            RedirectAttributes redirectAttributes) {
        try {
            attendanceService.saveAttendance(attendance);
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/attendance";
    }

    /**
     * Show edit attendance form
     * URL: GET /admin/attendance/edit/{id}
     * Thymeleaf: templates/admin/attendance-edit.html (edit.html)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Attendance attendance = attendanceService.getAttendanceById(id);
            model.addAttribute("attendance", attendance);
            model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
            return "admin/attendance-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Attendance record not found!");
            return "redirect:/admin/attendance";
        }
    }

    /**
     * Update attendance
     * URL: POST /admin/attendance/update
     * Redirects to: /admin/attendance
     */
    @PostMapping("/update")
    public String updateAttendance(@ModelAttribute("attendance") Attendance attendance,
            RedirectAttributes redirectAttributes) {
        try {
            attendanceService.saveAttendance(attendance);
            redirectAttributes.addFlashAttribute("success", "Attendance updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/attendance";
    }

    /**
     * Delete attendance
     * URL: GET /admin/attendance/delete/{id}
     * Redirects to: /admin/attendance
     */
    @GetMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            attendanceService.deleteAttendance(id);
            redirectAttributes.addFlashAttribute("success", "Attendance deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/attendance";
    }

    /**
     * View attendance report for enrollment
     * URL: GET /admin/attendance/report/{enrollmentId}
     * Thymeleaf: templates/admin/attendance-report.html
     */
    @GetMapping("/report/{enrollmentId}")
    public String attendanceReport(@PathVariable("enrollmentId") Long enrollmentId, Model model) {
        try {
            model.addAttribute("enrollment", enrollmentService.getEnrollmentById(enrollmentId));
            model.addAttribute("attendanceList",
                    attendanceService.getAttendanceByEnrollmentId(enrollmentId));
            model.addAttribute("attendancePercentage",
                    attendanceService.calculateAttendancePercentage(enrollmentId));
            model.addAttribute("presentDays",
                    attendanceService.countPresentDays(enrollmentId));
            model.addAttribute("absentDays",
                    attendanceService.countAbsentDays(enrollmentId));
            return "admin/attendance-report";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading report: " + e.getMessage());
            return "redirect:/admin/attendance";
        }
    }
}