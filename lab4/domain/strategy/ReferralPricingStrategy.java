package lab4.domain.strategy;

// Concrete Strategy - Referral pricing for students who bring friends
public class ReferralPricingStrategy implements PricingStrategy {
    private int referralsCount;
    
    public ReferralPricingStrategy(int referralsCount) {
        this.referralsCount = referralsCount;
    }
    
    @Override
    public double calculatePrice(double basePrice, int numberOfLessons) {
        double total = basePrice * numberOfLessons;
        
        // Each referral gives 5% discount, max 30%
        double discountPercentage = Math.min(referralsCount * 0.05, 0.30);
        
        if (discountPercentage > 0) {
            System.out.println("      " + (int)(discountPercentage * 100) + 
                             "% referral discount (" + referralsCount + " referrals)");
            return total * (1 - discountPercentage);
        }
        
        return total;
    }
    
    @Override
    public String getStrategyName() {
        return "Referral Pricing";
    }
    
    @Override
    public String getDescription() {
        return "5% off per referral (max 30%) - currently " + referralsCount + " referral(s)";
    }
}
