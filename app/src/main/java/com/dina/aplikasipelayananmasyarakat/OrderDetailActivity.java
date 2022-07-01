package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabAkte;
    LinearLayout layoutBtn;
    Button btnApprove,btnDecline;
    ArrayList<Order> order;
    ArrayList<String> user;
    ArrayList akte;
    String status,desc,body;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        int role = sharedPreferences.getInt("role",1);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        Intent intent=this.getIntent();
        order= (ArrayList<Order>) intent.getSerializableExtra("order");
        pos=intent.getIntExtra("pos",0);
        user=new ArrayList<>();
        akte=new ArrayList();

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect=connectionHelper.connections();
            ResultSet rs=null;

            if (connect == null){
                Toast.makeText(this,"Check Your Connection",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }else{
                String query="Select * from users where email='"+order.get(pos).getUser_email()+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                while (rs.next()){
                    user.add(rs.getString("email"));
                    user.add(rs.getString("name"));
                    user.add(rs.getString("nik"));
                    user.add(rs.getString("birthplace"));
                    user.add(rs.getString("birthdate"));
                    user.add(rs.getString("address"));
                    user.add(rs.getString("occupation"));
                }
                progress.dismiss();
                replaceFragment(new OrderInfoFragment(order.get(pos), user));
                connect.close();
            }
        } catch (Exception e) {
            Log.e("gagals", String.valueOf(e));
            progress.dismiss();
            e.printStackTrace();
        }

        if (order.get(pos).getAkte()!=null){
            try {
                ConnectionHelper connectionHelper=new ConnectionHelper();
                Connection connect=connectionHelper.connections();
                ResultSet rs=null;

                if (connect == null){
                    Toast.makeText(this,"Check Your Connection",Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }else{
                    String query="Select * from akte where user_email='"+order.get(pos).getUser_email()+"' and nama_bayi='"+order.get(pos).getAkte()+"'";
                    Statement stmt=connect.createStatement();
                    rs=stmt.executeQuery(query);

                    while (rs.next()){
                        akte.add(rs.getString("kepala_keluarga"));
                        akte.add(rs.getString("kartu_keluarga"));
                        akte.add(rs.getString("nama_bayi"));
                        akte.add(rs.getString("gender_bayi"));
                        akte.add(rs.getString("tempat_dilahirkan"));
                        akte.add(rs.getString("tempat_lahir_bayi"));
                        akte.add(rs.getString("hari_kelahiran"));
                        akte.add(rs.getString("tgl_lahir_bayi"));
                        akte.add(rs.getString("jam_lahir_bayi"));
                        akte.add(rs.getString("jenis_kelahiran"));
                        akte.add(rs.getString("kelahiran_ke"));
                        akte.add(rs.getString("penolong_kelahiran"));
                        akte.add(rs.getInt("berat_bayi"));
                        akte.add(rs.getInt("panjang_bayi"));
                        akte.add(rs.getString("nik_ibu"));
                        akte.add(rs.getString("nama_ibu"));
                        akte.add(rs.getString("tgl_lahir_ibu"));
                        akte.add(rs.getInt("umur_ibu"));
                        akte.add(rs.getString("kerja_ibu"));
                        akte.add(rs.getString("alamat_ibu"));
                        akte.add(rs.getString("kewarganegaraan_ibu"));
                        akte.add(rs.getString("tgl_perkawinan"));
                        akte.add(rs.getString("nik_ayah"));
                        akte.add(rs.getString("nama_ayah"));
                        akte.add(rs.getString("tgl_lahir_ayah"));
                        akte.add(rs.getInt("umur_ayah"));
                        akte.add(rs.getString("kerja_ayah"));
                        akte.add(rs.getString("alamat_ayah"));
                        akte.add(rs.getString("kewarganegaraan_ayah"));
                        akte.add(rs.getString("nik_pelapor"));
                        akte.add(rs.getString("nama_pelapor"));
                        akte.add(rs.getString("gender_pelapor"));
                        akte.add(rs.getInt("umur_pelapor"));
                        akte.add(rs.getString("kerja_pelapor"));
                        akte.add(rs.getString("alamat_pelapor"));
                    }
                    progress.dismiss();
                    connect.close();
                }
            } catch (Exception e) {
                Log.e("gagals", String.valueOf(e));
                progress.dismiss();
                e.printStackTrace();
            }
        }

        tabLayout=findViewById(R.id.tabLayout);
        layoutBtn=findViewById(R.id.layout_btn);
        btnApprove=findViewById(R.id.btn_approve);
        btnDecline=findViewById(R.id.btn_decline);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.v("hasil", String.valueOf(tab.getPosition()));
                switch (tab.getPosition()){
                    case 0:
                        Log.v("gagala",String.valueOf(user));
                        replaceFragment(new OrderInfoFragment(order.get(pos),user));
                        break;
                    case 1:
                        replaceFragment(new OrderFileFragment(order.get(pos)));
                        break;
                    case 2:
                        replaceFragment(new OrderAkteFragment(akte));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (role==2){
            if (order.get(pos).getStatus().equals("Ditolak oleh Kepala Dusun")||order.get(pos).getStatus().equals("Disetujui oleh Kepala Dusun")){
                layoutBtn.setVisibility(View.GONE);
            }else{
                layoutBtn.setVisibility(View.VISIBLE);
            }
        }else if (role==3){
            if (order.get(pos).getStatus().equals("Ditolak oleh Kepala Desa")||order.get(pos).getStatus().equals("Disetujui oleh Kepala Desa")){
                layoutBtn.setVisibility(View.GONE);
            }else{
                layoutBtn.setVisibility(View.VISIBLE);
            }
        }

        Log.v("role",String.valueOf(role));

        LayoutInflater inflater =this.getLayoutInflater();

        btnDecline.setOnClickListener(view -> {
            View viewDecline=inflater.inflate(R.layout.dialog_decline,null);
            AlertDialog.Builder alertDecline=new AlertDialog.Builder(this).setTitle("Tolak Permohonan")
                    .setView(viewDecline)
                    .setPositiveButton("Tolak Permohonan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText etDecline=viewDecline.findViewById(R.id.et_decline);
                            desc=etDecline.getText().toString();
                            if (role==2){
                                status="Ditolak oleh Kepala Dusun";
                                body="Permohonan Untuk "+order.get(pos).getPurpose()+" Anda Ditolak Oleh Kepala Dusun Dengan Alasan : "+desc.toUpperCase();
                            }else{
                                status="Ditolak oleh Kepala Desa";
                                body="Permohonan Untuk "+order.get(pos).getPurpose()+" Anda Ditolak Oleh Kepala Desa Dengan Alasan : "+desc.toUpperCase();
                            }

                            try {
                                ConnectionHelper connectionHelper=new ConnectionHelper();
                                Connection connect=connectionHelper.connections();
                                ResultSet rs=null;

                                if (connect == null){
                                    Toast.makeText(OrderDetailActivity.this,"Check Your Connection",Toast.LENGTH_LONG).show();
                                }else{
                                    String query="update orders set status='"+status+"',description='"+desc+"' where id='"+order.get(pos).getId()+"'";
                                    Statement stmt=connect.createStatement();
                                    stmt.executeUpdate(query);
                                    rs=stmt.executeQuery(query);

                                    connect.close();
                                    startActivity(new Intent(OrderDetailActivity.this,MainActivity.class));
                                    finish();
                                }
                            } catch (Exception e) {
                                Log.e("gagals", String.valueOf(e));
                                e.printStackTrace();
                            }
                            try {
                                GMailSender sender = new GMailSender("apl.mandoge@gmail.com", "rsrmsemgfazzequf");
                                sender.sendMail("Perubahan Status Permohonan",
                                        body,
                                        "apl.mandoge@gmail.com",
                                        order.get(pos).getUser_email());
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }
                    }).setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    alertDecline.show();
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertApprove=new AlertDialog.Builder(OrderDetailActivity.this).setTitle("Setujui Permohonan")
                        .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setPositiveButton("Setujui Permohonan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (role==2){
                                    status="Disetujui oleh Kepala Dusun";
                                    body="Permohonan Untuk "+order.get(pos).getPurpose()+" Anda Sudah Disetujui Oleh Kepala Dusun";
                                }else{
                                    status="Disetujui oleh Kepala Desa";
                                    body="Permohonan Untuk "+order.get(pos).getPurpose()+" Anda Sudah Disetujui Oleh Kepala Desa\nSegera ambil ke kantor kepala desa";
                                }

                                try {
                                    ConnectionHelper connectionHelper=new ConnectionHelper();
                                    Connection connect=connectionHelper.connections();
                                    ResultSet rs=null;

                                    if (connect == null){
                                        Toast.makeText(OrderDetailActivity.this,"Check Your Connection",Toast.LENGTH_LONG).show();
                                    }else{
                                        String query="update orders set status='"+status+"' where id='"+order.get(pos).getId()+"'";
                                        Statement stmt=connect.createStatement();
                                        stmt.executeUpdate(query);
                                        rs=stmt.executeQuery(query);

                                        connect.close();
                                        startActivity(new Intent(OrderDetailActivity.this,MainActivity.class));
                                        finish();
                                    }
                                } catch (Exception e) {
                                    Log.e("gagals", String.valueOf(e));
                                    e.printStackTrace();
                                }

                                try {
                                    GMailSender sender = new GMailSender("apl.mandoge@gmail.com", "rsrmsemgfazzequf");
                                    sender.sendMail("Perubahan Status Permohonan",
                                            body,
                                            "apl.mandoge@gmail.com",
                                            order.get(pos).getUser_email());
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                }
                            }
                        });
                alertApprove.show();

            }
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.container_order_detail,fragment);
        ft.commit();
    }
}