package lab4.domain.observer;

// Observer interface - all observers must implement this
public interface BookingObserver {
    void update(String eventType, String message);
}
