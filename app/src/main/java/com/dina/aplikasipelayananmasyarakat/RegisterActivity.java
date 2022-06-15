package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etEmail,etPhone,etNik,etAddress,etBirthDate,etBirthPlace,etOccupation;
    Button btnSubmit,btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPhone=findViewById(R.id.etPhone);
        etNik=findViewById(R.id.etNik);
        etAddress=findViewById(R.id.etAddress);
        etBirthDate=findViewById(R.id.etBirthDate);
        etBirthPlace=findViewById(R.id.etBirthPlace);
        etOccupation=findViewById(R.id.etOccupation);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnLogout=findViewById(R.id.btnLogout);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal").build();

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               datePicker.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                etBirthDate.setText(datePicker.getHeaderText());
            }
        });

        Amplify.Auth.fetchUserAttributes(
                attributes->{
                    String email=attributes.get(3).getValue();
                    etEmail.setText(email);
                },
                error -> Log.v("Error","Failed to Fetch Email")
        );

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private boolean validateInput(){
        if (etName.length()==0){
            etName.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etEmail.length()==0){
            etEmail.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etPhone.length()==0){
            etPhone.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etNik.length()==0){
            etNik.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etAddress.length()==0){
            etAddress.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etBirthDate.length()==0){
            etBirthDate.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etBirthPlace.length()==0){
            etBirthPlace.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etOccupation.length()==0){
            etOccupation.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }
        return true;
    }

    private void addUser(){
        if (validateInput()){
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();
            try{
                if (connect==null){
                    String ConnectionResult="Check Your Internet Connection";
                    Toast.makeText(RegisterActivity.this,ConnectionResult,Toast.LENGTH_LONG).show();
                }else{
                    String query=String.format("Insert into users values ('%s','%s','%s','%s','%s','%s','%s',null,'%s',%d)",etEmail.getText(),etPhone.getText(),etNik.getText(),etName.getText(),etAddress.getText(),etBirthDate.getText(),etBirthPlace.getText(),etOccupation.getText(),1);
                    Statement stmt=connect.createStatement();
                    stmt.executeUpdate(query);

                    SharedPreferences sharedPreferences=getSharedPreferences("User",0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putString("email", String.valueOf(etEmail.getText()));
                    editor.putString("phone", String.valueOf(etPhone.getText()));
                    editor.putString("nik", String.valueOf(etNik.getText()));
                    editor.putString("name", String.valueOf(etName.getText()));
                    editor.putString("address", String.valueOf(etAddress.getText()));
                    editor.putString("birthDate", String.valueOf(etBirthDate.getText()));
                    editor.putString("birthPlace", String.valueOf(etBirthPlace.getText()));
                    editor.putString("avatar",null);
                    editor.putString("occupation", String.valueOf(etOccupation.getText()));
                    editor.putInt("role",1);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this,"Berhasil Register",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                    connect.close();
                }
            } catch (SQLException throwables) {
                Log.e("gagal",throwables.toString());
            }
        }
    }

    private void logout(){
        Amplify.Auth.signOut(
                () -> {
                    SharedPreferences sharedPreferences= getSharedPreferences("User", 0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear().commit();

//                    Toast.makeText(getContext(),"Logout Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );

    }
}