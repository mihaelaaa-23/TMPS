package lab4.client;

import lab4.domain.factory.LessonFactory;
import lab4.domain.models.Lesson;
import lab4.domain.models.Tutor;
import lab4.domain.observer.*;
import lab4.domain.strategy.*;
import lab4.domain.command.*;
import lab4.domain.booking.BookingManager;

public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("===============================================");
        System.out.println("  Lab 4: Behavioral Design Patterns Demo");
        System.out.println("===============================================\n");
        
        demonstrateObserver();
        demonstrateStrategy();
        demonstrateCommand();
    }
    
    private static void demonstrateObserver() {
        System.out.println("1. OBSERVER PATTERN - Event-driven notifications\n");
        
        BookingManager bookingManager = BookingManager.getInstance();
        
        // Create and attach observers
        StudentObserver student = new StudentObserver("Alice");
        TutorObserver tutor = new TutorObserver("Dr. Smith");
        AdminObserver admin = new AdminObserver();
        
        bookingManager.attach(student);
        bookingManager.attach(tutor);
        bookingManager.attach(admin);
        System.out.println("[✓] 3 observers attached\n");
        
        // Create lesson
        Lesson lesson = LessonFactory.createLesson("math");
        Tutor tutorModel = new Tutor.Builder()
                .setName("Dr. Smith")
                .setSubject("Mathematics")
                .setExperience(15)
                .build();
        
        // Demonstrate observer pattern
        System.out.println("--- Events trigger notifications to all observers ---");
        bookingManager.bookLesson(tutorModel, lesson);
        bookingManager.startLesson(tutorModel, lesson);
        bookingManager.completeLesson(tutorModel, lesson);
        
        System.out.println("\n--- Student unsubscribes ---");
        bookingManager.detach(student);
        System.out.println("[✓] Alice unsubscribed\n");
        
        System.out.println("--- Cancellation (only 2 observers notified) ---");
        bookingManager.cancelBooking(tutorModel, lesson);
    }
    
    private static void demonstrateStrategy() {
        System.out.println("\n\n2. STRATEGY PATTERN - Flexible pricing algorithms\n");

        double lessonPrice = 50.0;
        
        System.out.println("--- Different pricing strategies ---");
        
        // Standard
        PricingContext context = new PricingContext(new StandardPricingStrategy());
        context.calculateTotalPrice(lessonPrice, 3);
        
        // Bulk discount
        context.setStrategy(new BulkDiscountStrategy());
        context.calculateTotalPrice(lessonPrice, 10);
        
        // Seasonal
        context.setStrategy(new SeasonalDiscountStrategy("Summer", 0.15));
        context.calculateTotalPrice(lessonPrice, 5);
        
        // Referral
        context.setStrategy(new ReferralPricingStrategy(3));
        context.calculateTotalPrice(lessonPrice, 4);
    }
    
    private static void demonstrateCommand() {
        System.out.println("\n\n3. COMMAND PATTERN - Operations with undo/redo\n");
        
        CommandHistory history = new CommandHistory();
        BookingManager bookingManager = BookingManager.getInstance();
        
        // Setup
        Tutor tutor = new Tutor.Builder()
                .setName("Prof. Johnson")
                .setSubject("Programming")
                .setExperience(10)
                .build();
        
        Lesson lesson1 = LessonFactory.createLesson("programming");
        Lesson lesson2 = LessonFactory.createLesson("math");
        
        StudentObserver student = new StudentObserver("Bob");
        bookingManager.attach(student);
        
        // Execute commands
        System.out.println("--- Executing commands ---");
        history.executeCommand(new ScheduleLessonCommand(bookingManager, tutor, lesson1, "Monday 10AM"));
        history.executeCommand(new ScheduleLessonCommand(bookingManager, tutor, lesson2, "Wednesday 2PM"));
        history.executeCommand(new RescheduleLessonCommand(bookingManager, tutor, lesson1, "Monday 10AM", "Tuesday 3PM"));
        
        history.showHistory();
        
        // Undo/Redo
        System.out.println("\n--- Testing undo/redo ---");
        history.undo();
        history.undo();
        history.showHistory();
        
        history.redo();
        history.showHistory();
        
        // Cancel and undo cancellation
        history.executeCommand(new CancelLessonCommand(bookingManager, tutor, lesson1, "Tuesday 3PM", "Emergency"));
        history.showHistory();
        
        System.out.println("\n--- Undo cancellation ---");
        history.undo();
        history.showHistory();
        
        bookingManager.detach(student);
        System.out.println();
    }   
}