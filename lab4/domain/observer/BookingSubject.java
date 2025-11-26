package lab4.domain.observer;

import java.util.ArrayList;
import java.util.List;

// Subject interface - maintains observers and notifies them
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
