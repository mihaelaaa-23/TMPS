# Topic: Behavioral Design Patterns
### Course: Software Modeling & Design Techniques (aka TMPS)
### Author: Mihaela Untu | FAF-232

---

## Objectives

* Get familiar with the Behavioral Design Patterns (BDPs).
* Continue the previous laboratory work on **Tutor Lessons Booking**.
* Implement at least 3 BDPs for the domain: **Observer, Strategy, Command**.

---

## Introduction

Behavioral Design Patterns are concerned with algorithms and the assignment of responsibilities between objects. These patterns help define how objects communicate with each other, making the communication more flexible and easier to understand. After implementing creational patterns (Lab 2) and structural patterns (Lab 3), I needed to focus on how different entities in my system interact and collaborate.

There are eleven main behavioral design patterns:

- **Chain of Responsibility** - Passes requests along a chain of handlers where each handler decides either to process the request or pass it to the next handler in the chain. Useful for event handling systems.

- **Command** - Encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations. Perfect for implementing undo/redo functionality.

- **Iterator** - Provides a way to access elements of a collection sequentially without exposing the underlying representation. Used extensively in collections and data structures.

- **Mediator** - Reduces chaotic dependencies between objects by forcing them to collaborate only via a mediator object. Promotes loose coupling by preventing objects from referring to each other explicitly.

- **Observer** - Defines a subscription mechanism to notify multiple objects about events happening to the object they're observing. Enables event-driven architectures and loose coupling between publishers and subscribers.

- **Strategy** - Defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

- **Visitor** - Lets you separate algorithms from the objects on which they operate, making it easy to add new operations without modifying the object classes.

- **Interpreter** - Provides a way to evaluate language grammar or expressions. Used for creating domain-specific languages or parsing structured text.

For this laboratory work, I focused on three behavioral patterns that solved communication and interaction problems in my tutor booking system:

- **Observer** - I needed a way for multiple parties (students, tutors, administrators) to be automatically notified when booking events occur. The Observer pattern creates a subscription model where observers are notified automatically.

- **Strategy** - Different pricing strategies need to be applied based on context (bulk purchases, seasonal promotions, referrals). The Strategy pattern encapsulates these algorithms and makes them interchangeable at runtime.

- **Command** - Booking operations (schedule, reschedule, cancel) need to be tracked, undone, and redone. The Command pattern encapsulates operations as objects, enabling powerful features like operation history and undo/redo.

---

## Used Design Patterns

For this laboratory work, I implemented three behavioral design patterns:

* **Observer Pattern** – Implements an event notification system where students, tutors, and administrators automatically receive updates about booking events (confirmed, starting, completed, cancelled).

* **Strategy Pattern** – Enables flexible pricing with different algorithms: standard pricing, bulk discounts, seasonal promotions, and referral rewards. The pricing strategy can be changed at runtime.

* **Command Pattern** – Encapsulates booking operations (schedule, reschedule, cancel) as command objects with full support for undo/redo operations and command history tracking.

---

## Implementation

### Overview

I extended my tutor lesson booking system from Lab 3 by adding behavioral patterns on top of the existing creational and structural patterns. The goal was to improve communication between system entities and make the system more flexible in handling operations and pricing.

Here's what I implemented:
1. **Observer pattern** for event-driven notifications
2. **Strategy pattern** for flexible pricing algorithms
3. **Command pattern** for operation encapsulation with undo/redo support

All of this works together with patterns from previous labs:
- **Lab 2 (Creational)**: Builder (Tutors), Singleton (BookingManager), Factory Method (Lessons)
- **Lab 3 (Structural)**: Decorator (Lesson features), Facade (Simplified booking), Adapter (Payment integration)

---

### 1. Observer Pattern

**The Problem:**
When a lesson is booked, multiple parties need to know:
- **Students** need to add it to their calendar
- **Tutors** need to prepare materials
- **Admin system** needs to update statistics

Without the Observer pattern, I would need tight coupling between the BookingManager and all these entities, making the code rigid and hard to maintain.

**The Solution:**
The Observer pattern creates a subscription mechanism where observers register with a subject and are automatically notified of state changes.

#### Implementation Details

**BookingObserver.java (Observer Interface)**
*Location: `lab4/domain/observer/BookingObserver.java`*

```java
public interface BookingObserver {
    void update(String eventType, String message);
}
```

This is the observer interface that all concrete observers must implement. The `update()` method is called by the subject when an event occurs.

**BookingSubject.java (Subject)**
*Location: `lab4/domain/observer/BookingSubject.java`*

```java
public class BookingSubject {
    private List<BookingObserver> observers = new ArrayList<>();
    
    public void attach(BookingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    public void detach(BookingObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(String eventType, String message) {
        for (BookingObserver observer : observers) {
            observer.update(eventType, message);
        }
    }
}
```

The `BookingSubject` maintains a list of observers and notifies them when events occur. Observers can be attached or detached at runtime, providing flexibility.

**StudentObserver.java (Concrete Observer)**
*Location: `lab4/domain/observer/StudentObserver.java`*

```java
public class StudentObserver implements BookingObserver {
    private String studentName;
    
    public StudentObserver(String studentName) {
        this.studentName = studentName;
    }
    
    @Override
    public void update(String eventType, String message) {
        System.out.print("   [Student " + studentName + "] ");
        
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("Added to calendar");
                break;
            case "LESSON_STARTING":
                System.out.println("Preparing for lesson");
                break;
            case "LESSON_COMPLETED":
                System.out.println("Time to review notes");
                break;
            case "BOOKING_CANCELLED":
                System.out.println("Removed from schedule");
                break;
            case "LESSON_RESCHEDULED":
                System.out.println("Schedule updated");
                break;
        }
    }
}
```

Each concrete observer (StudentObserver, TutorObserver, AdminObserver) implements the `update()` method with their specific logic. Students add lessons to calendars, tutors prepare materials, and admins update statistics.

**Integration with BookingManager:**
*Location: `lab4/domain/booking/BookingManager.java`*

```java
public class BookingManager extends BookingSubject {
    // BookingManager IS-A BookingSubject, so it has observer capabilities
    
    public void bookLesson(Tutor tutor, Lesson lesson) {
        // Notify all observers
        notifyObservers("BOOKING_CONFIRMED", 
            lesson.getClass().getSimpleName() + " booked with " + tutor.getName());
    }
    
    public void startLesson(Tutor tutor, Lesson lesson) {
        notifyObservers("LESSON_STARTING",
            "Lesson with " + tutor.getName() + " is starting now!");
    }
    
    public void completeLesson(Tutor tutor, Lesson lesson) {
        notifyObservers("LESSON_COMPLETED",
            "Lesson with " + tutor.getName() + " completed successfully!");
    }
    
    public void cancelBooking(Tutor tutor, Lesson lesson) {
        notifyObservers("BOOKING_CANCELLED",
            lesson.getClass().getSimpleName() + " with " + tutor.getName() + " has been cancelled.");
    }
}
```

By making `BookingManager` extend `BookingSubject`, it automatically gains observer management capabilities. Now whenever a booking event occurs, all registered observers are notified automatically.

**Usage Example:**

```java
BookingManager bookingManager = BookingManager.getInstance();

// Create and attach observers
StudentObserver student = new StudentObserver("Alice");
TutorObserver tutor = new TutorObserver("Dr. Smith");
AdminObserver admin = new AdminObserver();

bookingManager.attach(student);
bookingManager.attach(tutor);
bookingManager.attach(admin);

// When a lesson is booked, all three observers are notified automatically
bookingManager.bookLesson(tutorModel, lesson);
```

---

### 2. Strategy Pattern

**The Problem:**
Different customers need different pricing:
- Regular students pay standard price
- Students buying multiple lessons get bulk discounts (10% for 5+, 20% for 10+)
- Seasonal promotions (summer/winter sales)
- Referral rewards (5% per friend referred, max 30%)

Hard-coding all these pricing rules in one place would create a giant if-else mess that's hard to maintain and extend.

**The Solution:**
The Strategy pattern encapsulates each pricing algorithm in its own class, making them interchangeable. The pricing strategy can be selected and changed at runtime without modifying existing code.

#### Implementation Details

**PricingStrategy.java (Strategy Interface)**
*Location: `lab4/domain/strategy/PricingStrategy.java`*

```java
public interface PricingStrategy {
    double calculatePrice(double basePrice, int numberOfLessons);
    String getStrategyName();
    String getDescription();
}
```

This interface defines the contract for all pricing strategies. Each strategy implements its own algorithm in `calculatePrice()`.

**BulkDiscountStrategy.java (Concrete Strategy)**
*Location: `lab4/domain/strategy/BulkDiscountStrategy.java`*

```java
public class BulkDiscountStrategy implements PricingStrategy {
    
    @Override
    public double calculatePrice(double basePrice, int numberOfLessons) {
        double total = basePrice * numberOfLessons;
        
        if (numberOfLessons >= 10) {
            System.out.println("      20% bulk discount applied");
            return total * 0.80;  // 20% off
        } else if (numberOfLessons >= 5) {
            System.out.println("      10% bulk discount applied");
            return total * 0.90;  // 10% off
        }
        
        return total;
    }
    
    @Override
    public String getStrategyName() {
        return "Bulk Discount Pricing";
    }
    
    @Override
    public String getDescription() {
        return "10% off for 5+ lessons, 20% off for 10+ lessons";
    }
}
```

The bulk discount strategy implements tiered pricing based on quantity. Each strategy class contains its own algorithm logic.

**SeasonalDiscountStrategy.java (Concrete Strategy)**
*Location: `lab4/domain/strategy/SeasonalDiscountStrategy.java`*

```java
public class SeasonalDiscountStrategy implements PricingStrategy {
    private String season;
    private double discountPercentage;
    
    public SeasonalDiscountStrategy(String season, double discountPercentage) {
        this.season = season;
        this.discountPercentage = discountPercentage;
    }
    
    @Override
    public double calculatePrice(double basePrice, int numberOfLessons) {
        double total = basePrice * numberOfLessons;
        System.out.println("      " + (int)(discountPercentage * 100) + "% " + season + " discount applied");
        return total * (1 - discountPercentage);
    }
    
    @Override
    public String getStrategyName() {
        return season + " Seasonal Pricing";
    }
    
    @Override
    public String getDescription() {
        return "Special " + season + " discount: " + (discountPercentage * 100) + "% off";
    }
}
```

The seasonal strategy is configurable - you can create different seasonal promotions by passing different parameters.

**PricingContext.java (Context)**
*Location: `lab4/domain/strategy/PricingContext.java`*

```java
public class PricingContext {
    private PricingStrategy strategy;
    
    public PricingContext(PricingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
        System.out.println("\nStrategy: " + strategy.getStrategyName());
    }
    
    public double calculateTotalPrice(double basePrice, int numberOfLessons) {
        double totalPrice = strategy.calculatePrice(basePrice, numberOfLessons);
        System.out.println("   ✓ Total: $" + String.format("%.2f", totalPrice) + " (" + numberOfLessons + " lessons)");
        return totalPrice;
    }
}
```

The `PricingContext` maintains a reference to a strategy and delegates the calculation to it. The strategy can be changed at runtime using `setStrategy()`.

**Usage Example:**

```java
double lessonPrice = 50.0;

// Start with standard pricing
PricingContext context = new PricingContext(new StandardPricingStrategy());
context.calculateTotalPrice(lessonPrice, 3);  // $150.00

// Switch to bulk discount for a different customer
context.setStrategy(new BulkDiscountStrategy());
context.calculateTotalPrice(lessonPrice, 10);  // $400.00 (20% off)

// Apply seasonal promotion
context.setStrategy(new SeasonalDiscountStrategy("Summer", 0.15));
context.calculateTotalPrice(lessonPrice, 5);  // $212.50 (15% off)
```

I also implemented `ReferralPricingStrategy` which gives 5% discount per friend referred (max 30%).

---

### 3. Command Pattern

**The Problem:**
Booking operations need to be:
- **Tracked** - Keep a history of all operations
- **Undoable** - Students make mistakes, tutors cancel, schedules change
- **Redoable** - Changed mind about that undo?
- **Auditable** - Admin needs to see what happened

Direct method calls (`bookingManager.schedule()`) don't support any of this. Once executed, they can't be undone or tracked.

**The Solution:**
The Command pattern encapsulates operations as objects. Each operation (schedule, reschedule, cancel) becomes a command object that knows how to execute itself and how to undo itself.

#### Implementation Details

**Command.java (Command Interface)**
*Location: `lab4/domain/command/Command.java`*

```java
public interface Command {
    void execute();
    void undo();
    String getDescription();
}
```

Every command must implement `execute()` (to perform the action) and `undo()` (to reverse it).

**ScheduleLessonCommand.java (Concrete Command)**
*Location: `lab4/domain/command/ScheduleLessonCommand.java`*

```java
public class ScheduleLessonCommand implements Command {
    private BookingManager bookingManager;
    private Tutor tutor;
    private Lesson lesson;
    private String timeSlot;
    
    public ScheduleLessonCommand(BookingManager bookingManager, Tutor tutor, 
                                 Lesson lesson, String timeSlot) {
        this.bookingManager = bookingManager;
        this.tutor = tutor;
        this.lesson = lesson;
        this.timeSlot = timeSlot;
    }
    
    @Override
    public void execute() {
        System.out.println("\n[SCHEDULE] " + lesson.getClass().getSimpleName() + 
                         " with " + tutor.getName() + " at " + timeSlot);
        bookingManager.bookLesson(tutor, lesson);
    }
    
    @Override
    public void undo() {
        System.out.println("\n[CANCEL] " + lesson.getClass().getSimpleName() + 
                         " at " + timeSlot);
        bookingManager.cancelBooking(tutor, lesson);
    }
    
    @Override
    public String getDescription() {
        return "Schedule " + lesson.getClass().getSimpleName() + " with " + 
               tutor.getName() + " at " + timeSlot;
    }
}
```

The `ScheduleLessonCommand` encapsulates all the information needed to schedule a lesson. It knows how to execute the booking and how to undo it (by cancelling).

**RescheduleLessonCommand.java (Concrete Command)**
*Location: `lab4/domain/command/RescheduleLessonCommand.java`*

```java
public class RescheduleLessonCommand implements Command {
    private BookingManager bookingManager;
    private Tutor tutor;
    private Lesson lesson;
    private String oldTimeSlot;
    private String newTimeSlot;
    
    @Override
    public void execute() {
        System.out.println("\n[RESCHEDULE] " + lesson.getClass().getSimpleName() + 
                         " from " + oldTimeSlot + " to " + newTimeSlot);
        bookingManager.notifyObservers("LESSON_RESCHEDULED",
            lesson.getClass().getSimpleName() + " moved from " + 
            oldTimeSlot + " to " + newTimeSlot);
    }
    
    @Override
    public void undo() {
        System.out.println("\n[UNDO RESCHEDULE] " + lesson.getClass().getSimpleName() + 
                         " back to " + oldTimeSlot);
        bookingManager.notifyObservers("LESSON_RESCHEDULED",
            lesson.getClass().getSimpleName() + " moved back to " + oldTimeSlot);
    }
}
```

Reschedule command knows both the old and new time slots, so it can undo by reverting to the old slot.

**CommandHistory.java (Invoker)**
*Location: `lab4/domain/command/CommandHistory.java`*

```java
public class CommandHistory {
    private List<Command> history = new ArrayList<>();
    private int currentPosition = -1;
    
    public void executeCommand(Command command) {
        // Remove any commands after current position
        while (history.size() > currentPosition + 1) {
            history.remove(history.size() - 1);
        }
        
        command.execute();
        history.add(command);
        currentPosition++;
    }
    
    public void undo() {
        if (currentPosition < 0) {
            System.out.println("   [WARNING] Nothing to undo!");
            return;
        }
        
        Command command = history.get(currentPosition);
        command.undo();
        currentPosition--;
    }
    
    public void redo() {
        if (currentPosition >= history.size() - 1) {
            System.out.println("   [WARNING] Nothing to redo!");
            return;
        }
        
        currentPosition++;
        Command command = history.get(currentPosition);
        command.execute();
    }
}
```

The `CommandHistory` acts as an invoker that manages command execution and maintains a history. It supports full undo/redo functionality with position tracking.

**Usage Example:**

```java
CommandHistory history = new CommandHistory();

// Execute commands
Command cmd1 = new ScheduleLessonCommand(manager, tutor, lesson, "Monday 10:00 AM");
history.executeCommand(cmd1);

Command cmd2 = new RescheduleLessonCommand(manager, tutor, lesson, 
                                           "Monday 10:00 AM", "Tuesday 3:00 PM");
history.executeCommand(cmd2);

// Undo last operation
history.undo();  // Reverts the reschedule

// Redo it
history.redo();  // Reapplies the reschedule

// Show full history
history.showHistory();
```

I also implemented `CancelLessonCommand` which can be undone to restore a cancelled booking.

---

### 4. Integration and Client Code

All three behavioral patterns work together seamlessly with the existing patterns from previous labs.

**Main.java**
*Location: `lab4/client/Main.java`*

The `Main` class demonstrates all patterns:
1. **Observer pattern** - Multiple observers react to booking events
2. **Strategy pattern** - Different pricing strategies for different scenarios
3. **Command pattern** - Operations with full undo/redo support

The client code shows how these patterns solve real problems in the tutoring system while maintaining clean, maintainable code.

---

## Results / Screenshots

### Expected Console Output

The program demonstrates all three behavioral patterns with clean, professional output:

```
===============================================
  Lab 4: Behavioral Design Patterns Demo
===============================================

1. OBSERVER PATTERN - Event-driven notifications

[✓] 3 observers attached

--- Events trigger notifications to all observers ---
   [Student Alice] Added to calendar
   [Tutor Dr. Smith] Preparing materials
   [Admin] Analytics updated (booking count++)
   [Student Alice] Preparing for lesson
   [Tutor Dr. Smith] Ready to teach
   [Admin] Analytics updated (lesson started)
   [Student Alice] Time to review notes
   [Tutor Dr. Smith] Recording hours
   [Admin] Analytics updated (success rate++)

--- Student unsubscribes ---
[✓] Alice unsubscribed

--- Cancellation (only 2 observers notified) ---
   [Tutor Dr. Smith] Slot available
   [Admin] Analytics updated (cancellation tracked)


2. STRATEGY PATTERN - Flexible pricing algorithms

--- Different pricing strategies ---
   ✓ Total: $150.00 (3 lessons)

Strategy: Bulk Discount Pricing
      20% bulk discount applied
   ✓ Total: $400.00 (10 lessons)

Strategy: Summer Seasonal Pricing
      15% Summer discount applied
   ✓ Total: $212.50 (5 lessons)

Strategy: Referral Pricing
      15% referral discount (3 referrals)
   ✓ Total: $170.00 (4 lessons)


3. COMMAND PATTERN - Operations with undo/redo

--- Executing commands ---

[SCHEDULE] ProgrammingLesson with Prof. Johnson at Monday 10AM
   [Tutor Dr. Smith] Preparing materials
   [Admin] Analytics updated (booking count++)
   [Student Bob] Added to calendar

[SCHEDULE] MathLesson with Prof. Johnson at Wednesday 2PM
   [Tutor Dr. Smith] Preparing materials
   [Admin] Analytics updated (booking count++)
   [Student Bob] Added to calendar

[RESCHEDULE] ProgrammingLesson from Monday 10AM to Tuesday 3PM
   [Tutor Dr. Smith] Schedule updated
   [Admin] Analytics updated (reschedule tracked)
   [Student Bob] Schedule updated

Command History:
   1. Schedule ProgrammingLesson with Prof. Johnson at Monday 10AM
   2. Schedule MathLesson with Prof. Johnson at Wednesday 2PM
 > 3. Reschedule ProgrammingLesson from Monday 10AM to Tuesday 3PM

--- Testing undo/redo ---

[UNDO RESCHEDULE] ProgrammingLesson back to Monday 10AM
   [Tutor Dr. Smith] Schedule updated
   [Admin] Analytics updated (reschedule tracked)
   [Student Bob] Schedule updated

[CANCEL] MathLesson at Wednesday 2PM
   [Tutor Dr. Smith] Slot available
   [Admin] Analytics updated (cancellation tracked)
   [Student Bob] Removed from schedule

Command History:
 > 1. Schedule ProgrammingLesson with Prof. Johnson at Monday 10AM
   2. Schedule MathLesson with Prof. Johnson at Wednesday 2PM
   3. Reschedule ProgrammingLesson from Monday 10AM to Tuesday 3PM

[SCHEDULE] MathLesson with Prof. Johnson at Wednesday 2PM
   [Tutor Dr. Smith] Preparing materials
   [Admin] Analytics updated (booking count++)
   [Student Bob] Added to calendar

Command History:
   1. Schedule ProgrammingLesson with Prof. Johnson at Monday 10AM
 > 2. Schedule MathLesson with Prof. Johnson at Wednesday 2PM
   3. Reschedule ProgrammingLesson from Monday 10AM to Tuesday 3PM

[CANCEL] ProgrammingLesson at Tuesday 3PM (Emergency)
   [Tutor Dr. Smith] Slot available
   [Admin] Analytics updated (cancellation tracked)
   [Student Bob] Removed from schedule

Command History:
   1. Schedule ProgrammingLesson with Prof. Johnson at Monday 10AM
   2. Schedule MathLesson with Prof. Johnson at Wednesday 2PM
 > 3. Cancel ProgrammingLesson with Prof. Johnson at Tuesday 3PM (Reason: Emergency)

--- Undo cancellation ---

[RESTORE] ProgrammingLesson at Tuesday 3PM
   [Tutor Dr. Smith] Preparing materials
   [Admin] Analytics updated (booking count++)
   [Student Bob] Added to calendar

Command History:
   1. Schedule ProgrammingLesson with Prof. Johnson at Monday 10AM
 > 2. Schedule MathLesson with Prof. Johnson at Wednesday 2PM
   3. Cancel ProgrammingLesson with Prof. Johnson at Tuesday 3PM (Reason: Emergency)
```

**Key Features Demonstrated:**
- **Observer Pattern**: Multiple observers receive automatic notifications for booking events
- **Strategy Pattern**: Different pricing algorithms applied dynamically (standard, bulk, seasonal, referral)
- **Command Pattern**: Full undo/redo support with command history tracking

---

## Conclusions

In this lab, I successfully implemented three behavioral design patterns that made my tutor booking system way more flexible and easier to maintain.

- **Observer Pattern** for automatic notifications - when something happens in the system (booking confirmed, lesson starting, etc.), everyone who needs to know gets notified automatically. No more manual updates!
- **Strategy Pattern** for flexible pricing - I can easily switch between different pricing algorithms (standard, bulk discount, seasonal promotions, referrals) without changing the core code.
- **Command Pattern** for undo/redo functionality - all booking operations can now be undone and redone, plus I have a full history of what happened.

Behavioral patterns are really about how objects talk to each other and who's responsible for what. Before this lab, I would have probably hard-coded everything with lots of if-else statements and tight coupling. Now I see how these patterns make the code cleaner and more maintainable.

The Observer pattern was especially cool - it's like having a notification system built into the code. The Strategy pattern made me realize you can swap entire algorithms at runtime without breaking anything. And the Command pattern showing that operations can be objects too was mind-blowing - it opens up so many possibilities like logging, undo/redo, and macros.
