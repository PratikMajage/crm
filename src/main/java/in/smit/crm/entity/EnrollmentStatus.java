package in.smit.crm.entity;

/**
 * Enum for Enrollment Status
 * Represents the current state of a student's enrollment
 */
public enum EnrollmentStatus {
    ACTIVE, // Currently enrolled and attending
    COMPLETED, // Successfully completed the course
    DROPPED, // Student dropped out
    SUSPENDED // Enrollment temporarily suspended
}