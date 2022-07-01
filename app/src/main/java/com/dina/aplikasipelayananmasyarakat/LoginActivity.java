package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin=findViewById(R.id.btn_login);

//        try {
//            Amplify.addPlugin(new AWSApiPlugin());
////             Add this line, to include the Auth plugin.
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.configure(getApplicationContext());
//            Log.i("Tutorial", "Initialized Amplify");
//
//        } catch (AmplifyException failure) {
//            Log.e("Tutorial", "Could not initialize Amplify", failure);
//        }

        btnLogin.setOnClickListener(view -> login());
    }

    private void login(){
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

//        Amplify.addPlugin(new AWSCognitoAuthPlugin());
        Amplify.Auth.signInWithSocialWebUI(AuthProvider.google(), this,
                result -> {
                    if (result.isSignInComplete()){
                        Amplify.Auth.fetchUserAttributes(
                                attributes->{
                                    String email=attributes.get(3).getValue();
                                    if (!email.isEmpty()){
                                        int total=countUserData(email);
                                        if (total<=0){
                                            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                                            finish();
                                        }else{
                                            saveUserData(email);
//                                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();

                                            SharedPreferences sharedPreferences=getSharedPreferences("User",0);
                                            int role=sharedPreferences.getInt("role",1);
                                            if (role>1){
                                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            }else{
                                                startActivity(new Intent(LoginActivity.this,MenuActivity.class));
                                            }
                                            finish();
                                        }
                                    }
                                    progress.dismiss();
                                },
                                error -> {
                                    Log.e("AuthDemo", "Failed to fetch user attributes.", error);
                                    progress.dismiss();
                                }
                        );
                    }
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    progress.dismiss();
                }
        );
    }

    private int countUserData(String email){
        ResultSet rs=null;
        int total=0;
        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();

            if (connect==null){
                String ConnectionResult="Check Your Internet Connection";
            }else{
                String query="Select count(*) as total from users where email='"+email+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                rs.next();
                total=rs.getInt("total");

                connect.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    public void saveUserData(String email){
        ResultSet rs=null;
        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();
            Log.v("teks", String.valueOf(connect));

            if (connect==null){
                String ConnectionResult="Check Your Internet Connection";
            }else{
                String query="Select * from users where email='"+email+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                SharedPreferences sharedPreferences=getSharedPreferences("User",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                while (rs.next()){
                    editor.putString("email",rs.getString("email"));
                    editor.putString("phone",rs.getString("phone"));
                    editor.putString("nik",rs.getString("nik"));
                    editor.putString("name",rs.getString("name"));
                    editor.putString("address",rs.getString("address"));
                    editor.putString("birthDate",rs.getString("birthDate"));
                    editor.putString("birthPlace",rs.getString("birthPlace"));
                    editor.putString("avatar",rs.getString("avatar"));
                    editor.putString("occupation",rs.getString("occupation"));
                    editor.putInt("role",rs.getInt("role"));
                    editor.apply();

                    Log.v("total3",rs.getString("email"));
                    Log.v("total3",sharedPreferences.getString("email",""));
                }

                connect.close();
            }
        } catch (SQLException throwables) {
            Log.e("gagal", String.valueOf(throwables));
            throwables.printStackTrace();
        }
    }
}