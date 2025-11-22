package lab4.domain.models;

public class Tutor {
    private final String name;
    private final String subject;
    private final int experience;

    private Tutor(Builder builder) {
        this.name = builder.name;
        this.subject = builder.subject;
        this.experience = builder.experience;
    }

    public static class Builder {
        private String name;
        private String subject;
        private int experience;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setExperience(int experience) {
            this.experience = experience;
            return this;
        }

        public Tutor build() {
            return new Tutor(this);
        }
    }
    
    @Override
    public String toString() {
        return name + " who teaches " + subject + " (" + experience + " years)";
    }

    public String getName() {
        return name;
    }
    public String getSubject() {
        return subject;
    }
    public int getExperience() {
        return experience;
    }
}
