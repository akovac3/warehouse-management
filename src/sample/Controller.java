package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller {
    private Warehouse warehouse=null;
    public TableView<Product> tableViewProducts;
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
        listProducts = FXCollections.observableArrayList(dao.products(null));
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

        tableViewProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldProduct, newProduct) -> {
            dao.setCurrentProduct(newProduct);
        });

        dao.currentProductProperty().addListener((obs, oldProduct, newProduct) -> {

        });
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
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add.fxml"));
            AddController ctrl = new AddController(dao.getListWarehouse());
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("About");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Product product = ctrl.getProduct();
                if(product!=null) {
                    dao.addProduct(product);
                    listProducts.setAll(dao.products(warehouse));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveAction(ActionEvent actionEvent){
        if(dao.getCurrentProduct()!=null) {
            List<String> choices = new ArrayList<>();
            choices.add("A");
            choices.add("B");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Choose warehouse");
            dialog.setHeaderText("Choose warehouse:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(letter -> {
                dao.moveProduct(dao.getCurrentProduct(), dao.getWarehouseByMark(letter).getId());
                listProducts.setAll(dao.products(warehouse));
            });
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error Current Product");
            alert.setContentText("You need to select product first!");

            alert.showAndWait();
        }
    }

    public void chooseAction(ActionEvent actionEvent){
        List<String> choices = new ArrayList<>();
        choices.add("A");
        choices.add("B");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("A", choices);
        dialog.setTitle("Choose warehouse");
        dialog.setHeaderText("Choose warehouse:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            warehouse = dao.getWarehouseByMark(letter);
            listProducts.setAll(dao.products(warehouse));
        });
    }

    public void saveAction(ActionEvent actionEvent){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("txt file","txt");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(fileNameExtensionFilter);
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file= new File(jfc.getSelectedFile().getAbsolutePath());
            PrintWriter output;
            try {
                output = new PrintWriter(new FileWriter(file));
                for(Product product : listProducts){
                    output.write(product.getName() + " " + product.getAmount() + " " + product.getImportPrice() + " " + product.getExportPrice() + " " + product.getCode() + " " + product.getWarehouse().getMark() + " " + product.getLocation().getSection() + "\n");
                }
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void orderAction(ActionEvent actionEvent){
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/order.fxml"));
            OrderController ctrl = new OrderController(dao.products(warehouse), true);
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("Make order");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Product product = ctrl.getProduct();
                Order order = ctrl.getOrder();
                if(product!=null) {
                    dao.changeProduct(order.getProduct().getId(), order.getAmount(), true);
                    dao.addOrder(order);
                    dao.changeAnnualReport(order);
                    listProducts.setAll(dao.products(warehouse));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deliveryAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/order.fxml"));
            OrderController ctrl = new OrderController(dao.products(warehouse), false);
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("Make order");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Product product = ctrl.getProduct();
                Delivery delivery = ctrl.getDelivery();
                if(product!=null) {
                    dao.changeProduct(delivery.getProduct().getId(), delivery.getAmount(), false);
                    dao.changeAnnualReport(delivery);
                    dao.addDelivery(delivery);
                    listProducts.setAll(dao.products(warehouse));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAction(ActionEvent actionEvent){
        try {
            new Report().showReport(dao.getConn());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
