package lab3.domain.payment;

public class PaymentAdapter implements PaymentProcessor{
    private ExternalPaymentService externalService;

    private String lastTransactionId;

    private static final String SYSTEM_ACCOUNT = "TUTOR_SYSTEM_ACCOUNT";
    private static final String CURRENCY = "USD";

    public PaymentAdapter(ExternalPaymentService externalService) {
        this.externalService = externalService;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("\n💳 Processing payment through adapter...");
        lastTransactionId = externalService.makeTransaction(
            "STUDENT_ACCOUNT", 
            SYSTEM_ACCOUNT,      // to - our system account
            amount,              // value - from our parameter
            CURRENCY             // currency - we provide this
        );
        
        System.out.println("💳 Adapter: Payment processed. Transaction ID: " + lastTransactionId);
    }

    @Override
    public boolean validatePayment(String transactionId) {
        System.out.println("\n🔍 Validating payment through adapter...");
        
        boolean status = externalService.checkTransactionStatus(transactionId);
        System.out.println("🔍 Adapter: Validation " + (status ? "successful" : "failed"));

        return status;
    }

    public String getLastTransactionId() {
        return lastTransactionId;
    }
}
