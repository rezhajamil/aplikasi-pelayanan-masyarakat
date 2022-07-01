package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    RecyclerView rvReport;
    String query;
    ArrayList<Report> report;
    ReportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        rvReport=findViewById(R.id.rv_report);

        ProgressDialog progress = new ProgressDialog(ReportActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Sedang Mengambil Data...");
        progress.setCancelable(false);
        progress.show();

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect=connectionHelper.connections();
            ResultSet rs=null;

            if (connect == null){
                Toast.makeText(ReportActivity.this,"Check Your Connection",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }else{
                query="Select * from report order by created_at asc";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                report=new ArrayList<Report>();
                while (rs.next()){
                    report.add(new Report(
                        rs.getString("user_email"),
                        rs.getString("message")
                    ));
                }

                Log.v("ini",String.valueOf(report));

                progress.dismiss();
                connect.close();
            }
        } catch (Exception e) {
            Log.e("gagal_report", String.valueOf(e));
            progress.dismiss();
            e.printStackTrace();
        }

        adapter=new ReportAdapter(ReportActivity.this,report);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ReportActivity.this);
        rvReport.setLayoutManager(layoutManager);
        rvReport.setAdapter(adapter);
    }
}