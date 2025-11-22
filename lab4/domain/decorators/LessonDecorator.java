package lab4.domain.decorators;

import lab4.domain.models.Lesson;

public abstract class LessonDecorator implements Lesson{
    protected Lesson wrappedLesson;

    public LessonDecorator(lab4.domain.models.Lesson lesson) {
        this.wrappedLesson = lesson;
    }

    @Override
    public void teach() {
        wrappedLesson.teach();
    }
}
