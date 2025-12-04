package in.smit.crm.repository;

import in.smit.crm.entity.Payment;
import in.smit.crm.entity.Student;
import in.smit.crm.entity.PaymentMethod;
import in.smit.crm.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Payment entity
 * Handles payment-related queries
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments by student
    List<Payment> findByStudent(Student student);

    // Find all payments by student ID
    List<Payment> findByStudentId(Long studentId);

    // Find payments by status
    List<Payment> findByStatus(PaymentStatus status);

    // Find payments by student and status
    List<Payment> findByStudentIdAndStatus(Long studentId, PaymentStatus status);

    // Find payments by payment method
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Find payments by date
    List<Payment> findByPaymentDate(LocalDate paymentDate);

    // Find payments by date range
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // Find payments by amount range
    List<Payment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Calculate total payments by student
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.student.id = :studentId AND p.status = 'COMPLETED'")
    BigDecimal calculateTotalPaymentsByStudent(@Param("studentId") Long studentId);

    // Calculate total payments by status
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status")
    BigDecimal calculateTotalPaymentsByStatus(@Param("status") PaymentStatus status);

    // Calculate total revenue (all completed payments)
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'")
    BigDecimal calculateTotalRevenue();

    // Find recent payments (last N days)
    @Query("SELECT p FROM Payment p WHERE p.paymentDate >= :startDate ORDER BY p.paymentDate DESC")
    List<Payment> findRecentPayments(@Param("startDate") LocalDate startDate);

    // Count payments by status
    long countByStatus(PaymentStatus status);

    // Find pending payments by student
    @Query("SELECT p FROM Payment p WHERE p.student.id = :studentId AND p.status = 'PENDING' ORDER BY p.paymentDate DESC")
    List<Payment> findPendingPaymentsByStudent(@Param("studentId") Long studentId);
}