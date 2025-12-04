package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false, updatable = false)
    // AUTO-FILLED in backend using @PrePersist
    private LocalDate paymentDate;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    // FRONTEND INPUT REQUIRED
    // Possible values: CASH, CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, CHEQUE
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    // FRONTEND INPUT OPTIONAL - defaults to PENDING
    // Possible values: PENDING, COMPLETED, FAILED, REFUNDED
    private PaymentStatus status;

    // Relationship: Many Payments belong to One Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    // FRONTEND INPUT REQUIRED - send student_id
    private Student student;

    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDate.now();
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }
}