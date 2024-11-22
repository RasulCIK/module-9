
 interface IBeverage {
    double getCost();
    String getDescription();
}

class Coffee implements IBeverage {
    public double getCost() {
        return 50.0;
    }
    public String getDescription() {
        return "Coffee";
    }
}

 abstract class BeverageDecorator implements IBeverage {
    protected IBeverage beverage;
    public BeverageDecorator(IBeverage beverage) {
        this.beverage = beverage;
    }
    public double getCost() {
        return beverage.getCost();
    }
    public String getDescription() {
        return beverage.getDescription();
    }
}

 class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(IBeverage beverage) {
        super(beverage);
    }
    public double getCost() {
        return super.getCost() + 10.0;
    }
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}

 class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(IBeverage beverage) {
        super(beverage);
    }
    public double getCost() {
        return super.getCost() + 5.0;
    }
    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }
}

class ChocolateDecorator extends BeverageDecorator {
    public ChocolateDecorator(IBeverage beverage) {
        super(beverage);
    }
    public double getCost() {
        return super.getCost() + 15.0;
    }
    public String getDescription() {
        return super.getDescription() + ", Chocolate";
    }
}

class BeverageClient {
    public static void main(String[] args) {
        IBeverage beverage = new Coffee();
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        beverage = new MilkDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        beverage = new SugarDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());

        beverage = new ChocolateDecorator(beverage);
        System.out.println(beverage.getDescription() + " : " + beverage.getCost());
    }
}

 interface IPaymentProcessor {
    void processPayment(double amount);
    void refundPayment(double amount);
}

 class InternalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Processing payment of " + amount + " via internal system.");
    }
    public void refundPayment(double amount) {
        System.out.println("Refunding payment of " + amount + " via internal system.");
    }
}

 class ExternalPaymentSystemA {
    public void makePayment(double amount) {
        System.out.println("Making payment of " + amount + " via External Payment System A.");
    }
    public void makeRefund(double amount) {
        System.out.println("Making refund of " + amount + " via External Payment System A.");
    }
}

 class PaymentAdapterA implements IPaymentProcessor {
    private ExternalPaymentSystemA externalSystemA;
    public PaymentAdapterA(ExternalPaymentSystemA externalSystemA) {
        this.externalSystemA = externalSystemA;
    }
    public void processPayment(double amount) {
        externalSystemA.makePayment(amount);
    }
    public void refundPayment(double amount) {
        externalSystemA.makeRefund(amount);
    }
}

 class ExternalPaymentSystemB {
    public void sendPayment(double amount) {
        System.out.println("Sending payment of " + amount + " via External Payment System B.");
    }
    public void processRefund(double amount) {
        System.out.println("Processing refund of " + amount + " via External Payment System B.");
    }
}

 class PaymentAdapterB implements IPaymentProcessor {
    private ExternalPaymentSystemB externalSystemB;
    public PaymentAdapterB(ExternalPaymentSystemB externalSystemB) {
        this.externalSystemB = externalSystemB;
    }
    public void processPayment(double amount) {
        externalSystemB.sendPayment(amount);
    }
    public void refundPayment(double amount) {
        externalSystemB.processRefund(amount);
    }
}

 class PaymentProcessorFactory {
    public static IPaymentProcessor getPaymentProcessor(String type) {
        switch (type.toLowerCase()) {
            case "internal":
                return new InternalPaymentProcessor();
            case "externala":
                return new PaymentAdapterA(new ExternalPaymentSystemA());
            case "externalb":
                return new PaymentAdapterB(new ExternalPaymentSystemB());
            default:
                throw new IllegalArgumentException("Unknown payment processor type: " + type);
        }
    }
}

 class PaymentClient {
    public static void main(String[] args) {
        IPaymentProcessor internalProcessor = PaymentProcessorFactory.getPaymentProcessor("internal");
        internalProcessor.processPayment(100.0);
        internalProcessor.refundPayment(50.0);

        IPaymentProcessor adapterA = PaymentProcessorFactory.getPaymentProcessor("externala");
        adapterA.processPayment(200.0);
        adapterA.refundPayment(100.0);

        IPaymentProcessor adapterB = PaymentProcessorFactory.getPaymentProcessor("externalb");
        adapterB.processPayment(300.0);
        adapterB.refundPayment(150.0);
    }
}
