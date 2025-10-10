package model;
// Tutor class only stores tutor data (Single Responsibility Principle)
public class Tutor {
    private final String name;

    public Tutor(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return "Tutor{name='" + name + "'}";
    }
}