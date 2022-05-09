package com.rezha.aplikasipelayananmasyarakat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Boolean isFABOpen=false;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        ExtendedFloatingActionButton btnOrder2 = (ExtendedFloatingActionButton) view.findViewById(R.id.btn_add_order2);
        ExtendedFloatingActionButton btnAkte = (ExtendedFloatingActionButton) view.findViewById(R.id.btn_add_akte);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }

            private void showFABMenu(){
                isFABOpen=true;
                btnOrder2.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
                btnAkte.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
            }

            private void closeFABMenu(){
                isFABOpen=false;
                btnOrder2.animate().translationY(0);
                btnAkte.animate().translationY(0);
            }
        });

        return  view;
    }


}