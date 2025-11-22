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
        System.out.println("1. OBSERVER PATTERN");
        System.out.println("   Event-driven notifications for bookings\n");
        // Get the booking manager (it's also a Subject now)
        BookingManager bookingManager = BookingManager.getInstance();
        
        // Create observers
        StudentObserver student = new StudentObserver("Alice");
        TutorObserver tutor = new TutorObserver("Dr. Smith");
        AdminObserver admin = new AdminObserver();
        
        // Attach observers to the subject
        bookingManager.attach(student);
        bookingManager.attach(tutor);
        bookingManager.attach(admin);
        
        // Create a lesson and tutor
        Lesson lesson = LessonFactory.createLesson("math");
        Tutor tutorModel = new Tutor.Builder()
                .setName("Dr. Smith")
                .setSubject("Advanced Mathematics")
                .setExperience(15)
                .build();
        
        // Book the lesson - all observers get notified automatically!
        System.out.println("\n--- Booking a lesson ---");
        bookingManager.bookLesson(tutorModel, lesson);
        
        // Start the lesson
        System.out.println("\n--- Starting the lesson ---");
        bookingManager.startLesson(tutorModel, lesson);
        
        // Complete the lesson
        System.out.println("\n--- Completing the lesson ---");
        bookingManager.completeLesson(tutorModel, lesson);
        
        // Detach student observer
        System.out.println("\n--- Student unsubscribes ---");
        bookingManager.detach(student);
        
        // Cancel a booking - student won't be notified
        System.out.println("\n--- Cancelling another booking ---");
        bookingManager.cancelBooking(tutorModel, lesson);
    }
    
    private static void demonstrateStrategy() {
        System.out.println("\n2. STRATEGY PATTERN");
        System.out.println("   Flexible pricing algorithms\n");

        double lessonPrice = 50.0; // Base price per lesson
        
        // Strategy 1: Standard Pricing
        System.out.println("--- Scenario 1: New Student (Standard Pricing) ---");
        PricingContext context = new PricingContext(new StandardPricingStrategy());
        context.calculateTotalPrice(lessonPrice, 3);
        
        // Strategy 2: Bulk Discount
        System.out.println("\n--- Scenario 2: Student buying 10 lessons (Bulk Discount) ---");
        context.setStrategy(new BulkDiscountStrategy());
        context.calculateTotalPrice(lessonPrice, 10);
        
        // Strategy 3: Seasonal Discount
        System.out.println("\n--- Scenario 3: Summer Promotion (Seasonal Discount) ---");
        context.setStrategy(new SeasonalDiscountStrategy("Summer", 0.15));
        context.calculateTotalPrice(lessonPrice, 5);
        
        // Strategy 4: Referral Pricing
        System.out.println("\n--- Scenario 4: Student with 3 referrals (Referral Pricing) ---");
        context.setStrategy(new ReferralPricingStrategy(3));
        context.calculateTotalPrice(lessonPrice, 4);
    }
    
    private static void demonstrateCommand() {
        System.out.println("\n3. COMMAND PATTERN");
        System.out.println("   Encapsulating operations with undo/redo\n");
        // Create command history
        CommandHistory history = new CommandHistory();
        BookingManager bookingManager = BookingManager.getInstance();
        
        // Create tutor and lessons
        Tutor tutor = new Tutor.Builder()
                .setName("Prof. Johnson")
                .setSubject("Programming")
                .setExperience(10)
                .build();
        
        Lesson lesson1 = LessonFactory.createLesson("programming");
        Lesson lesson2 = LessonFactory.createLesson("math");
        
        // Attach an observer to see notifications
        StudentObserver student = new StudentObserver("Bob");
        bookingManager.attach(student);
        
        // Execute commands
        System.out.println("--- Executing Commands ---");
        
        Command scheduleCmd1 = new ScheduleLessonCommand(bookingManager, tutor, lesson1, "Monday 10:00 AM");
        history.executeCommand(scheduleCmd1);
        
        Command scheduleCmd2 = new ScheduleLessonCommand(bookingManager, tutor, lesson2, "Wednesday 2:00 PM");
        history.executeCommand(scheduleCmd2);
        
        Command rescheduleCmd = new RescheduleLessonCommand(bookingManager, tutor, lesson1, 
                                                            "Monday 10:00 AM", "Tuesday 3:00 PM");
        history.executeCommand(rescheduleCmd);
        
        // Show history
        history.showHistory();
        
        // Undo operations
        System.out.println("\n--- Testing Undo ---");
        history.undo();  // Undo reschedule
        
        history.showHistory();
        
        history.undo();  // Undo second schedule
        
        // Redo operations
        System.out.println("\n--- Testing Redo ---");
        history.redo();  // Redo second schedule
        
        history.showHistory();
        
        // Cancel a lesson
        Command cancelCmd = new CancelLessonCommand(bookingManager, tutor, lesson1, 
                                                    "Tuesday 3:00 PM", "Student emergency");
        history.executeCommand(cancelCmd);
        
        history.showHistory();
        
        // Undo cancellation
        System.out.println("\n--- Undo Cancellation ---");
        history.undo();
        
        history.showHistory();
        // Cleanup
        bookingManager.detach(student);
        System.out.println();
    }   
}