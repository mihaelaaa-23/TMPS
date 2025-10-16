package lab2.domain.factory;
import lab2.domain.models.*;

public class LessonFactory {
    public static Lesson createLesson(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Lesson type cannot be null");
        }

        switch (type.toLowerCase()) {
            case "math":
                return new MathLesson();
            case "programming":
                return new ProgrammingLesson();
            case "english":
                return new EnglishLesson();
            default:
                throw new IllegalArgumentException("Unknown lesson type: " + type);
        }

    }
}
