package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(name = "enrollment_date", nullable = false, updatable = false)
    // AUTO-FILLED in backend using @PrePersist
    private LocalDate enrollmentDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    // FRONTEND INPUT OPTIONAL - defaults to ACTIVE
    // Possible values: ACTIVE, COMPLETED, DROPPED, SUSPENDED
    private EnrollmentStatus status;

    // Relationship: Many Enrollments belong to One Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    // FRONTEND INPUT REQUIRED - send student_id
    private Student student;

    // Relationship: Many Enrollments belong to One Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    // FRONTEND INPUT REQUIRED - send course_id
    private Course course;

    // Relationship: One Enrollment can have many Attendance records
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    // AUTO-MANAGED by JPA (don't send from frontend)
    private List<Attendance> attendances;

    @PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDate.now();
        if (status == null) {
            status = EnrollmentStatus.ACTIVE;
        }
    }
}