package in.smit.crm.entity;

/**
 * Enum for Attendance Status
 * Represents student's attendance for a particular day
 */
public enum AttendanceStatus {
    PRESENT, // Student was present
    ABSENT, // Student was absent
    LATE, // Student arrived late
    EXCUSED // Absence was excused (medical, emergency, etc.)
}