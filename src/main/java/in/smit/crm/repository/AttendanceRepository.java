package in.smit.crm.repository;

import in.smit.crm.entity.Attendance;
import in.smit.crm.entity.Enrollment;
import in.smit.crm.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Attendance entity
 * Handles attendance tracking queries
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find all attendance records by enrollment
    List<Attendance> findByEnrollment(Enrollment enrollment);

    // Find all attendance records by enrollment ID
    List<Attendance> findByEnrollmentId(Long enrollmentId);

    // Find attendance by enrollment and date
    Optional<Attendance> findByEnrollmentIdAndDate(Long enrollmentId, LocalDate date);

    // Find attendance by status
    List<Attendance> findByStatus(AttendanceStatus status);

    // Find attendance by enrollment and status
    List<Attendance> findByEnrollmentIdAndStatus(Long enrollmentId, AttendanceStatus status);

    // Find attendance records by date
    List<Attendance> findByDate(LocalDate date);

    // Find attendance records by date range
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Find attendance by enrollment and date range
    List<Attendance> findByEnrollmentIdAndDateBetween(
            Long enrollmentId, LocalDate startDate, LocalDate endDate);

    // Check if attendance already marked for a specific enrollment and date
    boolean existsByEnrollmentIdAndDate(Long enrollmentId, LocalDate date);

    // Count attendance by status for a specific enrollment
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.enrollment.id = :enrollmentId AND a.status = :status")
    long countByEnrollmentAndStatus(
            @Param("enrollmentId") Long enrollmentId,
            @Param("status") AttendanceStatus status);

    // Calculate attendance percentage for an enrollment
    @Query("SELECT (COUNT(a) * 100.0 / (SELECT COUNT(a2) FROM Attendance a2 WHERE a2.enrollment.id = :enrollmentId)) " +
            "FROM Attendance a WHERE a.enrollment.id = :enrollmentId AND a.status = 'PRESENT'")
    Double calculateAttendancePercentage(@Param("enrollmentId") Long enrollmentId);
}