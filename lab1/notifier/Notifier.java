package notifier;
// Notifier and BookingManager interface demonstrate Dependency Inversion Principle - 
// High-level modules should not depend on low-level modules. Both should depend on abstractions.
// Abstractions should not depend on details. Details should depend on abstractions.
public interface Notifier {
    void send(String message);
}