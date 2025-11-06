package lab3.domain.models;

public class ProgrammingLesson implements Lesson {
    @Override
    public void teach() {
        System.out.println("Teaching Programming lesson...");
    }
}
