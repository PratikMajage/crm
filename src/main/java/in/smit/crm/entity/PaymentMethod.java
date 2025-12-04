package in.smit.crm.entity;

/**
 * Enum for Payment Method
 * Represents the method used for payment
 */
public enum PaymentMethod {
    CASH, // Cash payment
    CREDIT_CARD, // Credit card payment
    DEBIT_CARD, // Debit card payment
    UPI, // UPI payment (GPay, PhonePe, etc.)
    NET_BANKING, // Net banking/Online transfer
    CHEQUE // Cheque payment
}