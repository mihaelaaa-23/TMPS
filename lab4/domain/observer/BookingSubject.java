package lab4.domain.observer;

import java.util.ArrayList;
import java.util.List;

// Subject interface - maintains observers and notifies them
public class BookingSubject {
    private List<BookingObserver> observers = new ArrayList<>();
    
    public void attach(BookingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("➕ Observer attached: " + observer.getClass().getSimpleName());
        }
    }
    
    public void detach(BookingObserver observer) {
        observers.remove(observer);
        System.out.println("➖ Observer detached: " + observer.getClass().getSimpleName());
    }
    
    public void notifyObservers(String eventType, String message) {
        System.out.println("\n📢 Broadcasting " + eventType + " event to " + observers.size() + " observer(s)...");
        for (BookingObserver observer : observers) {
            observer.update(eventType, message);
        }
    }
}
