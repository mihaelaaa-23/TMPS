package lab4.domain.observer;

// Concrete Observer - Admin monitors all system events
public class AdminObserver implements BookingObserver {
    
    @Override
    public void update(String eventType, String message) {
        System.out.println("   🔧 [Admin System] logged event:");
        System.out.println("      Event: " + eventType);
        System.out.println("      Message: " + message);
        System.out.println("      📊 Updating analytics dashboard...");
        
        // Admin-specific actions - logging and monitoring
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("      ✓ Booking count incremented.");
                break;
            case "LESSON_COMPLETED":
                System.out.println("      ✓ Success rate updated.");
                break;
            case "BOOKING_CANCELLED":
                System.out.println("      ⚠ Cancellation rate tracked.");
                break;
        }
    }
}
