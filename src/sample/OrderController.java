package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class OrderController {
    public ChoiceBox<Product> productChoice;
    private ObservableList<Product> listProducts;
    private Product product;
    public TextField fieldAmount;
    private SimpleStringProperty price;
    private Order order;
    public DatePicker date;

    public OrderController(ArrayList<Product> products) {
        listProducts = FXCollections.observableArrayList(products);
        price = new SimpleStringProperty("");
        order=null;
        date = null;
    }

    @FXML
    public void initialize() {
        productChoice.setItems(listProducts);
    }

    public Product getProduct() {
        return product;
    }

    public void okAction(ActionEvent actionEvent) {
        boolean ok = true;
        int amount = 0;

        try {
            amount = Integer.parseInt(fieldAmount.getText());
        }
        catch (NumberFormatException e){
            //...
        }
        if(amount <=0 ){
            fieldAmount.getStyleClass().removeAll("fieldCorrect");
            fieldAmount.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else {
            fieldAmount.getStyleClass().removeAll("fieldNotCorrect");
            fieldAmount.getStyleClass().add("fieldCorrect");
        }

        if(ok && productChoice.getValue()!=null && date.getValue()!=null){
            product = productChoice.getValue();
            price.set(String.valueOf(amount*product.getExportPrice()));

            order = new Order();
            order.setAmount(amount);
            order.setProduct(product);
            order.setDate(date.getValue());
            order.setTotalPrice(amount*product.getExportPrice());
        }
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public Order getOrder(){
        return order;
    }
}
