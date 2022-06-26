package com.example.quanlytv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost/project_javafx";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connect successfully!!!");
        } catch(SQLException e){
            connection=null;
            System.out.println(e);
        }
    }

    public List<Televitions> getTelevitions(){
        ArrayList<Televitions> televitions = new ArrayList<>();
        try{
            ResultSet resultSet = connection.prepareStatement("SELECT * From store").executeQuery();
            while (resultSet.next()){
                int id =resultSet.getInt("id");
                String name =resultSet.getString("name");
                String brand= resultSet.getString("brand");
                int quantity =resultSet.getInt("quantity");
                String high_resolution=resultSet.getNString("high_resolution");
                String image =resultSet.getNString("image");
                String size =resultSet.getNString("size");
                Float price=resultSet.getFloat("price");
                System.out.println("============");
                System.out.println(id);
                System.out.println(name);
                System.out.println(brand);
                System.out.println(quantity);
                System.out.println(high_resolution);
                System.out.println(image);
                System.out.println(size);
                System.out.println(price);
                televitions.add(new Televitions(id,name,brand,quantity,high_resolution,image,size,price));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return televitions;
    }
    public void insertTelevitions(Televitions televitions) {
//        String s = "')";
        String sql="INSERT INTO store(name, brand,quantity,high_resolution,image,size,price) VALUES ('"+televitions.name+"','"+
                televitions.brand+"',"+televitions.quantity+",'"+ televitions.high_resolution +"','"+ televitions.image+"','"+
                televitions.size+"',"+televitions.price+")";

        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteTelevitions(int id){
        String sql ="DELETE from store where id=" + id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTelevitions(Televitions televitions, int id){
        String sql="UPDATE store SET name='"+televitions.name+"',brand='"+televitions.brand+"',quantity="+televitions.quantity+",high_resolution='"+
                televitions.high_resolution+"',image='"+televitions.image+"',size='"+televitions.size+"',price="+televitions.price+"WHERE id="+id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
