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
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfileActivity extends AppCompatActivity {
    EditText etNameEdit,etEmailEdit,etPhoneEdit,etNikEdit,etAddressEdit,etBirthDateEdit,etBirthPlaceEdit,etOccupationEdit;
    Button btnSubmitEdit;
    int role;
    String email,name,phone,nik,address,birthDate,birthPlace,occupation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        email=sharedPreferences.getString("email","");
        name=sharedPreferences.getString("name","");
        phone=sharedPreferences.getString("phone","");
        nik=sharedPreferences.getString("nik","");
        address=sharedPreferences.getString("address","");
        birthDate=sharedPreferences.getString("birthDate","");
        birthPlace=sharedPreferences.getString("birthPlace","");
        occupation=sharedPreferences.getString("occupation","");
        role=sharedPreferences.getInt("role",1);

        etNameEdit=findViewById(R.id.etNameEdit);
        etEmailEdit=findViewById(R.id.etEmailEdit);
        etPhoneEdit=findViewById(R.id.etPhoneEdit);
        etNikEdit=findViewById(R.id.etNikEdit);
        etAddressEdit=findViewById(R.id.etAddressEdit);
        etBirthDateEdit=findViewById(R.id.etBirthDateEdit);
        etBirthPlaceEdit=findViewById(R.id.etBirthPlaceEdit);
        etOccupationEdit=findViewById(R.id.etOccupationEdit);
        btnSubmitEdit=findViewById(R.id.btnSubmitEdit);

        etNameEdit.setText(name);
        etEmailEdit.setText(email);
        etPhoneEdit.setText(phone);
        etNikEdit.setText(nik);
        etAddressEdit.setText(address);
        etBirthDateEdit.setText(birthDate);
        etBirthPlaceEdit.setText(birthPlace);
        etOccupationEdit.setText(occupation);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal").build();

        etBirthDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                etBirthDateEdit.setText(datePicker.getHeaderText());
            }
        });

//        Amplify.Auth.fetchUserAttributes(
//                attributes->{
//                    String email=attributes.get(3).getValue();
//                    etEmailEdit.setText(email);
//                },
//                error -> Log.v("Error","Failed to Fetch Email")
//        );

        btnSubmitEdit.setOnClickListener(view -> editUser());
    }

    private void editUser() {
        if (validateInput()){
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();
            try{
                if (connect==null){
                    String ConnectionResult="Check Your Internet Connection";
                    Toast.makeText(EditProfileActivity.this,ConnectionResult,Toast.LENGTH_LONG).show();
                }else{
                    String query=String.format("Update users set email='%s',phone='%s',nik='%s',name='%s',address='%s',birthdate='%s',birthplace='%s',occupation='%s' where email='"+etEmailEdit.getText()+"'",etEmailEdit.getText(),etPhoneEdit.getText(),etNikEdit.getText(),etNameEdit.getText(),etAddressEdit.getText(),etBirthDateEdit.getText(),etBirthPlaceEdit.getText(),etOccupationEdit.getText(),role);
                    Statement stmt=connect.createStatement();
                    Log.v("gagal",query);
                    stmt.executeUpdate(query);

                    SharedPreferences sharedPreferences=getSharedPreferences("User",0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putString("email", String.valueOf(etEmailEdit.getText()));
                    editor.putString("phone", String.valueOf(etPhoneEdit.getText()));
                    editor.putString("nik", String.valueOf(etNikEdit.getText()));
                    editor.putString("name", String.valueOf(etNameEdit.getText()));
                    editor.putString("address", String.valueOf(etAddressEdit.getText()));
                    editor.putString("birthDate", String.valueOf(etBirthDateEdit.getText()));
                    editor.putString("birthPlace", String.valueOf(etBirthPlaceEdit.getText()));
                    editor.putString("avatar",null);
                    editor.putString("occupation", String.valueOf(etOccupationEdit.getText()));
                    editor.putInt("role",role);
                    editor.apply();

                    Toast.makeText(EditProfileActivity.this,"Profile Telah Diedit",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
                    finish();
                    connect.close();
                }
            } catch (SQLException throwables) {
                Log.e("gagal",throwables.toString());
            }
        }
    }

    private boolean validateInput(){
        if (etNameEdit.length()==0){
            etNameEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etEmailEdit.length()==0){
            etEmailEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etPhoneEdit.length()==0){
            etPhoneEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etNikEdit.length()==0){
            etNikEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etAddressEdit.length()==0){
            etAddressEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etBirthDateEdit.length()==0){
            etBirthDateEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etBirthPlaceEdit.length()==0){
            etBirthPlaceEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }

        if (etOccupationEdit.length()==0){
            etOccupationEdit.setError("Field Ini Tidak Boleh Kosong");
            return false;
        }
        return true;
    }
}