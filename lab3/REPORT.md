# Topic: Structural Design Patterns
### Course: Software Modeling & Design Techniques (aka TMPS)
### Author: Mihaela Untu | FAF-232

---

## Objectives

* Get familiar with the Structural Design Patterns (SDPs).
* Continue the previous laboratory work on **Tutor Lessons Booking**.
* Implement at least 3 SDPs for the domain: **Decorator, Facade, Adapter**.

---

## Introduction

Structural Design Patterns are all about how we organize and compose our classes and objects to form larger, more flexible structures. These patterns focus on simplifying the design by identifying simple ways to realize relationships between entities. After implementing creational patterns in lab2, I needed to think about how to make my system more maintainable and easier to use.

There are seven main structural design patterns:

- **Adapter** - Allows objects with incompatible interfaces to collaborate. It acts as a wrapper between two objects, catching calls for one object and transforming them to a format and interface recognizable by the second object. This is like a power adapter that lets you plug your device into a foreign outlet.

- **Bridge** - Separates an object's abstraction from its implementation so that the two can vary independently. This pattern is useful when both the class and what it does vary often. It helps avoid a permanent binding between an abstraction and its implementation.

- **Composite** - Composes objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly. This is particularly useful when you need to work with tree-like structures.

- **Decorator** - Attaches new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors. It provides a flexible alternative to subclassing for extending functionality, allowing you to add responsibilities to objects dynamically.

- **Facade** - Provides a unified interface to a set of interfaces in a subsystem. It defines a higher-level interface that makes the subsystem easier to use by wrapping a complicated subsystem with a simpler interface.

- **Flyweight** - Minimizes memory usage by sharing as much data as possible with similar objects. It's particularly useful when you need to create a large number of similar objects, storing common data shared between objects externally.

- **Proxy** - Provides a surrogate or placeholder for another object to control access to it. It can add additional functionality when accessing an object, such as lazy initialization, access control, logging, etc.

For this laboratory work, I focused on three structural patterns that solved real problems in my tutor booking system:

- **Decorator** - I needed a way to add features to lessons (like premium support, recording, materials) without creating tons of subclasses. The Decorator pattern lets me wrap lessons with additional functionality dynamically.

- **Facade** - My booking process was getting complicated, involving multiple steps across different classes. The Facade pattern helped me create a simple interface that hides all that complexity from the client.

- **Adapter** - I wanted to integrate an external payment service, but its interface didn't match what my system expected. The Adapter pattern acts as a translator between the two incompatible interfaces.

---

## Used Design Patterns

For this laboratory work, I implemented three structural design patterns:

* **Decorator Pattern** – I use this to dynamically add features to lessons without changing the base lesson classes. Students can get basic lessons, or add premium support, recording capabilities, and study materials in any combination.

* **Facade Pattern** – This simplifies the booking process for clients. Instead of manually creating tutors, lessons, getting the booking manager, and teaching the lesson (5+ steps), the facade handles everything in one simple method call.

* **Adapter Pattern** – This allows me to integrate an external payment service that has a completely different interface than what my system expects, making them work together seamlessly.

---

## Implementation

### Overview

I extended my tutor lesson booking system from lab2 by adding structural patterns on top of the existing creational patterns. The goal was to make the system more flexible and easier to use while maintaining all the functionality from before.

Here's what I did:
1. Implemented the **Decorator pattern** to add optional features to lessons
2. Created a **Facade** to simplify the booking workflow
3. Built an **Adapter** to integrate with an external payment API

All of this works together with my previous patterns: Builder (for Tutors), Singleton (for BookingManager), and Factory Method (for Lessons).

---

### 1. Decorator Pattern

The first problem I tackled was how to add features to lessons. Initially, I thought about creating subclasses like `PremiumMathLesson`, `RecordedMathLesson`, `PremiumRecordedMathLesson`, etc. But this would lead to a huge number of classes! 

The Decorator pattern solved this elegantly. I created a base decorator that wraps any lesson, and then specific decorators for each feature.

**LessonDecorator.java (Base Decorator)**
*Location: `lab3/domain/decorators/LessonDecorator.java`*

```java
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
```

This is my base decorator. The key insight here is that it implements `Lesson` (so it can be used anywhere a lesson is expected) and also HAS a `Lesson` (the wrapped lesson). This is the foundation that makes everything work.

**PremiumLessonDecorator.java (Concrete Decorator)**
*Location: `lab3/domain/decorators/PremiumLessonDecorator.java`*

```java
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
```

The `PremiumLessonDecorator` adds functionality before and after calling the wrapped lesson's `teach()` method. I also created `RecordedLessonDecorator` and `MaterialsLessonDecorator` following the same pattern.

The beauty of this approach is that I can stack decorators:

```java
Lesson completeLesson = LessonFactory.createLesson("math");
completeLesson = new PremiumLessonDecorator(completeLesson);
completeLesson = new RecordedLessonDecorator(completeLesson);
completeLesson = new MaterialsLessonDecorator(completeLesson);
completeLesson.teach();  // Now has ALL features!
```

Each decorator wraps the previous one, creating a chain of functionality. No need for separate classes for every combination!

---

### 2. Facade Pattern

After implementing all these patterns, I realized the client code was becoming too complex. To book a lesson, you had to:
1. Use the Builder to create a Tutor
2. Use the Factory to create a Lesson
3. Get the Singleton BookingManager instance
4. Apply Decorators if you want features
5. Book the lesson
6. Teach the lesson

That's a lot of steps! So I created a Facade to simplify this.

**BookingFacade.java**
*Location: `lab3/domain/facade/BookingFacade.java`*

```java
public class BookingFacade {
    private BookingManager bookingManager;
    
    public BookingFacade() {
        this.bookingManager = BookingManager.getInstance();
    }

    public void quickBook(String lessonType, String tutorName, String subject, int experience) {
        System.out.println("\n--- Quick Booking via Facade ---");

        Tutor tutor = new Tutor.Builder()
                .setName(tutorName)
                .setSubject(subject)
                .setExperience(experience)
                .build();
    
        Lesson lesson = LessonFactory.createLesson(lessonType);
        bookingManager.bookLesson(tutor, lesson);
        lesson.teach();
    }

    public void bookCompletePackage(String lessonType, String tutorName, String subject, int experience) {
        System.out.println("\n--- Complete Package Booking via Facade ---");

        Tutor tutor = new Tutor.Builder()
                .setName(tutorName)
                .setSubject(subject)
                .setExperience(experience)
                .build();
        
        Lesson lesson = LessonFactory.createLesson(lessonType);
        lesson = new PremiumLessonDecorator(lesson);
        lesson = new RecordedLessonDecorator(lesson);
        lesson = new MaterialsLessonDecorator(lesson);
        
        bookingManager.bookLesson(tutor, lesson);
        lesson.teach();
    }
}
```

Now instead of the client doing all those steps, they just call:
```java
facade.bookCompletePackage("math", "Sarah", "Mathematics", 7);
```

Much simpler! The facade handles all the complexity internally. I created different methods for different use cases: `quickBook()` for basic lessons, `bookPremiumLesson()` for premium features, and `bookCompletePackage()` for everything.

---

### 3. Adapter Pattern

The last challenge was integrating a payment system. I wanted to add payment processing, but the external payment service I found has a completely different interface than what I designed.

**My interface (PaymentProcessor.java):**
*Location: `lab3/domain/payment/PaymentProcessor.java`*
```java
public interface PaymentProcessor {
    void processPayment(double amount);
    boolean validatePayment(String transactionId);
}
```

**External service interface (ExternalPaymentService.java):**
*Location: `lab3/domain/payment/ExternalPaymentService.java`*
```java
public String makeTransaction(String fromAccount, String toAccount, double amount, String currency)
public boolean checkTransactionStatus(String transactionId)
```

See the problem? My system expects `processPayment(amount)` but the external service needs `makeTransaction(from, to, amount, currency)`. They're incompatible!

**PaymentAdapter.java**
*Location: `lab3/domain/payment/PaymentAdapter.java`*

```java
public class PaymentAdapter implements PaymentProcessor{
    private ExternalPaymentService externalService;
    private String lastTransactionId;
    private static final String SYSTEM_ACCOUNT = "TUTOR_SYSTEM_ACCOUNT";
    private static final String CURRENCY = "USD";

    public PaymentAdapter(ExternalPaymentService externalService) {
        this.externalService = externalService;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("\n💳 Processing payment through adapter...");
        lastTransactionId = externalService.makeTransaction(
            "STUDENT_ACCOUNT",   // from - I provide this
            SYSTEM_ACCOUNT,      // to - my system account
            amount,              // value - from the parameter
            CURRENCY             // currency - I set USD as default
        );
        
        System.out.println("💳 Adapter: Payment processed. Transaction ID: " + lastTransactionId);
    }

    @Override
    public boolean validatePayment(String transactionId) {
        System.out.println("\n🔍 Validating payment through adapter...");
        
        boolean status = externalService.checkTransactionStatus(transactionId);
        System.out.println("🔍 Adapter: Validation " + (status ? "successful" : "failed"));

        return status;
    }
}
```

The adapter acts as a translator! It implements my `PaymentProcessor` interface but internally uses the `ExternalPaymentService`. When someone calls `processPayment(50.00)`, the adapter translates it to `makeTransaction("STUDENT_ACCOUNT", "TUTOR_SYSTEM_ACCOUNT", 50.00, "USD")`, filling in the missing parameters with sensible defaults.

This way, the rest of my system doesn't need to know anything about the external service's interface. If I want to switch to a different payment provider later, I just need to create a new adapter!

---

### 4. Client Code

**Main.java**
*Location: `lab3/client/Main.java`*

```java
public static void main(String[] args) {
    System.out.println("===============================================");
    System.out.println("  Lab 3: Structural Design Patterns Demo");
    System.out.println("===============================================\n");
    
    demonstrateDecorator();
    demonstrateFacade();
    demonstrateAdapter();
    
    System.out.println("===============================================");
    System.out.println("  All patterns tested successfully!");
    System.out.println("===============================================");
}
```

I organized my Main class to clearly demonstrate each pattern separately. This makes it easy to understand what each pattern does and see the benefits.

---

## Results / Screenshots

### Expected Console Output

```
===============================================
  Lab 3: Structural Design Patterns Demo
===============================================

1. DECORATOR PATTERN
   Adding features to lessons without changing the classes

Basic lesson:
Teaching Math lesson...

Lesson with Premium feature:
[Premium Feature] 🌟 Starting one-on-one session with priority support...
Teaching Programming lesson...
[Premium Feature] ✨ Extended Q&A time included!

Lesson with Recording feature:
[Recording] 🔴 Recording started...
Teaching English lesson...
[Recording] ✅ Session recorded. Link: https://recordings.tutorsystem.com/session123

Lesson with ALL features (Premium + Recording + Materials):
[Materials] 📚 Providing study materials (notes.pdf, exercises.pdf)...
[Premium Feature] 🌟 Starting one-on-one session with priority support...
[Recording] 🔴 Recording started...
Teaching Math lesson...
[Recording] ✅ Session recorded. Link: https://recordings.tutorsystem.com/session123
[Premium Feature] ✨ Extended Q&A time included!
[Materials] 📥 Materials available for download in your dashboard!


2. FACADE PATTERN
   Simplifying complex booking operations

Without facade, booking needs:
  - Create tutor (Builder)
  - Create lesson (Factory)
  - Get booking manager (Singleton)
  - Book the lesson
  - Teach the lesson
  That's 5 different steps!

With facade - just one method call:

--- Quick Booking via Facade ---
Booking MathLesson with Sarah who teaches Mathematics (7 years)
Teaching Math lesson...

--- Premium Booking via Facade ---
Booking ProgrammingLesson with Alex who teaches Java Programming (10 years)
[Premium Feature] 🌟 Starting one-on-one session with priority support...
Teaching Programming lesson...
[Premium Feature] ✨ Extended Q&A time included!

--- Complete Package Booking via Facade ---
Booking EnglishLesson with Emma who teaches English Literature (12 years)
[Materials] 📚 Providing study materials (notes.pdf, exercises.pdf)...
[Premium Feature] 🌟 Starting one-on-one session with priority support...
[Recording] 🔴 Recording started...
Teaching English lesson...
[Recording] ✅ Session recorded. Link: https://recordings.tutorsystem.com/session123
[Premium Feature] ✨ Extended Q&A time included!
[Materials] 📥 Materials available for download in your dashboard!


3. ADAPTER PATTERN
   Integrating external payment system

The problem:
  Our system expects: processPayment(amount)
  External API uses: makeTransaction(from, to, value, currency)
  These interfaces don't match!

The solution: Use an adapter

Using our simple interface:

💳 Processing payment through adapter...
🌐 External Payment Service:
   From: STUDENT_ACCOUNT
   To: TUTOR_SYSTEM_ACCOUNT
   Amount: 50.0 USD
   Status: ✅ Transaction successful
💳 Adapter: Payment processed. Transaction ID: TXN1699632845123

🔍 Validating payment through adapter...
🔍 Checking transaction TXN1699632845123: COMPLETED
🔍 Adapter: Validation successful

Adapter successfully translated between the interfaces!

===============================================
  All patterns tested successfully!
===============================================
```

---

## Conclusions

In this laboratory work, I successfully implemented three structural design patterns that significantly improved my tutor booking system. Here's what I learned and achieved:

**What I Implemented:**
- **Decorator Pattern** to dynamically add features (Premium support, Recording, Study materials) to lessons without creating separate subclasses for every combination
- **Facade Pattern** to simplify the booking workflow from 5+ separate steps into single, convenient method calls
- **Adapter Pattern** to integrate an external payment service with an incompatible interface

This lab helped me understand that good software design isn't just about making code work—it's about making it flexible, maintainable, and easy to extend. The structural patterns I implemented here are used extensively in real production systems, and now I understand why they're so valuable.
