package lab3.client;

import lab3.domain.factory.LessonFactory;
import lab3.domain.models.Lesson;
import lab3.domain.decorators.*;
import lab3.domain.facade.BookingFacade;
import lab3.domain.payment.*;

public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("===============================================");
        System.out.println("  Lab 3: Structural Design Patterns Demo");
        System.out.println("===============================================\n");
        
        // demonstrate each pattern
        demonstrateDecorator();
        demonstrateFacade();
        demonstrateAdapter();
        
        System.out.println("===============================================");
        System.out.println("  All patterns tested successfully!");
        System.out.println("===============================================");
    }

    private static void demonstrateDecorator() {
        System.out.println("1. DECORATOR PATTERN");
        System.out.println("   Adding features to lessons without changing the classes\n");
        
        // basic lesson
        System.out.println("Basic lesson:");
        Lesson basicLesson = LessonFactory.createLesson("math");
        basicLesson.teach();
        
        // lesson with premium feature
        System.out.println("\nLesson with Premium feature:");
        Lesson premiumLesson = LessonFactory.createLesson("programming");
        premiumLesson = new PremiumLessonDecorator(premiumLesson);
        premiumLesson.teach();
        
        // lesson with recording
        System.out.println("\nLesson with Recording feature:");
        Lesson recordedLesson = LessonFactory.createLesson("english");
        recordedLesson = new RecordedLessonDecorator(recordedLesson);
        recordedLesson.teach();
        
        // multiple decorators combined - this is the powerful part
        System.out.println("\nLesson with ALL features (Premium + Recording + Materials):");
        Lesson completeLesson = LessonFactory.createLesson("math");
        completeLesson = new PremiumLessonDecorator(completeLesson);
        completeLesson = new RecordedLessonDecorator(completeLesson);
        completeLesson = new MaterialsLessonDecorator(completeLesson);
        completeLesson.teach();
        
        System.out.println();
    }

    private static void demonstrateFacade() {
        System.out.println("\n2. FACADE PATTERN");
        System.out.println("   Simplifying complex booking operations\n");
        
        // explain the problem
        System.out.println("Without facade, booking needs:");
        System.out.println("  - Create tutor (Builder)");
        System.out.println("  - Create lesson (Factory)");
        System.out.println("  - Get booking manager (Singleton)");
        System.out.println("  - Book the lesson");
        System.out.println("  - Teach the lesson");
        System.out.println("  That's 5 different steps!\n");
        
        // show the solution
        System.out.println("With facade - just one method call:\n");
        
        BookingFacade facade = new BookingFacade();
        
        // simple booking
        facade.quickBook("math", "Sarah", "Mathematics", 7);
        
        // premium booking
        facade.bookPremiumLesson("programming", "Alex", "Java Programming", 10);
        
        // complete package with all features
        facade.bookCompletePackage("english", "Emma", "English Literature", 12);
        
        System.out.println();
    }
    
 
    private static void demonstrateAdapter() {
        System.out.println("\n3. ADAPTER PATTERN");
        System.out.println("   Integrating external payment system\n");
        
        // describe the problem
        System.out.println("The problem:");
        System.out.println("  Our system expects: processPayment(amount)");
        System.out.println("  External API uses: makeTransaction(from, to, value, currency)");
        System.out.println("  These interfaces don't match!\n");
        
        System.out.println("The solution: Use an adapter\n");
        
        // create the external service (we can't change this)
        ExternalPaymentService externalService = new ExternalPaymentService();
        
        // create our adapter
        PaymentAdapter adapter = new PaymentAdapter(externalService);
        
        // now we can use our simple interface
        System.out.println("Using our simple interface:");
        adapter.processPayment(50.00);
        
        // verify the payment
        String transactionId = adapter.getLastTransactionId();
        adapter.validatePayment(transactionId);
        
        System.out.println("\nAdapter successfully translated between the interfaces!");
        System.out.println();
    }
}