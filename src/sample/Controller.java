package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller {
    private Warehouse warehouse=null;
    public TableView<Product> tableViewProducts;
    //public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAmount;
    public TableColumn colImportPrice;
    public TableColumn colExportPrice;
    public TableColumn colCode;
    public TableColumn colLocation;
    private WarehouseDAO dao;
    private ObservableList<Product> listProducts;

    public Controller() {
        dao = WarehouseDAO.getInstance();
        listProducts = FXCollections.observableArrayList(dao.products());
    }

    @FXML
    public void initialize() {
        tableViewProducts.setItems(listProducts);
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAmount.setCellValueFactory(new  PropertyValueFactory("amount"));
        colImportPrice.setCellValueFactory(new PropertyValueFactory("importPrice"));
        colExportPrice.setCellValueFactory(new PropertyValueFactory("exportPrice"));
        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colLocation.setCellValueFactory(new PropertyValueFactory("location"));
    }

    public void exitAction(ActionEvent actionEvent){
        System.exit(0);
    }

    public void aboutAction(ActionEvent actionEvent){
        if(warehouse==null) return;
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            AboutController ctrl = new AboutController(warehouse);
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("About");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addAction(ActionEvent actionEvent){

    }
    public void moveAction(ActionEvent actionEvent){

    }

    public void chooseAction(ActionEvent actionEvent){
        List<String> choices = new ArrayList<>();
        choices.add("A");
        choices.add("B");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("A", choices);
        dialog.setTitle("Choose warehouse");
        dialog.setHeaderText("Choose warehouse:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> warehouse=dao.getWarehouseByMark(letter));
    }
}
