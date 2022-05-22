package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        String userEmail = sharedPreferences.getString("email","");

        replaceFragment(new HomeFragment());
        NavigationBarView bottom_nav=findViewById(R.id.bottom_navigation);

        Log.v("role", String.valueOf(sharedPreferences.getInt("role",1)));
        if (sharedPreferences.getInt("role",1)!=4){
            bottom_nav.getMenu().removeItem(R.id.user);
        }

        bottom_nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.user:
                    replaceFragment(new UserFragment());
                    break;
                case R.id.setting:
                    replaceFragment(new SettingFragment());
                    break;
            }

            return true;
        });
//        ProgressDialog progress = new ProgressDialog(this);
//        progress.setTitle("Loading");
//        progress.setMessage("Wait while loading...");
//        progress.setCancelable(false);
//        progress.show();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
//             Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");

        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }

        if (userEmail.equals("")){
            Log.v(" main",sharedPreferences.getString("email","sa"));

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
    }

}