# Topic: Creational Design Patterns
### Course: Software Modeling & Design Techniques (aka TMPS)
### Author: Mihaela Untu | FAF-232

---

## Objectives

* Get familiar with the Creational Design Patterns (CDPs).
* Choose a specific domain — in this case, **Tutor Lessons Booking**.
* Implement at least 3 CDPs for the domain: **Builder, Singleton, Factory Method**.

---

## Introduction

- **Factory Method** - Provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.
- **Abstract Factory** - Lets you produce families of related objects without specifying their concrete classes.
- **Builder** - Lets you construct complex objects step by step. The patterns allows you to produce different types and representations of an object using the same construction code.
- **Prototype** - Lets you copy existing objects without making your code dependent on their classes.
- **Singleton** - Lets you ensure that a class has only one instance, while providing a global access point to this instance.

---

## Used Design Patterns

* **Builder** – To create Tutor objects step by step with different attributes (name, subject, experience).
* **Singleton** – To ensure a single instance of `BookingManager` that handles all lesson bookings.
* **Factory Method** – To create different types of lessons (`MathLesson`, `ProgrammingLesson`, `PhysicsLesson`) without coupling client code to concrete classes.

---

## Implementation

In this project, we simulated a simple lesson booking system:

* Tutors are created using the **Builder pattern** for flexible initialization.
* The **Singleton pattern** ensures only one `BookingManager` handles all lesson bookings.
* The **Factory Method pattern** allows the creation of different lesson types dynamically, supporting extensibility.

---

### Code Snippets

**Tutor.java (Builder Pattern)**

```java
private Tutor(Builder builder) {
        this.name = builder.name;
        this.subject = builder.subject;
        this.experience = builder.experience;
    }
```

**BookingManager.java (Singleton Pattern)**

```java
public class BookingManager {
    private static volatile BookingManager instance;
    private BookingManager() {}

    public static BookingManager getInstance() {
        if (instance == null) {
            synchronized (BookingManager.class) {
                if (instance == null) {
                    instance = new BookingManager();
                }
            }
        }
        return instance;
    }
```

**LessonFactory.java (Factory Method Pattern)**

```java
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
```

**Main.java (Testing All Patterns)**

```java
BookingManager manager = BookingManager.getInstance();
manager.bookLesson(mathTutor, mathLesson);
manager.bookLesson(progTutor, progLesson);
manager.bookLesson(englishTutor, englishLesson);
```

---

## Results / Screenshots

### Expected Console Output

```
=== Builder Pattern Demo ===
Lucas who teaches Math (10 years)
Emily who teaches Programming (5 years)
Josh who teaches English (8 years)

=== Singleton Pattern Demo ===
manager1 == manager2? true

=== Factory Method Pattern Demo ===
Booking MathLesson with Lucas who teaches Math (10 years)
Teaching Math lesson...
Booking ProgrammingLesson with Emily who teaches Programming (5 years)
Teaching Programming lesson...
Booking EnglishLesson with Josh who teaches English (8 years)
Teaching English lesson...

=== Error Handling Demo ===
Caught error: Unknown lesson type: history
Caught error: Lesson type cannot be null
```
---

## Conclusions

The project demonstrates the **usefulness of Creational Design Patterns**:

  * **Builder** allows flexible and readable creation of complex objects.
  * **Singleton** ensures only one booking manager exists, centralizing lesson bookings.
  * **Factory Method** decouples lesson creation from client code, making the system extensible.

* This small project shows a **realistic scenario** where combining these three patterns improves code clarity, maintainability, and scalability.
