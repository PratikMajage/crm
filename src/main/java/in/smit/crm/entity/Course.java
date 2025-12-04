package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(name = "course_name", nullable = false)
    // FRONTEND INPUT REQUIRED
    private String courseName;

    @Column(columnDefinition = "TEXT")
    // FRONTEND INPUT OPTIONAL
    private String description;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED - in months or days
    private Integer duration;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED
    private BigDecimal fees;

    @Column(name = "start_date", nullable = false)
    // FRONTEND INPUT REQUIRED
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    // FRONTEND INPUT REQUIRED
    private LocalDate endDate;

    // Relationship: One Course can have many Enrollments
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    // AUTO-MANAGED by JPA (don't send from frontend)
    private List<Enrollment> enrollments;
}