package com.dina.aplikasipelayananmasyarakat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    Boolean isFABOpen=false;
    String query;
    ArrayList<Order> listOrder=new ArrayList<>();
    OrderAdapter adapter;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_order, container, false);

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        Button btnOrder1 = view.findViewById(R.id.btn_add_order1);
        ExtendedFloatingActionButton btnOrder2 = view.findViewById(R.id.btn_add_order2);
        ExtendedFloatingActionButton btnAkte = view.findViewById(R.id.btn_add_akte);
        RecyclerView rvOrder=view.findViewById(R.id.rv_order);
        TextView tvOrderTitle=view.findViewById(R.id.tv_order_title);
        TextView tvNull=view.findViewById(R.id.tv_null);


        Animation rotateOpenAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_animation);
        Animation rotateCloseAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_close_animation);
        Animation fromBottomAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.from_bottom_animation);
        Animation toBottomAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.to_bottom_animation);

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("User",0);
        String userEmail = sharedPreferences.getString("email","");
        String userName = sharedPreferences.getString("name","");
        int userRole = sharedPreferences.getInt("role",1);

        if (userRole==2||userRole==3||userRole==4){
            btnOrder1.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }

        Log.v("gagal3",String.valueOf(listOrder));

        btnOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),OrderFormActivity.class));
            }
        });

        btnOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),OrderFormActivity.class));
            }
        });

        btnAkte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AkteFormActivity.class));
            }
        });

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
                fab.startAnimation(rotateOpenAnimation);
                btnOrder2.setVisibility(View.VISIBLE);
                btnAkte.setVisibility(View.VISIBLE);
                btnOrder2.startAnimation(fromBottomAnimation);
                btnAkte.startAnimation(fromBottomAnimation);
            }

            private void closeFABMenu(){
                isFABOpen=false;
                fab.startAnimation(rotateCloseAnimation);
                btnOrder2.setVisibility(View.INVISIBLE);
                btnAkte.setVisibility(View.INVISIBLE);
                btnOrder2.startAnimation(toBottomAnimation);
                btnAkte.startAnimation(toBottomAnimation);
            }
        });

        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Sedang Mengambil Data...");
        progress.setCancelable(false);
        progress.show();

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect=connectionHelper.connections();
            ResultSet rs=null;

            if (connect == null){
                Toast.makeText(getContext(),"Check Your Connection",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }else{
                if (userRole==1){
                    query="Select * from orders where user_email='"+userEmail+"'";
                }else if (userRole==2){
                    query="Select * from orders where status='Menunggu Kepala Dusun'";
                }else  if (userRole==3){
                    query="Select * from orders where status='Disetujui oleh Kepala Dusun'";
                }else {
                    query="Select * from orders";
                }
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);


                Log.v("gagal4",String.valueOf(listOrder));
                while (rs.next()){
                    Date date=null;
                    if (rs.getDate("created_at")!=null){
                        date=new Date(rs.getDate("created_at").getTime());
                    }

                    listOrder.add(new Order(
                       rs.getString("id"),
                       rs.getString("user_email"),
                       rs.getString("purpose"),
                       rs.getString("ktp"),
                       rs.getString("kk"),
                       rs.getString("status"),
                       rs.getString("description"),
                       rs.getString("akte"),
                        date
                    ));
                    Log.v("gagal6",String.valueOf(listOrder.get(0).getUser_email()));
                }

                if (!listOrder.isEmpty()){
                    tvOrderTitle.setVisibility(View.VISIBLE);
                    if (userRole==1){
                        tvOrderTitle.setText("Daftar Permohonan oleh "+userName);
                    }else if (userRole==2){
                        tvOrderTitle.setText("Daftar Permohonan dari masyarakat");
                    }else if (userRole==3){
                        tvOrderTitle.setText("Daftar Permohonan yang disetujui Kepala Dusun");
                    }else {
                        tvOrderTitle.setText("Daftar Permohonan");
                    }

                    rvOrder.setVisibility(View.VISIBLE);
                    tvNull.setVisibility(View.GONE);
                    btnOrder1.setVisibility(View.GONE);
                }

                progress.dismiss();
                connect.close();
            }
        } catch (Exception e) {
            Log.e("gagal", String.valueOf(e));
            progress.dismiss();
            e.printStackTrace();
        }

        adapter=new OrderAdapter(getContext(),listOrder);
        Log.v("gagal1", String.valueOf(listOrder));
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return  view;

    }


}