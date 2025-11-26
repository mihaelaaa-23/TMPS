package lab4.domain.observer;

// Concrete Observer - Admin monitors all system events
public class AdminObserver implements BookingObserver {
    
    @Override
    public void update(String eventType, String message) {
        System.out.print("   [Admin] ");
        
        // Admin-specific tracking
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("Analytics updated (booking count++)");
                break;
            case "LESSON_STARTING":
                System.out.println("Analytics updated (lesson started)");
                break;
            case "LESSON_COMPLETED":
                System.out.println("Analytics updated (success rate++)");
                break;
            case "BOOKING_CANCELLED":
                System.out.println("Analytics updated (cancellation tracked)");
                break;
            case "LESSON_RESCHEDULED":
                System.out.println("Analytics updated (reschedule tracked)");
                break;
        }
    }
}
