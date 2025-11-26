package lab4.domain.observer;

// Concrete Observer - Tutor receives notifications
public class TutorObserver implements BookingObserver {
    private String tutorName;
    
    public TutorObserver(String tutorName) {
        this.tutorName = tutorName;
    }
    
    @Override
    public void update(String eventType, String message) {
        System.out.print("   [Tutor " + tutorName + "] ");
        
        // Tutor-specific actions based on event type
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("Preparing materials");
                break;
            case "LESSON_STARTING":
                System.out.println("Ready to teach");
                break;
            case "LESSON_COMPLETED":
                System.out.println("Recording hours");
                break;
            case "BOOKING_CANCELLED":
                System.out.println("Slot available");
                break;
            case "LESSON_RESCHEDULED":
                System.out.println("Schedule updated");
                break;
        }
    }
}
