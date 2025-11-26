package lab4.domain.command;

import lab4.domain.booking.BookingManager;
import lab4.domain.models.Lesson;
import lab4.domain.models.Tutor;

// Concrete Command - Schedule a lesson
public class ScheduleLessonCommand implements Command {
    private BookingManager bookingManager;
    private Tutor tutor;
    private Lesson lesson;
    private String timeSlot;
    
    public ScheduleLessonCommand(BookingManager bookingManager, Tutor tutor, Lesson lesson, String timeSlot) {
        this.bookingManager = bookingManager;
        this.tutor = tutor;
        this.lesson = lesson;
        this.timeSlot = timeSlot;
    }
    
    @Override
    public void execute() {
        System.out.println("\n[SCHEDULE] " + lesson.getClass().getSimpleName() + " with " + tutor.getName() + " at " + timeSlot);
        bookingManager.bookLesson(tutor, lesson);
    }
    
    @Override
    public void undo() {
        System.out.println("\n[CANCEL] " + lesson.getClass().getSimpleName() + " at " + timeSlot);
        bookingManager.cancelBooking(tutor, lesson);
    }
    
    @Override
    public String getDescription() {
        return "Schedule " + lesson.getClass().getSimpleName() + " with " + tutor.getName() + " at " + timeSlot;
    }
}
