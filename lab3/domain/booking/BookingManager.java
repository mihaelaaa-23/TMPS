package lab3.domain.booking;
import lab3.domain.models.Tutor;
import lab3.domain.models.Lesson;
// Singleton class to manage bookings
public class BookingManager {
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
        System.out.println("Booking " + lesson.getClass().getSimpleName() + " with " + tutor.toString());
    }

}