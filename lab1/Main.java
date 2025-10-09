public class Main {
    public static void main(String[] args) {
        Tutor tutor = new Tutor("Lucas");
        Lesson lesson = new ProgrammingLesson();
        Notifier notifier = new SMSNotifier();
        BookingManager manager = new BookingManager(notifier);

        manager.bookLesson(tutor, lesson);
    }
}