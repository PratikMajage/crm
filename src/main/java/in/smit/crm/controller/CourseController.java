package in.smit.crm.controller;

import in.smit.crm.entity.Course;
import in.smit.crm.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Course Controller
 * Handles CRUD operations for courses
 * Admin only - manages courses
 */
@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Show all courses (already in AdminController, but can also be here)
     * URL: GET /admin/courses
     * Thymeleaf: templates/admin/courses.html (list.html)
     */
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/courses";
    }

    /**
     * Show add course form
     * URL: GET /admin/courses/add
     * Thymeleaf: templates/admin/course-add.html (add.html)
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/course-add";
    }

    /**
     * Save new course (handles form submission)
     * URL: POST /admin/courses/save
     * Redirects to: /admin/courses
     */
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course,
            RedirectAttributes redirectAttributes) {
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("success", "Course saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    /**
     * Show edit course form
     * URL: GET /admin/courses/edit/{id}
     * Thymeleaf: templates/admin/course-edit.html (edit.html)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);
            return "admin/course-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Course not found!");
            return "redirect:/admin/courses";
        }
    }

    /**
     * Update course (handles edit form submission)
     * URL: POST /admin/courses/update
     * Redirects to: /admin/courses
     */
    @PostMapping("/update")
    public String updateCourse(@ModelAttribute("course") Course course,
            RedirectAttributes redirectAttributes) {
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    /**
     * Delete course
     * URL: GET /admin/courses/delete/{id}
     * Redirects to: /admin/courses
     */
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    /**
     * View course details
     * URL: GET /admin/courses/view/{id}
     * Thymeleaf: templates/admin/course-view.html
     */
    @GetMapping("/view/{id}")
    public String viewCourse(@PathVariable("id") Long id, Model model) {
        try {
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);
            return "admin/course-view";
        } catch (Exception e) {
            model.addAttribute("error", "Course not found!");
            return "redirect:/admin/courses";
        }
    }
}