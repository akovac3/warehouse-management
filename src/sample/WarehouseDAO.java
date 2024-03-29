package sample;

import javafx.beans.property.SimpleObjectProperty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WarehouseDAO {
    private static WarehouseDAO instance;
    private Connection conn;
    private PreparedStatement getARQuery, addARQuery, changeCostQuery, changeProfitQuery, getMaxDeliveryId, addDeliveryQuery, getProductQuery, getProductsQuery, getMaxOrderId, getWarehouseByIdQuery, addOrderQuery, getLocationQuery, getWarehouseByMarkQuery, getProductsByWarehouseQuery, updateWarehouseOfProductQuery, getWarehouseQuery, getMaxProductId, getMaxLocationId, addProductQuery, addLocationQuery, changeProductQuery;
    private SimpleObjectProperty<Product> currentProduct = new SimpleObjectProperty<>();

    public WarehouseDAO (){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:base.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getProductsQuery = conn.prepareStatement("SELECT * FROM product");
        } catch (SQLException e) {
            regenerateBase();
            try {
                getProductsQuery = conn.prepareStatement("SELECT * FROM product");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            getProductQuery = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
            getWarehouseByIdQuery = conn.prepareStatement("SELECT id, manager, address, mark, image FROM warehouse WHERE id=?");
            getLocationQuery = conn.prepareStatement("SELECT * FROM location WHERE id=?");
            getWarehouseByMarkQuery = conn.prepareStatement("SELECT id, manager, address, mark, image FROM warehouse WHERE mark=?");
            getProductsByWarehouseQuery = conn.prepareStatement("SELECT * FROM product WHERE warehouse=?");
            updateWarehouseOfProductQuery = conn.prepareStatement("UPDATE product SET warehouse=? WHERE id=?");
            getWarehouseQuery = conn.prepareStatement("SELECT * FROM warehouse");
            getMaxProductId = conn.prepareStatement("SELECT Max(id) FROM product");
            getMaxLocationId = conn.prepareStatement("SELECT Max(id) FROM  location");
            addProductQuery = conn.prepareStatement("INSERT INTO product values(?,?,?,?,?,?,?,?)");
            addLocationQuery = conn.prepareStatement("INSERT INTO location values(?,?,?)");
            changeProductQuery = conn.prepareStatement("UPDATE product SET amount=? WHERE id=?");
            getMaxOrderId = conn.prepareStatement("SELECT Max(id) FROM orders");
            addOrderQuery = conn.prepareStatement("INSERT INTO orders values(?,?,?,?,?)");
            getMaxDeliveryId = conn.prepareStatement("SELECT Max(id) FROM delivery");
            addDeliveryQuery = conn.prepareStatement("INSERT INTO delivery VALUES(?,?,?,?,?)");
            getARQuery = conn.prepareStatement("SELECT * FROM annual_report");
            changeCostQuery = conn.prepareStatement("UPDATE annual_report SET cost=? WHERE current_year=?");
            changeProfitQuery = conn.prepareStatement("UPDATE annual_report SET profit=? WHERE current_year=?");
            addARQuery = conn.prepareStatement("INSERT INTO annual_report VALUES(?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static WarehouseDAO getInstance(){
        if(instance==null) instance= new WarehouseDAO();
        return instance;
    }

    public static void removeInstance() {
        if(instance == null) return;
        instance.close();
        instance = null;
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerateBase() {
        Scanner input = null;
        try {
            input = new Scanner( new FileInputStream("base.db.sql"));
            String sqlUpit="";
            while (input.hasNext()){
                sqlUpit += input.nextLine();
                if(sqlUpit.charAt(sqlUpit.length()-1) == ';'){
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit="";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

    public ArrayList<Product> products(Warehouse wh) {
        ArrayList<Product> result = new ArrayList<>();

        try {
            ResultSet rs = null;
            if(wh==null)
                rs = getProductsQuery.executeQuery();
            else{
                getProductsByWarehouseQuery.setInt(1, wh.getId());
                rs = getProductsByWarehouseQuery.executeQuery();
            }
                while (rs.next()){
                    Product p = getProductFromRS(rs);
                    result.add(p);
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        currentProduct.set(null);
        return result;
    }

    private Warehouse getWarehouseFromRS(ResultSet rs) throws SQLException {
        Warehouse w = new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
        return w;
    }

    private Location getLocationFromRS(ResultSet rs) throws SQLException {
        Location l = null;
        try {
            l = new Location(rs.getInt(2), rs.getInt(3));
        } catch (IllegalLocationException e) {
            e.printStackTrace();
        }
        return l;
    }

    public Warehouse getWarehouseByMark(String mark){
        Warehouse w= null;
        try {
            getWarehouseByMarkQuery.setString(1,mark);
            ResultSet rs = getWarehouseByMarkQuery.executeQuery();
            w= getWarehouseFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public Product getCurrentProduct() {
        return currentProduct.get();
    }

    public SimpleObjectProperty<Product> currentProductProperty() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct.set(currentProduct);
    }

    public void moveProduct(Product p, int wh){
        if(wh==p.getWarehouse().getId())return;
        try {
            updateWarehouseOfProductQuery.setInt(1, wh);
            updateWarehouseOfProductQuery.setInt(2, p.getId());
            updateWarehouseOfProductQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Warehouse> getListWarehouse (){
        ArrayList<Warehouse> result = new ArrayList<>();
        try {
            ResultSet rs = getWarehouseQuery.executeQuery();
            while(rs.next()){
                Warehouse wh = getWarehouseFromRS(rs);
                result.add(wh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addProduct(Product product) {
        try {
            ResultSet rs = getMaxLocationId.executeQuery();
            int id = 1;
            if(rs.next()){
                id = rs.getInt(1)+1;
            }

            addLocationQuery.setInt(1, id);
            addLocationQuery.setInt(2, product.getLocation().getSection());
            addLocationQuery.setInt(3, product.getLocation().getPosition());
            addLocationQuery.executeUpdate();
            rs = null;
            rs = getMaxProductId.executeQuery();
            int idProduct =1;
            if(rs.next()){
                idProduct = rs.getInt(1) + 1 ;
            }

            addProductQuery.setInt(1, idProduct);
            addProductQuery.setString(2, product.getName());
            addProductQuery.setInt(3, product.getAmount() );
            addProductQuery.setInt(4, product.getImportPrice());
            addProductQuery.setInt(5, product.getExportPrice());
            addProductQuery.setString(6, product.getCode());
            addProductQuery.setInt(7, product.getWarehouse().getId());
            addProductQuery.setInt(8, id);
            addProductQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void changeProduct(int id, int amount, boolean od) {
        try {
            getProductQuery.setInt(1, id);
            ResultSet rs = getProductQuery.executeQuery();
            Product p = null;
            if(rs.next()) {
                p = getProductFromRS(rs);
            }
            if(p!=null) {
                if(od) {
                    if(p.getAmount()>amount) {
                        changeProductQuery.setInt(1, p.getAmount() - amount);
                    }
                    else return;
                } else {
                    changeProductQuery.setInt(1, p.getAmount() + amount);
                }
                changeProductQuery.setInt(2, id);
                changeProductQuery.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Product getProductFromRS(ResultSet rs) {
        Product product=null;
        try {
            getWarehouseByIdQuery.setInt(1,rs.getInt(7));
            ResultSet r = getWarehouseByIdQuery.executeQuery();
            Warehouse w = getWarehouseFromRS(r);
            getLocationQuery.setInt(1, rs.getInt(8));
            ResultSet s= getLocationQuery.executeQuery();
            Location l = getLocationFromRS(s);
            product = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), w, l);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void addOrder(Order order) {
        ResultSet rs = null;
        try {
            rs = getMaxOrderId.executeQuery();
            int id = 1;
            if(rs.next()){
                id = rs.getInt(1)+1;
            }

            addOrderQuery.setInt(1, id);
            addOrderQuery.setDate(2, Date.valueOf(order.getDate()));
            addOrderQuery.setInt(3, order.getProduct().getId());
            addOrderQuery.setInt(4, order.getAmount());
            addOrderQuery.setInt(5, order.getTotalPrice());
            addOrderQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addDelivery(Delivery delivery) {
        ResultSet rs = null;
        try {
            rs = getMaxDeliveryId.executeQuery();
            int id = 1;
            if(rs.next()){
                id = rs.getInt(1)+1;
            }

            addDeliveryQuery.setInt(1, id);
            addDeliveryQuery.setDate(2, Date.valueOf(delivery.getDate()));
            addDeliveryQuery.setInt(3, delivery.getProduct().getId());
            addDeliveryQuery.setInt(4, delivery.getAmount());
            addDeliveryQuery.setInt(5, delivery.getTotalPrice());
            addDeliveryQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeAnnualReport(Order od) {
        try {
            boolean found = false;
            int cost = 0, profit=0;
            ResultSet rs = getARQuery.executeQuery();
            while (rs.next()){
                if(od.getDate().getYear() == rs.getInt(1)){
                    found = true;
                    profit = rs.getInt(2);
                    cost = rs.getInt(3);
                }
            }
            if(!found){
                addARQuery.setInt(1, od.getDate().getYear());
                addARQuery.setInt(2, cost);
                addARQuery.setInt(3, profit);
                addARQuery.executeUpdate();
            }
            if(od instanceof Delivery){
                changeCostQuery.setInt(1, cost + od.getTotalPrice());
                changeCostQuery.setInt(2, od.getDate().getYear());
                changeCostQuery.executeUpdate();
            }

            else{
                changeProfitQuery.setInt(1, profit + od.getTotalPrice());
                changeProfitQuery.setInt(2, od.getDate().getYear());
                changeProfitQuery.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
