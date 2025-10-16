package lab2.client;

import lab2.domain.BookingManager;
import lab2.domain.factory.LessonFactory;
import lab2.domain.models.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern Demo ===");
        // Builder: create tutors step-by-step
        Tutor mathTutor = new Tutor.Builder()
                .setName("Lucas")
                .setSubject("Math")
                .setExperience(10)
                .build();

        Tutor progTutor = new Tutor.Builder()
                .setName("Emily")
                .setSubject("Programming")
                .setExperience(5)
                .build();

        Tutor englishTutor = new Tutor.Builder()
                .setName("Josh")
                .setSubject("English")
                .setExperience(8)
                .build();

        System.out.println(mathTutor);
        System.out.println(progTutor);
        System.out.println(englishTutor);

        System.out.println("\n=== Singleton Pattern Demo ===");
        // Singleton: BookingManager should have only one instance
        BookingManager manager1 = BookingManager.getInstance();
        BookingManager manager2 = BookingManager.getInstance();
        System.out.println("manager1 == manager2? " + (manager1 == manager2));

        System.out.println("\n=== Factory Method Pattern Demo ===");
        // Factory: create lessons without client knowing exact classes
        Lesson mathLesson = LessonFactory.createLesson("math");
        Lesson progLesson = LessonFactory.createLesson("programming");
        Lesson englishLesson = LessonFactory.createLesson("english");

        // Book lessons using singleton manager
        manager1.bookLesson(mathTutor, mathLesson);
        mathLesson.teach();

        manager1.bookLesson(progTutor, progLesson);
        progLesson.teach();

        manager1.bookLesson(englishTutor, englishLesson);
        englishLesson.teach();


        System.out.println("\n=== Error Handling Demo ===");
        // Factory error cases
        try {
            Lesson invalidLesson = LessonFactory.createLesson("history"); // unknown lesson
        } catch (IllegalArgumentException e) {
            System.out.println("Caught error: " + e.getMessage());
        }

        try {
            Lesson nullLesson = LessonFactory.createLesson(null); // null input
        } catch (IllegalArgumentException e) {
            System.out.println("Caught error: " + e.getMessage());
        }
    }
}
