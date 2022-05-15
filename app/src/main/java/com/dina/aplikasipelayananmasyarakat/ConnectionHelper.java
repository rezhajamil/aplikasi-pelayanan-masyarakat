package com.dina.aplikasipelayananmasyarakat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {
    public Connection connections(){
        java.sql.Connection con=null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ec2-18-136-103-3.ap-southeast-1.compute.amazonaws.com:1521:xe",
                    "dina", "dina");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  con;
    }

    public void test(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ec2-18-136-103-3.ap-southeast-1.compute.amazonaws.com:1521:xe",
                    "dina", "dina");


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "Select * from customers"
            );

            while (rs.next())
                System.out.println(rs.getInt(1)+ " "+rs.getString(2));
            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
