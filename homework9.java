interface Beverage {
    String getDescription();
    double cost();
}

class Espresso implements Beverage {
    public String getDescription() {
        return "Espresso";
    }

    public double cost() {
        return 2.0;
    }
}

class Tea implements Beverage {
    public String getDescription() {
        return "Tea";
    }

    public double cost() {
        return 1.5;
    }
}

abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription();
    }

    public double cost() {
        return beverage.cost();
    }
}

class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.5;
    }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.2;
    }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.7;
    }
}

 class CafeOrder {
    public static void main(String[] args) {
        Beverage beverage1 = new Milk(new Sugar(new Espresso()));
        System.out.println(beverage1.getDescription() + " costs $" + beverage1.cost());

        Beverage beverage2 = new WhippedCream(new Tea());
        System.out.println(beverage2.getDescription() + " costs $" + beverage2.cost());

        Beverage beverage3 = new WhippedCream(new Milk(new Sugar(new Espresso())));
        System.out.println(beverage3.getDescription() + " costs $" + beverage3.cost());
    }
}

interface IPaymentProcessor {
    void processPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}

class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Processing Stripe payment of $" + totalAmount);
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;

    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = stripeService;
    }

    @Override
    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

 class PaymentClient {
    public static void main(String[] args) {
        IPaymentProcessor paypalProcessor = new PayPalPaymentProcessor();
        paypalProcessor.processPayment(50.0);

        IPaymentProcessor stripeAdapter = new StripePaymentAdapter(new StripePaymentService());
        stripeAdapter.processPayment(75.0);
    }
}
