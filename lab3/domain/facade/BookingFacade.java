package lab3.domain.facade;

import lab3.domain.booking.BookingManager;
import lab3.domain.decorators.MaterialsLessonDecorator;
import lab3.domain.decorators.PremiumLessonDecorator;
import lab3.domain.decorators.RecordedLessonDecorator;
import lab3.domain.factory.LessonFactory;
import lab3.domain.models.Lesson;
import lab3.domain.models.Tutor;

public class BookingFacade {
    private BookingManager bookingManager;
    public BookingFacade() {
        this.bookingManager = BookingManager.getInstance();
    }

    public void quickBook(String lessonType, String tutorName, String subject, int experience) {
        System.out.println("\n--- Quick Booking via Facade ---");

        Tutor tutor = new Tutor.Builder()
                .setName(tutorName)
                .setSubject(subject)
                .setExperience(experience)
                .build();
    
    Lesson lesson = LessonFactory.createLesson(lessonType);
    bookingManager.bookLesson(tutor, lesson);
    lesson.teach();

    }

    public void bookPremiumLesson(String lessonType, String tutorName, String subject, int experience) {
        System.out.println("\n--- Premium Booking via Facade ---");

        Tutor tutor = new Tutor.Builder()
                .setName(tutorName)
                .setSubject(subject)
                .setExperience(experience)
                .build();
        
        Lesson lesson = LessonFactory.createLesson(lessonType);
        lesson = new PremiumLessonDecorator(lesson);

        bookingManager.bookLesson(tutor, lesson);
        lesson.teach();
    }

    public void bookCompletePackage(String lessonType, String tutorName, String subject, int experience) {
        System.out.println("\n--- Complete Package Booking via Facade ---");

        Tutor tutor = new Tutor.Builder()
                .setName(tutorName)
                .setSubject(subject)
                .setExperience(experience)
                .build();
        
        Lesson lesson = LessonFactory.createLesson(lessonType);
        lesson = new PremiumLessonDecorator(lesson);
        lesson = new RecordedLessonDecorator(lesson);
        lesson = new MaterialsLessonDecorator(lesson);
        
        bookingManager.bookLesson(tutor, lesson);
        lesson.teach();
    }
}
