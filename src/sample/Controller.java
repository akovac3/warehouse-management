package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
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
}
