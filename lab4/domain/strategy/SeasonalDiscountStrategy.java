package lab4.domain.strategy;

// Concrete Strategy - Seasonal discounts (summer/winter promotions)
public class SeasonalDiscountStrategy implements PricingStrategy {
    private String season;
    private double discountPercentage;
    
    public SeasonalDiscountStrategy(String season, double discountPercentage) {
        this.season = season;
        this.discountPercentage = discountPercentage;
    }
    
    @Override
    public double calculatePrice(double basePrice, int numberOfLessons) {
        double total = basePrice * numberOfLessons;
        System.out.println("      " + (int)(discountPercentage * 100) + "% " + season + " discount applied");
        return total * (1 - discountPercentage);
    }
    
    @Override
    public String getStrategyName() {
        return season + " Seasonal Pricing";
    }
    
    @Override
    public String getDescription() {
        return "Special " + season + " discount: " + (discountPercentage * 100) + "% off";
    }
}
