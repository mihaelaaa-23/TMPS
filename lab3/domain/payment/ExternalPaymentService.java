package lab3.domain.payment;

public class ExternalPaymentService {

    public String makeTransaction(String fromAccount, String toAccount, double amount, String currency) {
        System.out.println("🌐 External Payment Service:");
        System.out.println("   From: " + fromAccount);
        System.out.println("   To: " + toAccount);
        System.out.println("   Amount: " + amount + " " + currency);
        System.out.println("   Status: ✅ Transaction successful");
        return "TXN" + System.currentTimeMillis();
    }

    public boolean checkTransactionStatus(String transactionId) {
        System.out.println("🔍 Checking transaction " + transactionId + ": COMPLETED");
        return true;
    }
}
