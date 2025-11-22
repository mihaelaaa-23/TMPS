package lab4.domain.facade;

import lab4.domain.booking.BookingManager;
import lab4.domain.decorators.MaterialsLessonDecorator;
import lab4.domain.decorators.PremiumLessonDecorator;
import lab4.domain.decorators.RecordedLessonDecorator;
import lab4.domain.factory.LessonFactory;
import lab4.domain.models.Lesson;
import lab4.domain.models.Tutor;

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
