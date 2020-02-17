package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddController {
    public TextField fieldName;
    public TextField fieldAmount;
    public ChoiceBox<Warehouse> choiceWarehouse;
    public TextField fieldImport;
    public TextField fieldExport;
    public TextField fieldSection;
    public TextField fieldPosition;
    public TextField fieldCode;
    private ObservableList<Warehouse> listWareHouse;
    private Product product;

    public AddController(ArrayList<Warehouse> list){
        listWareHouse = FXCollections.observableArrayList(list);
    }

    @FXML
    public void initialize() {
        choiceWarehouse.setItems(listWareHouse);
    }

    public void okAction(){
        boolean ok=true;
        if(fieldName.getText().trim().isEmpty()){
            fieldName.getStyleClass().removeAll("fieldCorrect");
            fieldName.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else {
            fieldName.getStyleClass().removeAll("fieldNotCorrect");
            fieldName.getStyleClass().add("fieldCorrect");
        }

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

        int importPrice = 0;

        try{
        importPrice = Integer.parseInt(fieldImport.getText());
        }
        catch (NumberFormatException e){
            //...
        }

        if(importPrice<=0){
            fieldImport.getStyleClass().removeAll("fieldCorrect");
            fieldImport.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else{
            fieldImport.getStyleClass().removeAll("fieldNotCorrect");
            fieldImport.getStyleClass().add("fieldCorrect");
        }

        int exportPrice = 0;

        try{
        exportPrice = Integer.parseInt(fieldExport.getText());
        }
        catch (NumberFormatException e){
            //...
        }
        if(exportPrice <= 0){
            fieldExport.getStyleClass().removeAll("fieldCorrect");
            fieldExport.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else{
            fieldExport.getStyleClass().removeAll("fieldNotCorrect");
            fieldExport.getStyleClass().add("fieldCorrect");
        }

        if(fieldCode.getText().trim().isEmpty() || fieldCode.getText().charAt(0)!='#'){
            fieldCode.getStyleClass().removeAll("fieldCorrect");
            fieldCode.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else{
            fieldCode.getStyleClass().removeAll("fieldNotCorrect");
            fieldCode.getStyleClass().add("fieldCorrect");
        }

        int locationSection = 0;
        try{
        locationSection = Integer.parseInt(fieldSection.getText());
        }
        catch (NumberFormatException e){
            //...
        }
        if(locationSection<=0 || locationSection%10!=0) {
            fieldSection.getStyleClass().removeAll("fieldCorrect");
            fieldSection.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else{
            fieldSection.getStyleClass().removeAll("fieldNotCorrect");
            fieldSection.getStyleClass().add("fieldCorrect");
        }

        int locationPosition = 0;

        try{
        locationPosition = Integer.parseInt(fieldPosition.getText());
        }
        catch (NumberFormatException e){
            //...
        }
        if(locationPosition<=0) {
            fieldPosition.getStyleClass().removeAll("fieldCorrect");
            fieldPosition.getStyleClass().add("fieldNotCorrect");
            ok=false;
        } else{
            fieldPosition.getStyleClass().removeAll("fieldNotCorrect");
            fieldPosition.getStyleClass().add("fieldCorrect");
        }

        if(ok){
            product = new Product();
            product.setName(fieldName.getText());
            product.setAmount(Integer.parseInt(fieldAmount.getText()));
            product.setCode(fieldCode.getText());
            product.setExportPrice(Integer.parseInt(fieldExport.getText()));
            product.setImportPrice(Integer.parseInt(fieldImport.getText()));
            product.setWarehouse(choiceWarehouse.getValue());
            try {
                product.setLocation(new Location(Integer.parseInt(fieldSection.getText()), Integer.parseInt(fieldPosition.getText())));
            } catch (IllegalLocationException e) {
                e.printStackTrace();
            }
        }


    }

    public Product getProduct(){
        return product;
    }

    public void exitAction(ActionEvent actionEvent){
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
