package lab3.domain.decorators;

public class MaterialsLessonDecorator extends LessonDecorator {
    public MaterialsLessonDecorator(lab3.domain.models.Lesson lesson) {
        super(lesson);
    }

    @Override
    public void teach() {
        System.out.println("[Materials] 📚 Providing study materials (notes.pdf, exercises.pdf)...");
        wrappedLesson.teach();
        System.out.println("[Materials] 📥 Materials available for download in your dashboard!");
    }
}
