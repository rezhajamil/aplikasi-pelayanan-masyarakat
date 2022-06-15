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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AkteAdapter extends RecyclerView.Adapter<AkteAdapter.ViewHolder>{

    ArrayList<String> akte;
    AkteActivity akteActivity;
    String email;

    public AkteAdapter(AkteActivity akteActivity, ArrayList<String> akte, String userEmail) {
        this.akteActivity=akteActivity;
        this.akte=akte;
        this.email=userEmail;
    }

    @NonNull
    @Override
    public AkteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(akteActivity).inflate(R.layout.akte_item, parent, false);

        return new AkteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AkteAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(akte.get(position));

        AlertDialog.Builder alert=new AlertDialog.Builder(akteActivity).setTitle("Hapus Akte");
        alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    ConnectionHelper connectionHelper=new ConnectionHelper();
                    Connection connect=connectionHelper.connections();
                    ResultSet rs=null;

                    if (connect == null){
                        Toast.makeText(akteActivity,"Check Your Connection",Toast.LENGTH_LONG).show();
                    }else{
                        String query="Delete from akte where nama_bayi='"+akte.get(position)+"' and user_email='"+email+"'";
                        Statement stmt=connect.createStatement();
                        rs=stmt.executeQuery(query);

                        rs.next();

                        akte.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();

                        Toast.makeText(akteActivity,"Akte Dihapus",Toast.LENGTH_SHORT).show();
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
        return (akte != null) ? akte.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_akte_name);
            btnDelete=itemView.findViewById(R.id.btn_delete_akte);
        }
    }
}
