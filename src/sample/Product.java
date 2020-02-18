package sample;

public class Product {
    private int id;
    private String name;
    private int amount;
    private int importPrice;
    private int exportPrice;
    private String code;
    private Warehouse warehouse;
    private Location location;

    public Product(int id, String name, int amount, int importPrice, int exportPrice, String code, Warehouse warehouse, Location location) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.code = code;
        this.warehouse = warehouse;
        this.location = location;
    }

    public Product() {
        id=0;
        warehouse = null;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
