package com.dina.aplikasipelayananmasyarakat;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class UserFragment extends Fragment {

    ArrayList<User> listUsers;
    String query;
    UserAdapter adapter;
    RecyclerView rvUser;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        rvUser=view.findViewById(R.id.rv_user);

        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Sedang Mengambil Data...");
        progress.setCancelable(false);
        progress.show();

        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect=connectionHelper.connections();
            ResultSet rs=null;
            listUsers=new ArrayList<User>();

            if (connect == null){
                Toast.makeText(getContext(),"Check Your Connection",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }else{
                query="Select * from users where role < 4";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                while (rs.next()){
                    listUsers.add(new User(
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getInt("role")
                    ));
                }
                Log.v("daftar user", String.valueOf(listUsers));

                progress.dismiss();
                connect.close();
            }
        } catch (Exception e) {
            Log.e("gagal", String.valueOf(e));
            progress.dismiss();
            e.printStackTrace();
        }

        adapter=new UserAdapter(getContext(),listUsers);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        rvUser.setLayoutManager(layoutManager);
        rvUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}