package com.dina.aplikasipelayananmasyarakat;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderFileFragment extends Fragment {

    Order order;
    ImageView ivKtp,ivKk;
    Uri uriKtp,uriKk;

    public OrderFileFragment(Order order) {
        this.order=order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_order_file, container, false);
        ivKtp=view.findViewById(R.id.iv_ktp_file);
        ivKk=view.findViewById(R.id.iv_kk_file);

        StorageReference ktpRef= FirebaseStorage.getInstance().getReference("ktp/"+order.getKtp());
        StorageReference kkRef= FirebaseStorage.getInstance().getReference().child("kk/"+order.getKk());

        uriKtp=Uri.parse(order.getKtp());
        uriKk=Uri.parse(order.getKk());

        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Mengambil Gambar...");
        progress.setCancelable(false);
        progress.show();

        try {
            final File fileKtp=File.createTempFile("tempImage","jpeg");
            final File fileKk=File.createTempFile("tempImage","jpeg");

            ktpRef.getFile(fileKtp)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapKtp= BitmapFactory.decodeFile(fileKtp.getAbsolutePath());
                            ivKtp.setImageBitmap(bitmapKtp);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            });

            kkRef.getFile(fileKk)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmapKk= BitmapFactory.decodeFile(fileKk.getAbsolutePath());
                            ivKk.setImageBitmap(bitmapKk);
                            progress.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            });
        } catch (IOException e) {
            Toast.makeText(getContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return view;
    }
}