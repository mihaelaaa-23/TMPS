package lab4.domain.strategy;

// Context class - uses a pricing strategy
public class PricingContext {
    private PricingStrategy strategy;
    
    public PricingContext(PricingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
        System.out.println("\n💵 Pricing strategy changed to: " + strategy.getStrategyName());
        System.out.println("   Description: " + strategy.getDescription());
    }
    
    public double calculateTotalPrice(double basePrice, int numberOfLessons) {
        System.out.println("\n💵 Calculating price using: " + strategy.getStrategyName());
        System.out.println("   Base price per lesson: $" + basePrice);
        System.out.println("   Number of lessons: " + numberOfLessons);
        
        double totalPrice = strategy.calculatePrice(basePrice, numberOfLessons);
        
        System.out.println("   ✅ Total price: $" + String.format("%.2f", totalPrice));
        return totalPrice;
    }
    
    public PricingStrategy getStrategy() {
        return strategy;
    }
}
