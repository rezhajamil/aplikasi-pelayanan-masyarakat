package com.dina.aplikasipelayananmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    String fragmentExtra="permohonan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences("User",0);
        String userEmail = sharedPreferences.getString("email","");
        int userRole = sharedPreferences.getInt("role",1);

        Intent intent=getIntent();
        if (intent.hasExtra("fragment")){
            fragmentExtra=intent.getStringExtra("fragment");
        }

        NavigationBarView bottom_nav=findViewById(R.id.bottom_navigation);

        if (fragmentExtra.equals("setting")){
            replaceFragment(new SettingFragment());
            bottom_nav.setSelectedItemId(R.id.setting);
        }else{
            replaceFragment(new OrderFragment());
        }


        Log.v("userEmail", String.valueOf(userEmail));
        Log.v("userRole", String.valueOf(userRole));
        if (sharedPreferences.getInt("role",1)!=4){
            bottom_nav.getMenu().removeItem(R.id.user);
        }

        bottom_nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new OrderFragment());
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