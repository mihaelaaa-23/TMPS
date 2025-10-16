package lab2.domain.models;

public class MathLesson implements Lesson {
    @Override
    public void teach() {
        System.out.println("Teaching Math lesson...");
    }
}
