package lab4.domain.decorators;

import lab4.domain.models.Lesson;

public class PremiumLessonDecorator extends LessonDecorator {
    public PremiumLessonDecorator(Lesson lesson) {
        super(lesson);
    }

    @Override
    public void teach() {
        addPremiumIntro();
        wrappedLesson.teach();
        addPremiumOutro();
    }

    private void addPremiumIntro() {
        System.out.println("[Premium Feature] 🌟 Starting one-on-one session with priority support...");
    }

    private void addPremiumOutro() {
        System.out.println("[Premium Feature] ✨ Extended Q&A time included!");
    }
}
