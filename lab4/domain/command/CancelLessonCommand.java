package lab4.domain.command;

import lab4.domain.booking.BookingManager;
import lab4.domain.models.Lesson;
import lab4.domain.models.Tutor;

// Concrete Command - Cancel a lesson
public class CancelLessonCommand implements Command {
    private BookingManager bookingManager;
    private Tutor tutor;
    private Lesson lesson;
    private String timeSlot;
    private String reason;
    
    public CancelLessonCommand(BookingManager bookingManager, Tutor tutor, Lesson lesson, 
                               String timeSlot, String reason) {
        this.bookingManager = bookingManager;
        this.tutor = tutor;
        this.lesson = lesson;
        this.timeSlot = timeSlot;
        this.reason = reason;
    }
    
    @Override
    public void execute() {
        System.out.println("\n[CANCEL] " + lesson.getClass().getSimpleName() + " at " + timeSlot + " (" + reason + ")");
        bookingManager.cancelBooking(tutor, lesson);
    }
    
    @Override
    public void undo() {
        System.out.println("\n[RESTORE] " + lesson.getClass().getSimpleName() + " at " + timeSlot);
        bookingManager.bookLesson(tutor, lesson);
    }
    
    @Override
    public String getDescription() {
        return "Cancel " + lesson.getClass().getSimpleName() + " with " + tutor.getName() + 
               " at " + timeSlot + " (Reason: " + reason + ")";
    }
}
