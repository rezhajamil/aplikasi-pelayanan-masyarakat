package com.dina.aplikasipelayananmasyarakat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{


    private Context context;
//    String id,purpose,user_email,ktp,kk,status,description,akte;
//    Date created_at;
    ArrayList<Order> data;


    public OrderAdapter(Context ct, ArrayList<Order> orders) {
        context=ct;
        data=orders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPurpose,tvName,tvDate,tvStatus;
        MaterialCardView card;
        Button btnDetail,btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPurpose=itemView.findViewById(R.id.tv_purpose);
            tvName=itemView.findViewById(R.id.tv_name);
            tvDate=itemView.findViewById(R.id.tv_date);
            tvStatus=itemView.findViewById(R.id.tv_status);
            btnDetail=itemView.findViewById(R.id.btn_detail);
            btnDelete=itemView.findViewById(R.id.btn_delete);
            card=itemView.findViewById(R.id.card);
        }
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
        String name = null;
        String date="";
        SharedPreferences sharedPreferences= context.getSharedPreferences("User",0);
        int userRole=sharedPreferences.getInt("role",1);

        if (userRole==2||userRole==3){
            holder.btnDelete.setVisibility(View.GONE);
        }

        if (data.get(position).getCreated_at()!=null){
            date=formatter.format(data.get(position).getCreated_at());
        }
        try {
            ConnectionHelper connectionHelper=new ConnectionHelper();
            Connection connect=connectionHelper.connections();
            ResultSet rs=null;

            if (connect == null){
                Toast.makeText(context,"Check Your Connection",Toast.LENGTH_LONG).show();
            }else{
                String query="Select * from users where email='"+data.get(position).getUser_email()+"'";
                Statement stmt=connect.createStatement();
                rs=stmt.executeQuery(query);

                rs.next();
                name=rs.getString("name");
                connect.close();
            }
        } catch (Exception e) {
            Log.e("gagal",String.valueOf(e));
            e.printStackTrace();
        }
        holder.tvName.setText(name);
        holder.tvPurpose.setText(data.get(position).getPurpose());
        holder.tvDate.setText(date);
        holder.tvStatus.setText(data.get(position).getStatus());
        String arr[]=data.get(position).getStatus().split(" ",2);
        if (arr[0]=="Ditolak"){
            holder.tvStatus.setTextColor(Color.parseColor("#b00020"));
        }

        if (data.get(position).getStatus().equals("Disetuji oleh Kepala Desa")){
            holder.card.setChecked(true);
        }

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,OrderDetailActivity.class).putExtra("order",data).putExtra("pos",position));
            }
        });


        AlertDialog.Builder alert=new AlertDialog.Builder(context).setTitle("Hapus Permohonan");
        alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    ConnectionHelper connectionHelper=new ConnectionHelper();
                    Connection connect=connectionHelper.connections();
                    ResultSet rs=null;

                    if (connect == null){
                        Toast.makeText(context,"Check Your Connection",Toast.LENGTH_LONG).show();
                    }else{
                        String query="Delete from orders where id='"+data.get(position).getId()+"'";
                        Statement stmt=connect.createStatement();
                        rs=stmt.executeQuery(query);

                        rs.next();

                        data.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();

                        Toast.makeText(context,"Permohonan Dihapus",Toast.LENGTH_SHORT).show();
                        connect.close();
                    }
                } catch (Exception e) {
                    Log.e("gagal",String.valueOf(e));
                    e.printStackTrace();
                }
            }
        });

        alert.setNeutralButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create();

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() : 0;
    }
}
