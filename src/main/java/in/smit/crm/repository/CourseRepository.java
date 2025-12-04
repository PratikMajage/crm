package in.smit.crm.repository;

import in.smit.crm.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Course entity
 * Handles course-related queries
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Find course by name
    Optional<Course> findByCourseName(String courseName);

    // Search courses by name (partial match, case-insensitive)
    List<Course> findByCourseNameContainingIgnoreCase(String courseName);

    // Find courses within a fee range
    List<Course> findByFeesBetween(BigDecimal minFees, BigDecimal maxFees);

    // Find courses by duration
    List<Course> findByDuration(Integer duration);

    // Find active courses (courses that are currently running)
    @Query("SELECT c FROM Course c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate")
    List<Course> findActiveCourses(LocalDate currentDate);

    // Find upcoming courses (courses that haven't started yet)
    List<Course> findByStartDateAfter(LocalDate currentDate);

    // Find courses by start date range
    List<Course> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // Count total courses
    @Query("SELECT COUNT(c) FROM Course c")
    long countTotalCourses();
}