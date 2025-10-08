public class Main {
    public static void main(String[] args) {
        Tutor tutor = new Tutor("Lucas");
        Lesson lesson = new EnglishLesson();
        Notifier notifier = new EmailNotifier();
        BookingManager manager = new BookingManager(notifier);

        manager.bookLesson(tutor, lesson);
    }
}