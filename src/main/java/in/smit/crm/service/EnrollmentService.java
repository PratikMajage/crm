package in.smit.crm.service;

import in.smit.crm.entity.Enrollment;
import in.smit.crm.entity.EnrollmentStatus;
import in.smit.crm.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Enrollment entity
 * Handles student course enrollment logic
 */
@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Get all enrollments (for list.html)
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Get enrollment by ID (for edit.html and view details)
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
    }

    // Save or update enrollment (for add.html and edit.html)
    public Enrollment saveEnrollment(Enrollment enrollment) {
        // Check if student is already enrolled in this course
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId(),
                EnrollmentStatus.ACTIVE);

        if (exists && enrollment.getId() == null) {
            throw new RuntimeException("Student is already enrolled in this course!");
        }

        return enrollmentRepository.save(enrollment);
    }

    // Delete enrollment by ID
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    // Get enrollments by student ID (for student profile page)
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Get enrollments by course ID (for course details page)
    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    // Get active enrollments by student
    public List<Enrollment> getActiveEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentIdAndStatus(studentId, EnrollmentStatus.ACTIVE);
    }

    // Get enrollments by status (for filtering)
    public List<Enrollment> getEnrollmentsByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByStatus(status);
    }

    // Count enrollments for a course (for dashboard)
    public long countEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.countEnrollmentsByCourse(courseId);
    }

    // Count active enrollments (for dashboard)
    public long countActiveEnrollments() {
        return enrollmentRepository.countByStatus(EnrollmentStatus.ACTIVE);
    }
}