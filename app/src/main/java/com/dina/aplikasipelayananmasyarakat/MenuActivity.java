package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    LinearLayout llPermohonan,llAkte,llPengaduan,llSetting;
    String query,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        String userEmail = sharedPreferences.getString("email","");
        int userRole = sharedPreferences.getInt("role",1);

        if (userEmail.equals("")){
            Log.v(" main",sharedPreferences.getString("email","sa"));

            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            finish();
        }else{
            if (userRole>1){
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
                finish();
            }
        }

        llPermohonan=findViewById(R.id.ll_menu_permohonan);
        llAkte=findViewById(R.id.ll_menu_akte);
        llPengaduan=findViewById(R.id.ll_menu_pengaduan);
        llSetting=findViewById(R.id.ll_menu_setting);

        llPermohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
                finish();
            }
        });

        llAkte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,AkteFormActivity.class));
            }
        });

        llPengaduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDecline=getLayoutInflater().inflate(R.layout.dialog_report,null);
                AlertDialog.Builder alertReport=new AlertDialog.Builder(MenuActivity.this,R.style.MaterialDialogReport).setTitle("Buat Pengaduan")
                        .setView(viewDecline)
                        .setPositiveButton("Kirim Pengaduan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etReport=viewDecline.findViewById(R.id.et_report);
                                desc=etReport.getText().toString();

                                try {
                                    ConnectionHelper connectionHelper=new ConnectionHelper();
                                    Connection connect=connectionHelper.connections();
                                    ResultSet rs=null;

                                    if (connect == null){
                                        Toast.makeText(MenuActivity.this,"Check Your Connection",Toast.LENGTH_LONG).show();
                                    }else{
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                                        Date now = new Date();
                                        String id = userEmail+"_"+formatter.format(now);

                                        String query=String.format("Insert into report values ('%s','%s','%s',TO_DATE('%s', 'yyyy/mm/dd hh24:mi:ss'))",id,userEmail,desc,formatter2.format(now));
                                        Statement stmt=connect.createStatement();
                                        stmt.executeUpdate(query);
                                        rs=stmt.executeQuery(query);

                                        connect.close();
                                        Toast.makeText(MenuActivity.this,"Pengaduan telah dikirim",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("gagals", String.valueOf(e));
                                    e.printStackTrace();
                                }
                            }
                        }).setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                alertReport.show();
            }
        });

        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,MainActivity.class).putExtra("fragment","setting"));
            }
        });
    }
}