package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
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
                                        int total=getUserData(email);
                                        if (total<=0){
                                            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                                            finish();
                                        }
                                    }else{
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();
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

    private int getUserData(String email){
        ResultSet rs=null;
        int total=0;
        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect =connectionHelper.connections();

            if (connect==null){
                String ConnectionResult="Check Your Internet Connection";
            }else{
                String query="Select count(*) as total from users where email="+email;
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                while (rs.next()){
                    total=rs.getInt("total");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }
}