package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED - date of attendance
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    // FRONTEND INPUT REQUIRED
    // Possible values: PRESENT, ABSENT, LATE, EXCUSED
    private AttendanceStatus status;

    // Relationship: Many Attendance records belong to One Enrollment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    // FRONTEND INPUT REQUIRED - send enrollment_id
    private Enrollment enrollment;
}