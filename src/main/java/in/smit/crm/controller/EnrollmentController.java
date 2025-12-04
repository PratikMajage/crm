package in.smit.crm.controller;

import in.smit.crm.entity.Enrollment;
import in.smit.crm.service.EnrollmentService;
import in.smit.crm.service.StudentService;
import in.smit.crm.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Enrollment Controller
 * Handles student enrollment in courses
 * Admin only - manages enrollments
 */
@Controller
@RequestMapping("/admin/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    /**
     * Show all enrollments
     * URL: GET /admin/enrollments
     * Thymeleaf: templates/admin/enrollments.html (list.html)
     */
    @GetMapping
    public String listEnrollments(Model model) {
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        return "admin/enrollments";
    }

    /**
     * Show add enrollment form
     * URL: GET /admin/enrollments/add
     * Thymeleaf: templates/admin/enrollment-add.html (add.html)
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        // Populate dropdowns
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/enrollment-add";
    }

    /**
     * Save new enrollment
     * URL: POST /admin/enrollments/save
     * Redirects to: /admin/enrollments
     */
    @PostMapping("/save")
    public String saveEnrollment(@ModelAttribute("enrollment") Enrollment enrollment,
            RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.saveEnrollment(enrollment);
            redirectAttributes.addFlashAttribute("success", "Enrollment created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/enrollments";
    }

    /**
     * Show edit enrollment form
     * URL: GET /admin/enrollments/edit/{id}
     * Thymeleaf: templates/admin/enrollment-edit.html (edit.html)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentById(id);
            model.addAttribute("enrollment", enrollment);
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("courses", courseService.getAllCourses());
            return "admin/enrollment-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Enrollment not found!");
            return "redirect:/admin/enrollments";
        }
    }

    /**
     * Update enrollment
     * URL: POST /admin/enrollments/update
     * Redirects to: /admin/enrollments
     */
    @PostMapping("/update")
    public String updateEnrollment(@ModelAttribute("enrollment") Enrollment enrollment,
            RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.saveEnrollment(enrollment);
            redirectAttributes.addFlashAttribute("success", "Enrollment updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/enrollments";
    }

    /**
     * Delete enrollment
     * URL: GET /admin/enrollments/delete/{id}
     * Redirects to: /admin/enrollments
     */
    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.deleteEnrollment(id);
            redirectAttributes.addFlashAttribute("success", "Enrollment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/enrollments";
    }

    /**
     * View enrollment details
     * URL: GET /admin/enrollments/view/{id}
     * Thymeleaf: templates/admin/enrollment-view.html
     */
    @GetMapping("/view/{id}")
    public String viewEnrollment(@PathVariable("id") Long id, Model model) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentById(id);
            model.addAttribute("enrollment", enrollment);
            return "admin/enrollment-view";
        } catch (Exception e) {
            model.addAttribute("error", "Enrollment not found!");
            return "redirect:/admin/enrollments";
        }
    }
}