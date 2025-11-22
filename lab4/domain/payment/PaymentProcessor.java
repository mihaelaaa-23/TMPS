package lab4.domain.payment;

public interface PaymentProcessor {
    
    void processPayment(double amount);

    boolean validatePayment(String transactionId);
}
