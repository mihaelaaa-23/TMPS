package lab4.domain.booking;
import lab4.domain.models.Tutor;
import lab4.domain.models.Lesson;
import lab4.domain.observer.BookingSubject;

// Singleton class to manage bookings - now with Observer pattern integration
public class BookingManager extends BookingSubject {
    private static volatile BookingManager instance;
    
    private BookingManager() {}

    public static BookingManager getInstance() {
        if (instance == null) {
            synchronized (BookingManager.class) {
                if (instance == null) {
                    instance = new BookingManager();
                }
            }
        }
        return instance;
    }

    public void bookLesson(Tutor tutor, Lesson lesson) {
        // Notify observers about the booking
        notifyObservers("BOOKING_CONFIRMED", 
            lesson.getClass().getSimpleName() + " booked with " + tutor.getName());
    }
    
    public void startLesson(Tutor tutor, Lesson lesson) {
        notifyObservers("LESSON_STARTING",
            "Lesson with " + tutor.getName() + " is starting now!");
    }
    
    public void completeLesson(Tutor tutor, Lesson lesson) {
        notifyObservers("LESSON_COMPLETED",
            "Lesson with " + tutor.getName() + " completed successfully!");
    }
    
    public void cancelBooking(Tutor tutor, Lesson lesson) {
        notifyObservers("BOOKING_CANCELLED",
            lesson.getClass().getSimpleName() + " with " + tutor.getName() + " has been cancelled.");
    }
}