package lab4.domain.strategy;

// Concrete Strategy - Standard pricing with no discounts
public class StandardPricingStrategy implements PricingStrategy {
    
    @Override
    public double calculatePrice(double basePrice, int numberOfLessons) {
        return basePrice * numberOfLessons;
    }
    
    @Override
    public String getStrategyName() {
        return "Standard Pricing";
    }
    
    @Override
    public String getDescription() {
        return "Regular price with no discounts";
    }
}
