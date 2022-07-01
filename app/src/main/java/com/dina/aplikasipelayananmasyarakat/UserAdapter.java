package com.dina.aplikasipelayananmasyarakat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    ArrayList<User> users;
    Context context;
    int newRole;
    public UserAdapter(Context context,ArrayList<User> users) {
        this.context=context;
        this.users=users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String userRole;
        String[] role={"Masyarakat","Kepala Dusun","Perangkat Desa"};
        int checkedItem=users.get(position).getRole()-1;
        MaterialAlertDialogBuilder dialogBuilder=new MaterialAlertDialogBuilder(context);
        dialogBuilder.setTitle("Ganti Role User")
                .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setSingleChoiceItems(role, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newRole=i+1;
                    }
                })
                .setPositiveButton("Ganti Role", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ConnectionHelper connectionHelper=new ConnectionHelper();
                            Connection connect=connectionHelper.connections();
                            ResultSet rs=null;

                            if (connect == null){
                                Toast.makeText(context,"Check Your Connection",Toast.LENGTH_LONG).show();
                            }else{
                                String query="Update users set role="+newRole+" where email='"+users.get(position).getEmail()+"'";
                                Statement stmt=connect.createStatement();
                                stmt.executeUpdate(query);
                                rs=stmt.executeQuery(query);

                                rs.next();
                                users.get(position).setRole(newRole);

                                Toast.makeText(context,"Role "+users.get(position).getEmail()+" diganti",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                                connect.close();
                            }
                        } catch (Exception e) {
                            Log.e("gagal",String.valueOf(e));
                            e.printStackTrace();
                        }
                    }
                }).create();

        switch (users.get(position).getRole()){
            case 1:
                userRole="Masyarakat";
                break;
            case 2:
                userRole="Kepala Dusun";
                break;
            case 3:
                userRole="Perangkat Desa";
                break;
            case 4:
                userRole="Admin";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + users.get(position).getRole());
        }

        holder.tvEmail.setText(users.get(position).getEmail());
        holder.tvName.setText(users.get(position).getName());
        holder.tvPhone.setText(users.get(position).getPhone());
        holder.tvRole.setText(userRole);
        holder.btnChangeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (users != null) ? users.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail,tvName,tvPhone,tvRole;
        Button btnChangeRole;
        public ViewHolder(@NonNull View view) {
            super(view);

            tvEmail=view.findViewById(R.id.tv_email);
            tvName=view.findViewById(R.id.tv_name_user);
            tvPhone=view.findViewById(R.id.tv_phone);
            tvRole=view.findViewById(R.id.tv_role);
            btnChangeRole=view.findViewById(R.id.btn_change_role);
        }
    }
}
