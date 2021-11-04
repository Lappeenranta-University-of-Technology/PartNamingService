package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBConnection {

    Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/smart_search", "root", "");
//            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static ObservableList<Project> getDataprojects() {
        Connection conn = ConnectDb();
        ObservableList<Project> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `project`");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                list.add(new Project(Integer.parseInt(rs.getString("Project_Id")), rs.getString("Project_Name"), rs.getString("Project_Zone"), rs.getString("Project_Scope")));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public static ObservableList<Hardware> getDataHardware(String id) {
        Connection conn = ConnectDb();
        ObservableList<Hardware> list = FXCollections.observableArrayList();
        System.out.println(id);
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `hardwares`  where Project_Id='" + id + "'  ");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                list.add(new Hardware(Integer.parseInt(rs.getString("Hardware_Id")),Integer.parseInt(rs.getString("Project_Id")),
                        rs.getString("Diameter"), rs.getString("Pitch"), rs.getString("B"), rs.getString("K"),
                        rs.getString("DK"), rs.getString("A"), rs.getString("S"), rs.getString("Total_Length"),
                        rs.getString("Head"), rs.getString("Socket"), rs.getString("Type"), Integer.parseInt(rs.getString("Quan")), rs.getNString("Name")));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public static Object getDataAllHardware() {
        Connection conn = ConnectDb();
        ObservableList<Hardware> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `hardwares`");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                list.add(new Hardware(Integer.parseInt(rs.getString("Hardware_Id")),Integer.parseInt(rs.getString("Project_Id")),
                        rs.getString("Diameter"), rs.getString("Pitch"), rs.getString("B"), rs.getString("K"),
                        rs.getString("DK"), rs.getString("A"), rs.getString("S"), rs.getString("Total_Length"),
                        rs.getString("Head"), rs.getString("Socket"), rs.getString("Type"), Integer.parseInt(rs.getString("Quan")), rs.getString("Name")));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public static Object getSelectedProject(String id) {
        Connection conn = ConnectDb();
        ObservableList<Hardware> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `project`  where Project_Id='" + id + "'  ");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                list.add(new Hardware(Integer.parseInt(rs.getString("Hardware_Id")),Integer.parseInt(rs.getString("Project_Id")),
                        rs.getString("Diameter"), rs.getString("Pitch"), rs.getString("B"), rs.getString("K"),
                        rs.getString("DK"), rs.getString("A"), rs.getString("S"), rs.getString("Total_Length"),
                        rs.getString("Head"), rs.getString("Socket"), rs.getString("Type"), Integer.parseInt(rs.getString("Quan")), rs.getString("Name")));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public static Object getSelectedHardware(String id) {
        Connection conn = ConnectDb();
        ObservableList<Hardware> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `hardwares`  where Hardware+Id='" + id + "'  ");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                list.add(new Hardware(Integer.parseInt(rs.getString("Hardware_Id")),Integer.parseInt(rs.getString("Project_Id")),
                        rs.getString("Diameter"), rs.getString("Pitch"), rs.getString("B"), rs.getString("K"),
                        rs.getString("DK"), rs.getString("A"), rs.getString("S"), rs.getString("Total_Length"),
                        rs.getString("Head"), rs.getString("Socket"), rs.getString("Type"), Integer.parseInt(rs.getString("Quan")), rs.getString("Name")));

            }
        } catch (Exception e) {
        }

        return list;
    }

}
