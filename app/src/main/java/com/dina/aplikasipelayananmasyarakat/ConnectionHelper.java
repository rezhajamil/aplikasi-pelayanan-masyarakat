package com.dina.aplikasipelayananmasyarakat;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {
    public Connection connections(){
        java.sql.Connection con=null;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ec2-13-250-123-129.ap-southeast-1.compute.amazonaws.com:1521:xe",
                    "dina", "dina");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            Log.e("gagal koneksi",e.toString());
            e.printStackTrace();
        }

        return  con;
    }
}
