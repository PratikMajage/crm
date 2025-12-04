package in.smit.crm.service;

import in.smit.crm.entity.Course;
import in.smit.crm.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for Course entity
 * Handles course management business logic
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Get all courses (for list.html and dropdown in enrollment form)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID (for edit.html and view details)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    // Save or update course (for add.html and edit.html)
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // Delete course by ID
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // Search courses by name (for search functionality)
    public List<Course> searchCourses(String keyword) {
        return courseRepository.findByCourseNameContainingIgnoreCase(keyword);
    }

    // Get active courses (courses currently running)
    public List<Course> getActiveCourses() {
        return courseRepository.findActiveCourses(LocalDate.now());
    }

    // Get upcoming courses (courses that haven't started yet)
    public List<Course> getUpcomingCourses() {
        return courseRepository.findByStartDateAfter(LocalDate.now());
    }

    // Count total courses (for dashboard)
    public long getTotalCourses() {
        return courseRepository.count();
    }
}