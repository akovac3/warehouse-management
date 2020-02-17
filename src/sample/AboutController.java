package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class AboutController {
    private SimpleStringProperty managerName;
    private SimpleStringProperty address;
    private SimpleStringProperty mark;
    private Warehouse warehouse ;
    public ImageView imgView;
    public AboutController(Warehouse warehouse) {
        managerName= new SimpleStringProperty("");
        address = new SimpleStringProperty("");
        mark = new SimpleStringProperty("");
        this.warehouse = warehouse;
    }

    @FXML
    public void initialize(){
        managerName.set("Manager: " + warehouse.getManagerName());
        address.set("Address: " + warehouse.getAddress());
        mark.set("Warehouse " + warehouse.getMark());
        File file = new File(warehouse.getImage());
        imgView.setImage(new Image(file.toURI().toString()));
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

    public String getMark() {
        return mark.get();
    }

    public SimpleStringProperty markProperty() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark.set(mark);
    }

    public void exitAction(ActionEvent actionEvent){
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
