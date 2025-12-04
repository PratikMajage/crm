package in.smit.crm.service;

import in.smit.crm.entity.Student;
import in.smit.crm.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Student entity
 * Handles student management business logic
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students (for list.html)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID (for edit.html and view details)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // Save or update student (for add.html and edit.html)
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Delete student by ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Find student by email
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElse(null);
    }

    // Check if email exists (for validation in add form)
    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    // Search students by name (for search functionality)
    public List<Student> searchStudents(String keyword) {
        return studentRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword);
    }

    // Get student by user ID
    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElse(null);
    }

    // Count total students (for dashboard)
    public long getTotalStudents() {
        return studentRepository.count();
    }
}