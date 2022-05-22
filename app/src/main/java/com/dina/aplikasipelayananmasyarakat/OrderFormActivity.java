package com.dina.aplikasipelayananmasyarakat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderFormActivity extends AppCompatActivity {

    Uri KtpUri,KkUri;
    ImageView ivKtp,ivKk;
    Spinner spPurpose,spAkte;
    TextView tvAkte,tvAkteNull;
    Button btnKtp,btnKk,btnSubmit;
    String fileKtpName,fileKkName,akteName;
    int selectedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        String userEmail=sharedPreferences.getString("email","");
        ResultSet rs=null;

        spPurpose= findViewById(R.id.sp_purpose);
        tvAkte= findViewById(R.id.textView6);
        tvAkteNull= findViewById(R.id.tv_akte_null);
        spAkte= findViewById(R.id.sp_akte);
        btnKtp= findViewById(R.id.btn_ktp);
        btnKk= findViewById(R.id.btn_kk);
        btnSubmit= findViewById(R.id.btnSubmitPermohonan);
        ivKtp= findViewById(R.id.iv_ktp_done);
        ivKk= findViewById(R.id.iv_kk_done);

        ArrayList<String> listAkte=new ArrayList<String>();
        ArrayAdapter<String> adapterAkte=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();
            Log.v("teks", String.valueOf(connect));

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
                if (i==7){
                    tvAkte.setVisibility(View.VISIBLE);
                    if (!listAkte.isEmpty()){
                        spAkte.setVisibility(View.VISIBLE);
                    }else{
                        tvAkteNull.setVisibility(View.VISIBLE);
                    }
                }else{
                    tvAkte.setVisibility(View.GONE);
                    spAkte.setVisibility(View.GONE);
                }
                selectedSpinner=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnKtp.setOnClickListener(view -> selectKtp());

        btnKk.setOnClickListener(view -> selectKk());

        btnSubmit.setOnClickListener(view -> {
            if (validateInput()){
                uploadFile();
                try {
                    ConnectionHelper connectionHelper=new ConnectionHelper();
                    Connection connect=connectionHelper.connections();

                    if (connect==null){
                        String ConnectionResult="Check Your Internet Connection";
                        Toast.makeText(OrderFormActivity.this,ConnectionResult,Toast.LENGTH_LONG).show();
                    }else{
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                        Date now = new Date();
                        String id = formatter.format(now);

                        String query=String.format("Insert into orders values ('%s','%s','%s','%s','%s','%s',null,'%s')",id,userEmail,spPurpose.getSelectedItem().toString(),fileKtpName,fileKkName,"Menunggu Kepala Dusun",akteName);
                        Statement stmt=connect.createStatement();
                        stmt.executeUpdate(query);

                        Toast.makeText(OrderFormActivity.this,"Berhasil Buat Permohonan",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(OrderFormActivity.this,MainActivity.class));
                        finish();
                        connect.close();
                    }
                } catch (SQLException throwables) {
                    Log.e("gagal", String.valueOf(throwables));
                    throwables.printStackTrace();
                }
            }
        });
    }

    private void selectKtp() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,11);
    }

    private void selectKk() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,12);
    }

    private void uploadFile() {
        StorageReference storageReferenceKtp = FirebaseStorage.getInstance().getReference("ktp/"+fileKtpName);
        StorageReference storageReferenceKk = FirebaseStorage.getInstance().getReference("kk/"+fileKtpName);

        storageReferenceKtp.putFile(KtpUri)
            .addOnSuccessListener(taskSnapshot -> Toast.makeText(OrderFormActivity.this,"Berhasil Upload File",Toast.LENGTH_LONG).show())
            .addOnFailureListener(e -> Toast.makeText(OrderFormActivity.this,"Gagal Upload File",Toast.LENGTH_LONG).show());

        storageReferenceKk.putFile(KkUri)
            .addOnSuccessListener(taskSnapshot -> Toast.makeText(OrderFormActivity.this,"Berhasil Upload File",Toast.LENGTH_LONG).show())
            .addOnFailureListener(e -> Toast.makeText(OrderFormActivity.this,"Gagal Upload File",Toast.LENGTH_LONG).show());
    }

    private boolean validateInput(){
        if (spPurpose.getSelectedItem().toString().equals("")){
            Toast.makeText(OrderFormActivity.this,"Pilih Uraian Urusan",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (KtpUri==null){
            btnKtp.setError("Upload KTP Anda");
            return false;
        }

        if (KkUri==null){
            btnKk.setError("Upload KK Anda");
            return false;
        }

        if (selectedSpinner==7&&spAkte.getSelectedItem().toString().equals("")){
            Toast.makeText(OrderFormActivity.this,"Pilih Akte Kelahiran",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==11){
            assert data != null;
            KtpUri=data.getData();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
            Date now = new Date();
            fileKtpName = formatter.format(now);

            Toast.makeText(OrderFormActivity.this,"KTP Telah Dipilih",Toast.LENGTH_SHORT).show();
            ivKtp.setVisibility(View.VISIBLE);
        }else if (requestCode==12){
            assert data != null;
            KkUri=data.getData();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
            Date now = new Date();
            fileKkName = formatter.format(now);

            Toast.makeText(OrderFormActivity.this,"KK Telah Dipilih",Toast.LENGTH_SHORT).show();
            ivKk.setVisibility(View.VISIBLE);
        }
    }
}