package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        String userEmail=sharedPreferences.getString("email","");
        ResultSet rs=null;

        Spinner spPurpose=(Spinner) findViewById(R.id.sp_purpose);
        TextView tvAkte=(TextView) findViewById(R.id.textView6);
        Spinner spAkte=(Spinner) findViewById(R.id.sp_akte);

        ArrayList<String> listAkte=new ArrayList<String>();
        ArrayAdapter<String> adapterAkte=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();

            if (connect==null){
                String ConnectionResult="Check Your Internet Connection";
                Toast.makeText(OrderFormActivity.this,ConnectionResult,Toast.LENGTH_SHORT);
            }else{
                String query="Select name from akte where user_email='"+userEmail+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                while (rs.next()){
                    listAkte.add(rs.getString("name"));
                }

                spAkte.setAdapter(adapterAkte);
                connect.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        spPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text=adapterView.getSelectedItem().toString();

                Log.v("teks",String.valueOf(text=="Mengurus Surat Kelahiran"));

                if (text=="Mengurus Surat Kelahiran"){
                    tvAkte.setVisibility(View.VISIBLE);
                    spAkte.setVisibility(View.VISIBLE);
                }else{
                    tvAkte.setVisibility(View.GONE);
                    spAkte.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}