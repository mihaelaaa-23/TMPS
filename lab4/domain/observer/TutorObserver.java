package lab4.domain.observer;

// Concrete Observer - Tutor receives notifications
public class TutorObserver implements BookingObserver {
    private String tutorName;
    
    public TutorObserver(String tutorName) {
        this.tutorName = tutorName;
    }
    
    @Override
    public void update(String eventType, String message) {
        System.out.println("   👨‍🏫 [Tutor: " + tutorName + "] received notification:");
        System.out.println("      Event: " + eventType);
        System.out.println("      Message: " + message);
        
        // Tutor-specific actions based on event type
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("      📚 Preparing lesson materials...");
                break;
            case "LESSON_STARTING":
                System.out.println("      🎯 Ready to teach!");
                break;
            case "LESSON_COMPLETED":
                System.out.println("      💰 Recording hours for payment.");
                break;
            case "BOOKING_CANCELLED":
                System.out.println("      📅 Slot is now available.");
                break;
        }
    }
}
