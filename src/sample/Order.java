package sample;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private Product product;
    private int amount;
    private int totalPrice;

    public Order(int id, Date date, Product product, int amount, int totalPrice) {
        this.id = id;
        this.date = date;
        this.product = product;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Order() {
        id=0;
        date=null;
        product=null;
        amount=0;
        totalPrice=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
