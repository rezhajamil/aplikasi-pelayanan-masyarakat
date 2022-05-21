package com.dina.aplikasipelayananmasyarakat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);

        Button btnLogout=(Button) view.findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return view;
    }

    private void logout(){
        Amplify.Auth.signOut(
                () -> Log.i("AuthQuickstart", "Signed out successfully"),
                error -> Log.e("AuthQuickstart", error.toString())
        );

        SharedPreferences sharedPreferences= getContext().getSharedPreferences("User", 0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear().commit();

        startActivity(new Intent(getContext(),LoginActivity.class));
        getActivity().finish();

        Toast.makeText(getContext(),"Logout Success",Toast.LENGTH_LONG).show();
    }
}