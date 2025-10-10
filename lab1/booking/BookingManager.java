package booking;
import lesson.Lesson;
import model.Tutor;
import notifier.Notifier;

public class BookingManager {
    private final Notifier notifier;

    public BookingManager(Notifier notifier) {
        this.notifier = notifier;
    }

    public void bookLesson(Tutor tutor, Lesson lesson) {
        System.out.println("Booking lesson with " + tutor.getName());
        lesson.teach();
        notifier.send("Lesson booked with " + tutor.getName());
    }
}