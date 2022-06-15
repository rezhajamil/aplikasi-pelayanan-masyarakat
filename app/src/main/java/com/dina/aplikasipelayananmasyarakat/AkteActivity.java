package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class AkteActivity extends AppCompatActivity {

    RecyclerView rvAkte;
    String query;
    ArrayList<String> akte;
    AkteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akte);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        String userEmail = sharedPreferences.getString("email","");

        rvAkte=findViewById(R.id.rv_akte);

        ProgressDialog progress = new ProgressDialog(AkteActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Sedang Mengambil Data...");
        progress.setCancelable(false);
        progress.show();

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect=connectionHelper.connections();
            ResultSet rs=null;

            if (connect == null){
                Toast.makeText(AkteActivity.this,"Check Your Connection",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }else{
                query="Select * from akte where user_email='"+userEmail+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                akte=new ArrayList<>();
                while (rs.next()){
                    akte.add(rs.getString("nama_bayi"));
                }

                progress.dismiss();
                connect.close();
            }
        } catch (Exception e) {
            Log.e("gagal", String.valueOf(e));
            progress.dismiss();
            e.printStackTrace();
        }

        adapter=new AkteAdapter(AkteActivity.this,akte,userEmail);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(AkteActivity.this);
        rvAkte.setLayoutManager(layoutManager);
        rvAkte.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}