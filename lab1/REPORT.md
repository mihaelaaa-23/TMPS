# Topic: SOLID Principles
### Course: Software Modeling & Design Techniques (aka TMPS)
### Author: Mihaela Untu | FAF-232

---

## Purpose
Implement 3 SOLID letters in a simple project

## Introduction
`SOLID` is an acronym for the first five object-oriented design (OOD) principles by Robert C. Martin.
- S - Single-responsiblity Principle
- O - Open-closed Principle
- L - Liskov Substitution Principle
- I - Interface Segregation Principle
- D - Dependency Inversion Principle



## Implementation
In this lab, I have focused on the following three principles:

### 1. Single Responsibility Principle (SRP)
- **Tutor.java**: The `Tutor` class is responsible only for storing tutor data (name). It does not handle lesson logic or notifications.

### 2. Open/Closed Principle (OCP)
- **Lesson.java**: The `Lesson` interface allows for new lesson types (e.g., `MathLesson`, `EnglishLesson`, `ProgrammingLesson`) to be added without modifying existing code.

### 3. Dependency Inversion Principle (DIP)
- **BookingManager.java**: Depends on the `Notifier` abstraction, not concrete notification classes. This allows for flexible notification strategies.


## Project Structure

- `Tutor.java` — Stores tutor data
- `Lesson.java` — Interface for lessons
- `MathLesson.java`, `EnglishLesson.java`, `ProgrammingLesson.java` — Lesson implementations
- `Notifier.java` — Interface for notifications
- `EmailNotifier.java`, `SMSNotifier.java` — Notification implementations
- `BookingManager.java` — Handles lesson booking and notification
- `Main.java`, `QuickTest.java` — Example usage and testing

## Class, Interface, and Method Reference

1. **Tutor**
  - Fields: `name: String` — tutor’s name
  - Constructors: `Tutor(String name)` — creates a tutor
  - Methods:
    - `String getName()` — returns the tutor’s name
    - `String toString()` — human-readable representation

2. **Lesson** (interface)
  - Methods: `void teach()` — perform the lesson’s teaching action

- **MathLesson** implements Lesson
  - Methods: `void teach()` — prints “Teaching Math lesson...”

- **EnglishLesson** implements Lesson
  - Methods: `void teach()` — prints “Teaching English lesson...”

- **ProgrammingLesson** implements Lesson
  - Methods: `void teach()` — prints “Teaching Programming lesson...”

3. **Notifier** (interface)
  - Methods: `void send(String message)` — send a notification with a message

- **EmailNotifier** implements Notifier
  - Methods: `void send(String message)` — prints “Sending email notification: {message}”

- **SMSNotifier** implements Notifier
  - Methods: `void send(String message)` — prints “Sending SMS notification: {message}”

4. **BookingManager**
  - Fields: `notifier: Notifier` — decoupled notification strategy
  - Constructors: `BookingManager(Notifier notifier)` — injects the notifier
  - Methods: `void bookLesson(Tutor tutor, Lesson lesson)` — prints booking info, calls `lesson.teach()`, then `notifier.send(...)`

5. **Main**
  - Methods: `static void main(String[] args)` — demo: Programming lesson with SMS notification


## Testing Scenarios

### Scenario 1: Book a Programming Lesson with SMS Notification
**Code:**
```java
public class Main {
    public static void main(String[] args) {
        Tutor tutor = new Tutor("Lucas");
        Lesson lesson = new ProgrammingLesson();
        Notifier notifier = new SMSNotifier();
        BookingManager manager = new BookingManager(notifier);

        manager.bookLesson(tutor, lesson);
    }
}
```
**Output:**
```
Booking lesson with Lucas
Teaching Programming lesson...
Sending SMS notification: Lesson booked with Lucas
```

---

### Scenario 2: Book a Math Lesson with Email Notification
**Code:**
```java
public class QuickTest {
    public static void main(String[] args) {
        Tutor tutor = new Tutor("Alice");
        Lesson lesson = new MathLesson();
        Notifier notifier = new EmailNotifier();
        BookingManager manager = new BookingManager(notifier);

        manager.bookLesson(tutor, lesson);
    }
}
```
**Output:**
```
Booking lesson with Alice
Teaching Math lesson...
Sending email notification: Lesson booked with Alice
```

---

### Scenario 3: Book an English Lesson (add this test)
**Code:**
```java
Tutor tutor = new Tutor("Emma");
Lesson lesson = new EnglishLesson();
Notifier notifier = new SMSNotifier();
BookingManager manager = new BookingManager(notifier);
manager.bookLesson(tutor, lesson);
```
**Output:**
```
Booking lesson with Emma
Teaching English lesson...
Sending SMS notification: Lesson booked with Emma
```



## How to Run and Test

From the repo root:
```sh
cd lab1
javac lab1/*.java
java -cp lab1 Main
java -cp lab1 QuickTest
```

Clean compiled artifacts:
```sh
find lab1 -name "*.class" -delete
```


## Version Control Recommendations
- Exclude `.class` and temporary files using `.gitignore`:
  ```
    *.class
    tempCodeRunnerFile.*
  ```
- Commit in logical groups (interfaces, implementations, business logic, tests).



## Conclusion
This project is a clear example of how to apply SOLID principles in Java. The design is modular, easy to extend, and supports flexible testing and notification strategies.
- Each class is kept in its own file with clear responsibilities (SRP).
- The `Lesson` and `Notifier` abstractions make it easy to add new lessons or notification channels without changing existing code (OCP + DIP).
- Optional: create a very small "composition root" in `Main` to wire dependencies; for example, choose the `Notifier` based on a variable (e.g., `useSms = true ? new SMSNotifier() : new EmailNotifier();`).
- Optional: if the project grows, consider packages like `lessons` and `notifiers` and a build tool (Maven/Gradle) to separate sources and compiled classes.
