package in.smit.crm.entity;

/**
 * Enum for Payment Status
 * Represents the current status of a payment transaction
 */
public enum PaymentStatus {
    PENDING, // Payment initiated but not completed
    COMPLETED, // Payment successfully completed
    FAILED, // Payment failed
    REFUNDED // Payment was refunded to student
}