package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class AboutController {
    private SimpleStringProperty managerName;
    private SimpleStringProperty address;
    private Warehouse warehouse ;
    public AboutController(Warehouse warehouse) {
        managerName= new SimpleStringProperty("");
        address = new SimpleStringProperty("");
        this.warehouse = warehouse;
    }

    @FXML
    public void initialize(){
        managerName.set(warehouse.getManagerName());
        address.set(warehouse.getAddress());
    }

    public String getManagerName() {
        return managerName.get();
    }

    public SimpleStringProperty managerNameProperty() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName.set(managerName);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


}
