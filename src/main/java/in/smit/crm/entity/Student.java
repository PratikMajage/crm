package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(name = "first_name", nullable = false)
    // FRONTEND INPUT REQUIRED
    private String firstName;

    @Column(name = "last_name", nullable = false)
    // FRONTEND INPUT REQUIRED
    private String lastName;

    @Column(nullable = false, unique = true)
    // FRONTEND INPUT REQUIRED
    private String email;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED
    private String phone;

    @Column(columnDefinition = "TEXT")
    // FRONTEND INPUT OPTIONAL
    private String address;

    @Column(name = "dob", nullable = false)
    // FRONTEND INPUT REQUIRED - Date of Birth
    private LocalDate dob;

    @Column(name = "enrollment_date", nullable = false, updatable = false)
    // AUTO-FILLED in backend using @PrePersist
    private LocalDate enrollmentDate;

    // Relationship: One Student belongs to One User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    // FRONTEND INPUT REQUIRED - send user_id
    private User user;

    // Relationship: One Student can have many Enrollments
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    // AUTO-MANAGED by JPA (don't send from frontend)
    private List<Enrollment> enrollments;

    // Relationship: One Student can have many Payments
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    // AUTO-MANAGED by JPA (don't send from frontend)
    private List<Payment> payments;

    @PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDate.now();
    }
}