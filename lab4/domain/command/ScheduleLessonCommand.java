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
        System.out.println("\n⏰ Scheduling lesson...");
        System.out.println("   Time: " + timeSlot);
        System.out.println("   Tutor: " + tutor.getName());
        System.out.println("   Lesson: " + lesson.getClass().getSimpleName());
        bookingManager.bookLesson(tutor, lesson);
    }
    
    @Override
    public void undo() {
        System.out.println("\n❌ Cancelling scheduled lesson...");
        System.out.println("   Time: " + timeSlot);
        System.out.println("   Tutor: " + tutor.getName());
        bookingManager.cancelBooking(tutor, lesson);
    }
    
    @Override
    public String getDescription() {
        return "Schedule " + lesson.getClass().getSimpleName() + " with " + tutor.getName() + " at " + timeSlot;
    }
}
