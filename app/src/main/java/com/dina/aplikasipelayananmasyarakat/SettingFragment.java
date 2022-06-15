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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.divider.MaterialDivider;

public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("User",0);
        int role=sharedPreferences.getInt("role",1);

        LinearLayout editProfile,akte,logout;
        View divider;

        editProfile=view.findViewById(R.id.ll_edit_profile);
        akte=view.findViewById(R.id.ll_akte);
        logout=view.findViewById(R.id.ll_logout);
        divider=view.findViewById(R.id.divider7);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),EditProfileActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        akte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AkteActivity.class));
            }
        });

        if (role>1){
            akte.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }

        return view;
    }

    private void logout(){
        Amplify.Auth.signOut(
                () -> {
                    SharedPreferences sharedPreferences= getContext().getSharedPreferences("User", 0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear().commit();

//                    Toast.makeText(getContext(),"Logout Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    getActivity().finish();
                    },
                error -> Log.e("AuthQuickstart", error.toString())
        );

    }
}