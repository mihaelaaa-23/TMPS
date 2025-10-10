package app;
import booking.BookingManager;
import lesson.EnglishLesson;
import lesson.Lesson;
import lesson.MathLesson;
import lesson.ProgrammingLesson;
import model.Tutor;
import notifier.EmailNotifier;
import notifier.Notifier;
import notifier.SMSNotifier;

public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------");

        Tutor tutor = new Tutor("Lucas");
        Lesson lesson = new ProgrammingLesson();
        Notifier notifier = new SMSNotifier();
        BookingManager manager = new BookingManager(notifier);
        manager.bookLesson(tutor, lesson);

        System.out.println("---------------------------");
        Tutor tutor1 = new Tutor("Emma");
        Lesson lesson1 = new MathLesson();
        Notifier notifier1 = new EmailNotifier();
        BookingManager manager1 = new BookingManager(notifier1);
        manager1.bookLesson(tutor1, lesson1);

        System.out.println("---------------------------");
        Tutor tutor2 = new Tutor("Olivia");
        Lesson lesson2 = new EnglishLesson();
        Notifier notifier2 = new SMSNotifier();
        BookingManager manager2 = new BookingManager(notifier2);
        manager2.bookLesson(tutor2, lesson2);
    }
}