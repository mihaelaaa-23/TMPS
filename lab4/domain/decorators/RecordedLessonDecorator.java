package lab4.domain.decorators;

import lab4.domain.models.Lesson;

public class RecordedLessonDecorator extends LessonDecorator {
    public RecordedLessonDecorator(Lesson lesson) {
        super(lesson);
    }

    @Override
    public void teach() {
        System.out.println("[Recording] 🔴 Recording started...");
        wrappedLesson.teach();
        System.out.println("[Recording] ✅ Session recorded. Link: https://recordings.tutorsystem.com/session123");
    }
}
