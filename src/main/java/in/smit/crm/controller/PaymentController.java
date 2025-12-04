package in.smit.crm.controller;

import in.smit.crm.entity.Payment;
import in.smit.crm.service.PaymentService;
import in.smit.crm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Payment Controller
 * Handles payment processing and tracking
 * Admin only - manages payments
 */
@Controller
@RequestMapping("/admin/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StudentService studentService;

    /**
     * Show all payments
     * URL: GET /admin/payments
     * Thymeleaf: templates/admin/payments.html (list.html)
     */
    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("totalRevenue", paymentService.calculateTotalRevenue());
        model.addAttribute("pendingAmount", paymentService.calculatePendingAmount());
        return "admin/payments";
    }

    /**
     * Show add payment form
     * URL: GET /admin/payments/add
     * Thymeleaf: templates/admin/payment-add.html (add.html)
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("payment", new Payment());
        // Populate student dropdown
        model.addAttribute("students", studentService.getAllStudents());
        return "admin/payment-add";
    }

    /**
     * Save new payment
     * URL: POST /admin/payments/save
     * Redirects to: /admin/payments
     */
    @PostMapping("/save")
    public String savePayment(@ModelAttribute("payment") Payment payment,
            RedirectAttributes redirectAttributes) {
        try {
            paymentService.savePayment(payment);
            redirectAttributes.addFlashAttribute("success", "Payment recorded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/payments";
    }

    /**
     * Show edit payment form
     * URL: GET /admin/payments/edit/{id}
     * Thymeleaf: templates/admin/payment-edit.html (edit.html)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            model.addAttribute("payment", payment);
            model.addAttribute("students", studentService.getAllStudents());
            return "admin/payment-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Payment not found!");
            return "redirect:/admin/payments";
        }
    }

    /**
     * Update payment
     * URL: POST /admin/payments/update
     * Redirects to: /admin/payments
     */
    @PostMapping("/update")
    public String updatePayment(@ModelAttribute("payment") Payment payment,
            RedirectAttributes redirectAttributes) {
        try {
            paymentService.savePayment(payment);
            redirectAttributes.addFlashAttribute("success", "Payment updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/payments";
    }

    /**
     * Delete payment
     * URL: GET /admin/payments/delete/{id}
     * Redirects to: /admin/payments
     */
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            paymentService.deletePayment(id);
            redirectAttributes.addFlashAttribute("success", "Payment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/payments";
    }

    /**
     * View payment details
     * URL: GET /admin/payments/view/{id}
     * Thymeleaf: templates/admin/payment-view.html
     */
    @GetMapping("/view/{id}")
    public String viewPayment(@PathVariable("id") Long id, Model model) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            model.addAttribute("payment", payment);
            return "admin/payment-view";
        } catch (Exception e) {
            model.addAttribute("error", "Payment not found!");
            return "redirect:/admin/payments";
        }
    }

    /**
     * View payment history for a student
     * URL: GET /admin/payments/student/{studentId}
     * Thymeleaf: templates/admin/payment-history.html
     */
    @GetMapping("/student/{studentId}")
    public String studentPaymentHistory(@PathVariable("studentId") Long studentId, Model model) {
        try {
            model.addAttribute("student", studentService.getStudentById(studentId));
            model.addAttribute("payments", paymentService.getPaymentsByStudentId(studentId));
            model.addAttribute("totalPaid",
                    paymentService.calculateTotalPaymentsByStudent(studentId));
            model.addAttribute("pendingPayments",
                    paymentService.getPendingPaymentsByStudent(studentId));
            return "admin/payment-history";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading payment history: " + e.getMessage());
            return "redirect:/admin/payments";
        }
    }
}