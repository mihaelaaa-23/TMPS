package lab4.domain.command;

import lab4.domain.booking.BookingManager;
import lab4.domain.models.Lesson;
import lab4.domain.models.Tutor;

// Concrete Command - Reschedule a lesson
public class RescheduleLessonCommand implements Command {
    private BookingManager bookingManager;
    private Tutor tutor;
    private Lesson lesson;
    private String oldTimeSlot;
    private String newTimeSlot;
    
    public RescheduleLessonCommand(BookingManager bookingManager, Tutor tutor, Lesson lesson, 
                                   String oldTimeSlot, String newTimeSlot) {
        this.bookingManager = bookingManager;
        this.tutor = tutor;
        this.lesson = lesson;
        this.oldTimeSlot = oldTimeSlot;
        this.newTimeSlot = newTimeSlot;
    }
    
    @Override
    public void execute() {
        System.out.println("\n[RESCHEDULE] " + lesson.getClass().getSimpleName() + " from " + oldTimeSlot + " to " + newTimeSlot);
        
        // Notify observers about the change
        bookingManager.notifyObservers("LESSON_RESCHEDULED",
            lesson.getClass().getSimpleName() + " with " + tutor.getName() + 
            " moved from " + oldTimeSlot + " to " + newTimeSlot);
    }
    
    @Override
    public void undo() {
        System.out.println("\n[UNDO RESCHEDULE] " + lesson.getClass().getSimpleName() + " back to " + oldTimeSlot);
        
        bookingManager.notifyObservers("LESSON_RESCHEDULED",
            lesson.getClass().getSimpleName() + " with " + tutor.getName() + 
            " moved back to " + oldTimeSlot);
    }
    
    @Override
    public String getDescription() {
        return "Reschedule " + lesson.getClass().getSimpleName() + " from " + oldTimeSlot + " to " + newTimeSlot;
    }
}
