package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class WarehouseDAO {
    private static WarehouseDAO instance;
    private Connection conn;
    private PreparedStatement getProductsQuery, getWarehouseQuery, getLocationQuery;
    private ObservableList<Product> products = FXCollections.observableArrayList();

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
            getWarehouseQuery = conn.prepareStatement("SELECT manager, address, mark FROM warehouse WHERE id=?");
            getLocationQuery = conn.prepareStatement("SELECT section, position FROM location WHERE id=?");
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

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public void load(){
        try {
            ResultSet rs = getProductsQuery.executeQuery();
            while (rs.next()){
                getWarehouseQuery.setInt(1,rs.getInt(7));
                ResultSet r = getWarehouseQuery.executeQuery();
                Warehouse w = getWarehouseFromRS(r);
                getLocationQuery.setInt(1, rs.getInt(8));
                r= getLocationQuery.executeQuery();
                Location l = getLocationFromRS(r);
                Product p = new Product(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), w,l);
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Warehouse getWarehouseFromRS(ResultSet rs) throws SQLException {
        Warehouse w = new Warehouse(rs.getString(1), rs.getString(2), rs.getString(3));
        return w;
    }

    private Location getLocationFromRS(ResultSet rs) throws SQLException {
        Location l = null;
        try {
            l = new Location(rs.getInt(1), rs.getInt(2));
        } catch (IllegalLocationException e) {
            e.printStackTrace();
        }
        return l;
    }
}
