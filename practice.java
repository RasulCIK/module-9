
interface IReport {
    String generate();
}

class SalesReport implements IReport {
    public String generate() {
        return "Sales Report Data: [Sample Sales Data]";
    }
}

class UserReport implements IReport {
    public String generate() {
        return "User Report Data: [Sample User Data]";
    }
}

abstract class ReportDecorator implements IReport {
    protected IReport report;

    public ReportDecorator(IReport report) {
        this.report = report;
    }

    public String generate() {
        return report.generate();
    }
}

class DateFilterDecorator extends ReportDecorator {
    private String startDate;
    private String endDate;

    public DateFilterDecorator(IReport report, String startDate, String endDate) {
        super(report);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String generate() {
        return super.generate() + "\nFiltered by Date Range: " + startDate + " to " + endDate;
    }
}

class SortingDecorator extends ReportDecorator {
    private String criteria;

    public SortingDecorator(IReport report, String criteria) {
        super(report);
        this.criteria = criteria;
    }

    @Override
    public String generate() {
        return super.generate() + "\nSorted by: " + criteria;
    }
}

class CsvExportDecorator extends ReportDecorator {
    public CsvExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return super.generate() + "\nExported as CSV format.";
    }
}

class PdfExportDecorator extends ReportDecorator {
    public PdfExportDecorator(IReport report) {
        super(report);
    }

    @Override
    public String generate() {
        return super.generate() + "\nExported as PDF format.";
    }
}

class ReportClient {
    public static void main(String[] args) {
        IReport salesReport = new SalesReport();
        IReport userReport = new UserReport();

        IReport decoratedSalesReport = new DateFilterDecorator(
                new SortingDecorator(
                        new CsvExportDecorator(salesReport), "date"), "2024-01-01", "2024-01-31");

        System.out.println("--- Decorated Sales Report ---");
        System.out.println(decoratedSalesReport.generate());

        IReport decoratedUserReport = new PdfExportDecorator(
                new DateFilterDecorator(userReport, "2024-02-01", "2024-02-28"));

        System.out.println("\n--- Decorated User Report ---");
        System.out.println(decoratedUserReport.generate());
    }
}

interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
}

class InternalDeliveryService implements IInternalDeliveryService {
    public void deliverOrder(String orderId) {
        System.out.println("Order " + orderId + " is being delivered by the internal service.");
    }

    public String getDeliveryStatus(String orderId) {
        return "Status of order " + orderId + ": In Progress";
    }
}

class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("Item " + itemId + " shipped by External Logistics Service A.");
    }

    public String trackShipment(int shipmentId) {
        return "Tracking info for shipment " + shipmentId + ": In Transit";
    }
}

class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA externalServiceA;

    public LogisticsAdapterA(ExternalLogisticsServiceA externalServiceA) {
        this.externalServiceA = externalServiceA;
    }

    @Override
    public void deliverOrder(String orderId) {
        int itemId = Integer.parseInt(orderId);
        externalServiceA.shipItem(itemId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        int shipmentId = Integer.parseInt(orderId);
        return externalServiceA.trackShipment(shipmentId);
    }
}

class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("Package " + packageInfo + " sent by External Logistics Service B.");
    }

    public String checkPackageStatus(String trackingCode) {
        return "Status for package with tracking code " + trackingCode + ": Delivered";
    }
}

class LogisticsAdapterB implements IInternalDeliveryService {
    private ExternalLogisticsServiceB externalServiceB;

    public LogisticsAdapterB(ExternalLogisticsServiceB externalServiceB) {
        this.externalServiceB = externalServiceB;
    }

    @Override
    public void deliverOrder(String orderId) {
        externalServiceB.sendPackage(orderId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return externalServiceB.checkPackageStatus(orderId);
    }
}

class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String type) {
        switch (type.toLowerCase()) {
            case "internal":
                return new InternalDeliveryService();
            case "externala":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "externalb":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            default:
                throw new IllegalArgumentException("Unknown delivery service type: " + type);
        }
    }
}

class DeliveryClient {
    public static void main(String[] args) {
        IInternalDeliveryService service = DeliveryServiceFactory.getDeliveryService("externala");
        service.deliverOrder("123");
        System.out.println(service.getDeliveryStatus("123"));

        IInternalDeliveryService anotherService = DeliveryServiceFactory.getDeliveryService("externalb");
        anotherService.deliverOrder("PKG-456");
        System.out.println(anotherService.getDeliveryStatus("PKG-456"));
    }
}
