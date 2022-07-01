package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AkteFormActivity extends AppCompatActivity {

    Spinner genderBayi,hariKelahiran,jenisKelahiran,tempatDilahirkan,kelahiranKe,penolongKelahiran,kwIbu,kwAyah,genderPelapor;
    EditText namaKK,noKK,namaBayi,tempatLahirBayi,tglLahirBayi,jamLahirBayi,beratBayi,panjangBayi,nikIbu,namaIbu,tglLahirIbu,umurIbu,kerjaIbu,alamatIbu,tglPerkawinan,nikAyah,namaAyah,tglLahirAyah,umurAyah,kerjaAyah,alamatAyah,nikPelapor,namaPelapor,umurPelapor,kerjaPelapor,alamatPelapor;
    Button btnKirim;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akte_form);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        userEmail=sharedPreferences.getString("email","");


        genderBayi=findViewById(R.id.sp_gender_bayi);
        hariKelahiran=findViewById(R.id.sp_hari_kelahiran);
        jenisKelahiran=findViewById(R.id.sp_jenis_kelahiran);
        tempatDilahirkan=findViewById(R.id.sp_tempat_dilahirkan);
        kelahiranKe=findViewById(R.id.sp_kelahiran_ke);
        penolongKelahiran=findViewById(R.id.sp_penolong_kelahiran);
        kwIbu=findViewById(R.id.sp_kw_ibu);
        kwAyah=findViewById(R.id.sp_kw_ayah);
        genderPelapor=findViewById(R.id.sp_gender_pelapor);

        namaKK=findViewById(R.id.et_decline);
        noKK=findViewById(R.id.et_KK_num);
        namaBayi=findViewById(R.id.et_nama_bayi);
        tempatLahirBayi=findViewById(R.id.et_tempat_kelahiran);
        tglLahirBayi=findViewById(R.id.et_tgl_lahir_bayi);
        jamLahirBayi=findViewById(R.id.et_jam_lahir_bayi);
        beratBayi=findViewById(R.id.et_berat_bayi);
        panjangBayi=findViewById(R.id.et_panjang_bayi);
        nikIbu=findViewById(R.id.et_nik_ibu);
        namaIbu=findViewById(R.id.et_nama_ibu);
        tglLahirIbu=findViewById(R.id.et_tgl_lahir_ibu);
        umurIbu=findViewById(R.id.et_umur_ibu);
        kerjaIbu=findViewById(R.id.et_pekerjaan_ibu);
        alamatIbu=findViewById(R.id.et_alamat_ibu);
        tglPerkawinan=findViewById(R.id.et_tgl_perkawinan);
        nikAyah=findViewById(R.id.et_nik_ayah);
        namaAyah=findViewById(R.id.et_nama_ayah);
        tglLahirAyah=findViewById(R.id.et_tgl_lahir_ayah);
        umurAyah=findViewById(R.id.et_umur_ayah);
        kerjaAyah=findViewById(R.id.et_pekerjaan_ayah);
        alamatAyah=findViewById(R.id.et_alamat_ayah);
        nikPelapor=findViewById(R.id.et_nik_pelapor);
        namaPelapor=findViewById(R.id.et_nama_pelapor);
        umurPelapor=findViewById(R.id.et_umur_pelapor);
        kerjaPelapor=findViewById(R.id.et_pekerjaan_pelapor);
        alamatPelapor=findViewById(R.id.et_alamat_pelapor);

        btnKirim=findViewById(R.id.btn_submit_akte);

        MaterialDatePicker<Long> dpTglBayi = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal").build();
        MaterialDatePicker<Long> dpTglIbu = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal").build();
        MaterialDatePicker<Long> dpTglPerkawinan = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal").build();
        MaterialDatePicker<Long> dpTglAyah = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal").build();
        MaterialTimePicker tpBayi=new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setTitleText("Pilih Jam").build();

        tglLahirBayi.setOnClickListener(view -> {
            dpTglBayi.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
        });

        tglLahirIbu.setOnClickListener(view -> {
            dpTglIbu.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
        });

        tglPerkawinan.setOnClickListener(view -> {
            dpTglPerkawinan.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
        });

        tglLahirAyah.setOnClickListener(view -> {
            dpTglAyah.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
        });

        jamLahirBayi.setOnClickListener(view -> {
            tpBayi.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
        });

        dpTglBayi.addOnPositiveButtonClickListener(selection -> {
            tglLahirBayi.setText(dpTglBayi.getHeaderText());
        });

        dpTglIbu.addOnPositiveButtonClickListener(selection -> {
            tglLahirIbu.setText(dpTglIbu.getHeaderText());
        });

        dpTglPerkawinan.addOnPositiveButtonClickListener(selection -> {
            tglPerkawinan.setText(dpTglPerkawinan.getHeaderText());
        });

        dpTglAyah.addOnPositiveButtonClickListener(selection -> {
            tglLahirAyah.setText(dpTglAyah.getHeaderText());
        });

        tpBayi.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jamLahirBayi.setText(tpBayi.getHour()+":"+tpBayi.getMinute());
            }
        });

        btnKirim.setOnClickListener(view -> {
            if (validateInput()){
                if (validateName()>0){
                    Toast.makeText(this,"Nama Bayi Sudah Digunakan",Toast.LENGTH_LONG).show();
                    namaBayi.setError("Sudah Ada");
                    namaBayi.requestFocus();
                    return;
                }
                ConnectionHelper connectionHelper=new ConnectionHelper();
                Connection connect =connectionHelper.connections();
                try{
                    if (connect==null){
                        String ConnectionResult="Check Your Internet Connection";
                        Toast.makeText(AkteFormActivity.this,ConnectionResult,Toast.LENGTH_LONG).show();
                    }else{
                        @SuppressLint("DefaultLocale") String query=String.format("Insert into akte values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,%d,'%s','%s','%s',%d,'%s','%s','%s','%s','%s','%s','%s',%d,'%s','%s','%s','%s','%s','%s',%d,'%s','%s','%s')",
                                namaKK.getText(),
                                noKK.getText(),
                                namaBayi.getText(),
                                genderBayi.getSelectedItem(),
                                tempatDilahirkan.getSelectedItem(),
                                tempatLahirBayi.getText(),
                                hariKelahiran.getSelectedItem(),
                                tglLahirBayi.getText(),
                                jamLahirBayi.getText(),
                                jenisKelahiran.getSelectedItem(),
                                kelahiranKe.getSelectedItem(),
                                penolongKelahiran.getSelectedItem(),
                                Integer.parseInt(beratBayi.getText().toString()),
                                Integer.parseInt(panjangBayi.getText().toString()),
                                nikIbu.getText(),
                                namaIbu.getText(),
                                tglLahirIbu.getText(),
                                Integer.parseInt(umurIbu.getText().toString()),
                                kerjaIbu.getText(),
                                alamatIbu.getText(),
                                kwIbu.getSelectedItem(),
                                tglPerkawinan.getText(),
                                nikAyah.getText(),
                                namaAyah.getText(),
                                tglLahirAyah.getText(),
                                Integer.parseInt(umurAyah.getText().toString()),
                                kerjaAyah.getText(),
                                alamatAyah.getText(),
                                kwAyah.getSelectedItem(),
                                nikPelapor.getText(),
                                namaPelapor.getText(),
                                genderPelapor.getSelectedItem(),
                                Integer.parseInt(umurPelapor.getText().toString()),
                                kerjaPelapor.getText(),
                                alamatPelapor.getText(),
                                userEmail);
                        Log.e("gagal",query);
                        Statement stmt=connect.createStatement();
                        stmt.executeUpdate(query);


                        Toast.makeText(AkteFormActivity.this,"Berhasil Buat Surat Keterangan Kelahiran",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AkteFormActivity.this,AkteActivity.class));
                        finish();
                        connect.close();
                    }
                } catch (SQLException throwables) {
                    Log.e("gagal",throwables.toString());
                }
            }
        });
    }

    public boolean validateSpinner(Spinner spinner,String title){
        if (spinner.getSelectedItemPosition()==0){
            Toast.makeText(AkteFormActivity.this,"Pilih "+title,Toast.LENGTH_LONG).show();
            spinner.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateEditText(EditText et,String title){
        if (et.length()==0){
            et.setError(title+" tidak boleh kosong");
            et.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateInput(){
        if(!validateEditText(namaKK,"Nama Kepala Keluarga")){
            return false;
        }else if(!validateEditText(noKK,"Nomor Kart Keluarga")){
            return false;
        }else if(!validateEditText(namaBayi,"Nama Bayi")){
            return false;
        }else if(!validateEditText(tempatLahirBayi,"Tempat Lahir Bayi")){
            return false;
        }else if(!validateEditText(tglLahirBayi,"Tanggal Lahir Bayi")){
            return false;
        }else if(!validateEditText(jamLahirBayi,"Jam Kelahiran Bayi")){
            return false;
        }else if(!validateEditText(beratBayi,"Berat Bayi")){
            return false;
        }else if(!validateEditText(panjangBayi,"Panjang Bayi")){
            return false;
        }else if(!validateEditText(nikIbu,"NIK Ibu")){
            return false;
        }else if(!validateEditText(namaIbu,"Nama Ibu")){
            return false;
        }else if(!validateEditText(tglLahirIbu,"Tanggal Lahir Ibu")){
            return false;
        }else if(!validateEditText(umurIbu,"Umur Ibu")){
            return false;
        }else if(!validateEditText(kerjaIbu,"Pekerjaan Ibu")){
            return false;
        }else if(!validateEditText(alamatIbu,"Alamat Ibu")){
            return false;
        }else if(!validateEditText(tglPerkawinan,"Tanggal Pencatatan Perkawinan Ibu")){
            return false;
        }else if(!validateEditText(nikAyah,"NIK Ayah")){
            return false;
        }else if(!validateEditText(namaAyah,"Nama Ayah")){
            return false;
        }else if(!validateEditText(tglLahirAyah,"Tanggal Lahir Ayah")){
            return false;
        }else if(!validateEditText(kerjaAyah,"Pekerjaan Ayah")){
            return false;
        }else if(!validateEditText(alamatAyah,"Alamat Ayah")){
            return false;
        }else if(!validateEditText(nikPelapor,"NIK Pelapor")){
            return false;
        }else if(!validateEditText(namaPelapor,"Nama Pelapor")){
            return false;
        }else if(!validateEditText(umurPelapor,"Umur Pelapor")){
            return false;
        }else if(!validateEditText(kerjaPelapor,"Pekerjaan Pelapor")){
            return false;
        }else if(!validateEditText(alamatPelapor,"Alamat Pelapor")){
            return false;
        }else if (!validateSpinner(genderBayi,"Jenis Kelamin Bayi")){
            return false;
        }else if(!validateSpinner(tempatDilahirkan,"Tempat Bayi Dilahirkan")){
            return false;
        }else if(!validateSpinner(hariKelahiran,"Hari Bayi Dilahirkan")){
            return false;
        }else if(!validateSpinner(jenisKelahiran,"Jenis Kelahiran")){
            return false;
        }else if(!validateSpinner(kelahiranKe,"Kelahiran Bayi Ke")){
            return false;
        }else if(!validateSpinner(penolongKelahiran,"Penolong Kelahiran")){
            return false;
        }else if(!validateSpinner(kwIbu,"Kewarganegaraan Ibu")){
            return false;
        }else if(!validateSpinner(kwAyah,"Kewarganegaraan Ayah")){
            return false;
        }else if(!validateSpinner(genderPelapor,"Jenis Kelamin Pelapor")){
            return false;
        }

        return true;
    }

    public int validateName(){
        ResultSet rs=null;
        int total=0;
        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();

            if (connect==null){
                String ConnectionResult="Check Your Internet Connection";
            }else{
                String query="Select count(*) as total from akte where nama_bayi='"+namaBayi.getText()+"' and user_email='"+userEmail+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                rs.next();
                total=rs.getInt("total");

                connect.close();
            }
        } catch (SQLException throwables) {
            Log.v("gagal2",String.valueOf(throwables));
            throwables.printStackTrace();
        }
        return total;
    }
}