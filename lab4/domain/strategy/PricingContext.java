package lab4.domain.strategy;

// Context class - uses a pricing strategy
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
    
    public PricingStrategy getStrategy() {
        return strategy;
    }
}
