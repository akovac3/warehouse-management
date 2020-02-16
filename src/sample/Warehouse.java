package sample;

import java.util.ArrayList;

public class Warehouse {
    private String managerName;
    private String address;
    private ArrayList<Location> locations;
    private ArrayList<Product> products;
    private String mark;

    public Warehouse(String managerName, String address, ArrayList<Location> locations, ArrayList<Product> products, String mark) {
        this.managerName = managerName;
        this.address = address;
        this.locations = locations;
        this.products = products;
        this.mark = mark;
    }

    public Warehouse() {
        managerName=null;
        address=null;
        locations=null;
        products=null;
        mark=null;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
