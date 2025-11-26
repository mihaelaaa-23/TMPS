package lab4.domain.strategy;

// Concrete Strategy - Bulk discount for multiple lessons
public class BulkDiscountStrategy implements PricingStrategy {
    
    @Override
    public double calculatePrice(double basePrice, int numberOfLessons) {
        double total = basePrice * numberOfLessons;
        
        // Apply tiered discounts
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
