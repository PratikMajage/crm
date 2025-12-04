package in.smit.crm.service;

import in.smit.crm.entity.Attendance;
import in.smit.crm.entity.AttendanceStatus;
import in.smit.crm.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for Attendance entity
 * Handles attendance marking and tracking logic
 */
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Get all attendance records (for list.html)
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // Get attendance by ID (for edit.html)
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }

    // Save or update attendance (for add.html and edit.html)
    public Attendance saveAttendance(Attendance attendance) {
        // Check if attendance already marked for this date
        boolean exists = attendanceRepository.existsByEnrollmentIdAndDate(
                attendance.getEnrollment().getId(),
                attendance.getDate());

        if (exists && attendance.getId() == null) {
            throw new RuntimeException("Attendance already marked for this date!");
        }

        return attendanceRepository.save(attendance);
    }

    // Delete attendance by ID
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    // Get attendance by enrollment ID (for student attendance report)
    public List<Attendance> getAttendanceByEnrollmentId(Long enrollmentId) {
        return attendanceRepository.findByEnrollmentId(enrollmentId);
    }

    // Get attendance by date (for daily attendance list)
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    // Get attendance between dates (for reports)
    public List<Attendance> getAttendanceBetweenDates(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    // Get attendance for enrollment between dates
    public List<Attendance> getAttendanceForEnrollmentBetweenDates(
            Long enrollmentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEnrollmentIdAndDateBetween(enrollmentId, startDate, endDate);
    }

    // Calculate attendance percentage for an enrollment
    public Double calculateAttendancePercentage(Long enrollmentId) {
        Double percentage = attendanceRepository.calculateAttendancePercentage(enrollmentId);
        return percentage != null ? percentage : 0.0;
    }

    // Count present days for enrollment
    public long countPresentDays(Long enrollmentId) {
        return attendanceRepository.countByEnrollmentAndStatus(enrollmentId, AttendanceStatus.PRESENT);
    }

    // Count absent days for enrollment
    public long countAbsentDays(Long enrollmentId) {
        return attendanceRepository.countByEnrollmentAndStatus(enrollmentId, AttendanceStatus.ABSENT);
    }
}