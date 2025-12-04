package in.smit.crm.repository;

import in.smit.crm.entity.Student;
import in.smit.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity
 * Handles student-specific queries
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find student by email
    Optional<Student> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Find student by user
    Optional<Student> findByUser(User user);

    // Find student by user ID
    Optional<Student> findByUserId(Long userId);

    // Find students by phone number
    Optional<Student> findByPhone(String phone);

    // Search students by first name or last name (case-insensitive)
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    // Find students enrolled between two dates
    List<Student> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);

    // Count total students
    @Query("SELECT COUNT(s) FROM Student s")
    long countTotalStudents();
}