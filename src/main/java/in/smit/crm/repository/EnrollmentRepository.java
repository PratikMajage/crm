package in.smit.crm.repository;

import in.smit.crm.entity.Enrollment;
import in.smit.crm.entity.Student;
import in.smit.crm.entity.Course;
import in.smit.crm.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Enrollment entity
 * Handles enrollment-related queries
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Find all enrollments by student
    List<Enrollment> findByStudent(Student student);

    // Find all enrollments by student ID
    List<Enrollment> findByStudentId(Long studentId);

    // Find all enrollments by course
    List<Enrollment> findByCourse(Course course);

    // Find all enrollments by course ID
    List<Enrollment> findByCourseId(Long courseId);

    // Find enrollments by status
    List<Enrollment> findByStatus(EnrollmentStatus status);

    // Find enrollments by student and status
    List<Enrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);

    // Find enrollments by course and status
    List<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    // Check if student is already enrolled in a course
    boolean existsByStudentIdAndCourseIdAndStatus(
            Long studentId, Long courseId, EnrollmentStatus status);

    // Find specific enrollment by student and course
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Find enrollments by date range
    List<Enrollment> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);

    // Count enrollments by course
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId")
    long countEnrollmentsByCourse(@Param("courseId") Long courseId);

    // Count active enrollments
    long countByStatus(EnrollmentStatus status);
}