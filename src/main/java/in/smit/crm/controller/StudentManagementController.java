package in.smit.crm.controller;

import in.smit.crm.entity.Student;
import in.smit.crm.entity.User;
import in.smit.crm.service.StudentService;
import in.smit.crm.service.UserService;
import in.smit.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Student Management Controller (Admin side)
 * Admin manages student records
 * Separate from StudentController (which is for student's own view)
 */
@Controller
@RequestMapping("/admin/students")
public class StudentManagementController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Show all students
     * URL: GET /admin/students
     * Thymeleaf: templates/admin/students.html (list.html)
     */
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "admin/students";
    }

    /**
     * Show add student form
     * URL: GET /admin/students/add
     * Thymeleaf: templates/admin/student-add.html (add.html)
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("user", new User());
        // Get student role for dropdown
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/student-add";
    }

    /**
     * Save new student (with user account)
     * URL: POST /admin/students/save
     * Redirects to: /admin/students
     */
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("roleId") Long roleId,
            RedirectAttributes redirectAttributes) {
        try {
            // First create user account
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(roleService.getRoleById(roleId));
            User savedUser = userService.saveUser(user);

            // Then create student profile linked to user
            student.setUser(savedUser);
            studentService.saveStudent(student);

            redirectAttributes.addFlashAttribute("success", "Student added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }

    /**
     * Show edit student form
     * URL: GET /admin/students/edit/{id}
     * Thymeleaf: templates/admin/student-edit.html (edit.html)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Student student = studentService.getStudentById(id);
            model.addAttribute("student", student);
            return "admin/student-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Student not found!");
            return "redirect:/admin/students";
        }
    }

    /**
     * Update student
     * URL: POST /admin/students/update
     * Redirects to: /admin/students
     */
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") Student student,
            RedirectAttributes redirectAttributes) {
        try {
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }

    /**
     * Delete student
     * URL: GET /admin/students/delete/{id}
     * Redirects to: /admin/students
     */
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/students";
    }

    /**
     * View student details
     * URL: GET /admin/students/view/{id}
     * Thymeleaf: templates/admin/student-view.html
     */
    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable("id") Long id, Model model) {
        try {
            Student student = studentService.getStudentById(id);
            model.addAttribute("student", student);
            return "admin/student-view";
        } catch (Exception e) {
            model.addAttribute("error", "Student not found!");
            return "redirect:/admin/students";
        }
    }

    /**
     * Search students
     * URL: GET /admin/students/search?keyword=john
     * Thymeleaf: templates/admin/students.html
     */
    @GetMapping("/search")
    public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("students", studentService.searchStudents(keyword));
        model.addAttribute("keyword", keyword);
        return "admin/students";
    }
}