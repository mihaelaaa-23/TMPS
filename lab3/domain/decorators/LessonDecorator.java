package lab3.domain.decorators;

import lab3.domain.models.Lesson;

public abstract class LessonDecorator implements Lesson{
    protected Lesson wrappedLesson;

    public LessonDecorator(lab3.domain.models.Lesson lesson) {
        this.wrappedLesson = lesson;
    }

    @Override
    public void teach() {
        wrappedLesson.teach();
    }
}
