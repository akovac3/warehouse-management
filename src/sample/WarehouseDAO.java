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
    private PreparedStatement getProductsQuery, getWarehouseByIdQuery, getLocationQuery, getWarehouseByMarkQuery, getProductsByWarehouseQuery, updateWarehouseOfProductQuery, getWarehouseQuery, getMaxProductId, getMaxLocationId, addProductQuery, addLocationQuery;
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
                    getWarehouseByIdQuery.setInt(1,rs.getInt(7));
                    ResultSet r = getWarehouseByIdQuery.executeQuery();
                    Warehouse w = getWarehouseFromRS(r);
                    getLocationQuery.setInt(1, rs.getInt(8));
                    ResultSet s= getLocationQuery.executeQuery();
                    Location l = getLocationFromRS(s);
                    Product p = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), w, l);
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

}
