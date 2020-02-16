package sample;

public class Product {
    private String name;
    private int amount;
    private Location location;
    private int importPrice;
    private int exportPrice;
    private String code;

    public Product(String name, int amount, Location location, int importPrice, int exportPrice, String code) {
        this.name = name;
        this.amount = amount;
        this.location = location;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.code = code;
    }

    public Product() {
        name=null;
        amount=0;
        location=null;
        importPrice=0;
        exportPrice=0;
        code=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(int importPrice) {
        this.importPrice = importPrice;
    }

    public int getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(int exportPrice) {
        this.exportPrice = exportPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
