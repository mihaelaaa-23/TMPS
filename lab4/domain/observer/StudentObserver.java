package lab4.domain.observer;

// Concrete Observer - Student receives notifications
public class StudentObserver implements BookingObserver {
    private String studentName;
    
    public StudentObserver(String studentName) {
        this.studentName = studentName;
    }
    
    @Override
    public void update(String eventType, String message) {
        System.out.print("   [Student " + studentName + "] ");
        
        // Student-specific actions based on event type
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("Added to calendar");
                break;
            case "LESSON_STARTING":
                System.out.println("Preparing for lesson");
                break;
            case "LESSON_COMPLETED":
                System.out.println("Time to review notes");
                break;
            case "BOOKING_CANCELLED":
                System.out.println("Removed from schedule");
                break;
            case "LESSON_RESCHEDULED":
                System.out.println("Schedule updated");
                break;
        }
    }
}
