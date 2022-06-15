package com.dina.aplikasipelayananmasyarakat;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderInfoFragment extends Fragment {

    TextView tvPurpose,tvName,tvNik,tvBirth,tvAddress,tvOccupation;
    String purpose,name,nik,birthPlace,birthDate,address,occupation;
    Order order;
    ArrayList<String> user;

    public OrderInfoFragment(Order order, ArrayList<String> user) {
        this.order=order;
        this.user=user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_order_info, container, false);

        SharedPreferences sharedPreferences= getContext().getSharedPreferences("User",0);

        tvPurpose=view.findViewById(R.id.tv_purpose_info);
        tvName=view.findViewById(R.id.tv_nama_info);
        tvNik=view.findViewById(R.id.tv_nik_info);
        tvBirth=view.findViewById(R.id.tv_birth_info);
        tvAddress=view.findViewById(R.id.tv_address_info);
        tvOccupation=view.findViewById(R.id.tv_occupation_info);

        tvPurpose.setText(order.getPurpose());
        tvName.setText(user.get(1));
        tvNik.setText(user.get(2));
        tvBirth.setText(user.get(3)+"/"+user.get(4));
        tvAddress.setText(user.get(5));
        tvOccupation.setText(user.get(6));

        return view;
    }
}