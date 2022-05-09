package com.rezha.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Orders;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment());
        NavigationBarView bottom_nav=findViewById(R.id.bottom_navigation);

        bottom_nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.setting:
                    replaceFragment(new SettingFragment());
                    break;
            }

            return true;
        });
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");

        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }

        Amplify.DataStore.observe(Orders.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );

//        Amplify.Auth.signInWithSocialWebUI(AuthProvider.google(), this,
//                result -> {
//                    Log.v("Hasil",result.toString());
//                    if (result.isSignInComplete()){
//                        Amplify.Auth.fetchUserAttributes(
//                                attributes->{
//                                    String email=attributes.get(3).getValue();
//                                    if (!email.isEmpty()){
//                                        int total=getUserData(email);
//                                        if (total<=0){
//                                            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
//                                            finish();
//                                        }
//                                    }else{
//                                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
//                                        finish();
//                                    }
//                                    progress.dismiss();
//                                },
//                                error -> {
//                                    Log.e("AuthDemo", "Failed to fetch user attributes.", error);
//                                    progress.dismiss();
//                                }
//                        );
//                    }else{
//                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
//                        finish();
//                    }
//                },
//                error -> {
//                    Log.e("AuthQuickstart", error.toString());
//                    progress.dismiss();
//                }
//        );

        Amplify.Auth.fetchAuthSession(
                result -> {
                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
                    switch(cognitoAuthSession.getIdentityId().getType()) {
                        case SUCCESS:
                            Amplify.Auth.fetchUserAttributes(
                                    attributes->{
                                    String email=attributes.get(3).getValue();
                                    if (!email.isEmpty()){
                                        int total=getUserData(email);
                                        if (total<=0){
                                            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                                            finish();
                                        }
                                    }else{
                                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                        finish();
                                    }
                                    progress.dismiss();
                                },
                                    error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
                            );
                            Log.i("Session", "IdentityId: " + cognitoAuthSession.toString());
                            break;
                        case FAILURE:
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();
                            Log.i("Session", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
                    }
                },
                error -> Log.e("Session", error.toString())
        );
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
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