package in.smit.crm.service;

import in.smit.crm.entity.Payment;
import in.smit.crm.entity.PaymentStatus;
import in.smit.crm.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for Payment entity
 * Handles payment processing and tracking logic
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Get all payments (for list.html)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID (for edit.html and view details)
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    // Save or update payment (for add.html and edit.html)
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Delete payment by ID
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    // Get payments by student ID (for student payment history)
    public List<Payment> getPaymentsByStudentId(Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    // Get payments by status (for filtering)
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    // Get pending payments for a student
    public List<Payment> getPendingPaymentsByStudent(Long studentId) {
        return paymentRepository.findPendingPaymentsByStudent(studentId);
    }

    // Get payments by date range (for reports)
    public List<Payment> getPaymentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    // Get recent payments (last 30 days)
    public List<Payment> getRecentPayments() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return paymentRepository.findRecentPayments(thirtyDaysAgo);
    }

    // Calculate total payments by student
    public BigDecimal calculateTotalPaymentsByStudent(Long studentId) {
        BigDecimal total = paymentRepository.calculateTotalPaymentsByStudent(studentId);
        return total != null ? total : BigDecimal.ZERO;
    }

    // Calculate total revenue (completed payments)
    public BigDecimal calculateTotalRevenue() {
        BigDecimal revenue = paymentRepository.calculateTotalRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    // Calculate pending amount
    public BigDecimal calculatePendingAmount() {
        BigDecimal pending = paymentRepository.calculateTotalPaymentsByStatus(PaymentStatus.PENDING);
        return pending != null ? pending : BigDecimal.ZERO;
    }

    // Count payments by status
    public long countPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.countByStatus(status);
    }
}