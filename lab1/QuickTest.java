public class QuickTest {
    public static void main(String[] args) {
        Tutor tutor = new Tutor("Alice");
        // System.out.println("Tutor name: " + tutor.getName());
        // System.out.println(tutor);

        // Lesson math = new MathLesson();
        // Lesson english = new EnglishLesson();

        // math.teach();
        // english.teach();
        Lesson lesson = new MathLesson();
        Notifier notifier = new EmailNotifier();
        BookingManager manager = new BookingManager(notifier);

        manager.bookLesson(tutor, lesson);
    }
}