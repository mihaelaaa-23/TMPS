package lab4.domain.strategy;

// Strategy interface - defines pricing algorithm
public interface PricingStrategy {
    double calculatePrice(double basePrice, int numberOfLessons);
    String getStrategyName();
    String getDescription();
}
