package com.dina.aplikasipelayananmasyarakat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAkteFragment extends Fragment {

    ArrayList akte;
    TextView namaKK,noKK,namaBayi,genderBayi,tempatDilahirkan,tempatKelahiran,hariKelahiran,waktuKelahiran,infoKelahiran,ukuranBayi,nikIbu,namaIbu,usiaIbu,kerjaIbu,alamatIbu,kwIbu,tglKawin,nikAyah,namaAyah,usiaAyah,kerjaAyah,alamatAyah,kwAyah,nikPelapor,namaPelapor,usiaPelapor,kerjaPelapor,alamatPelapor,akteNull;
    LinearLayout layout;
    public OrderAkteFragment(ArrayList akte) {
        this.akte=akte;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_akte, container, false);
        namaKK = view.findViewById(R.id.tv_kepala_keluarga_akte);
        noKK = view.findViewById(R.id.tv_no_kk_akte);
        namaBayi = view.findViewById(R.id.tv_nama_bayi);
        genderBayi = view.findViewById(R.id.tv_gender_bayi);
        tempatDilahirkan = view.findViewById(R.id.tv_tempat_dilahirkan);
        tempatKelahiran = view.findViewById(R.id.tv_tempat_kelahiran);
        hariKelahiran = view.findViewById(R.id.tv_hari_kelahiran);
        waktuKelahiran = view.findViewById(R.id.tv_waktu_kelahiran);
        infoKelahiran = view.findViewById(R.id.tv_info_kelahiran);
        ukuranBayi = view.findViewById(R.id.tv_ukuran_bayi);
        nikIbu = view.findViewById(R.id.tv_nik_ibu);
        namaIbu = view.findViewById(R.id.tv_nama_ibu);
        usiaIbu = view.findViewById(R.id.tv_usia_ibu);
        kerjaIbu = view.findViewById(R.id.tv_kerja_ibu);
        alamatIbu = view.findViewById(R.id.tv_alamat_ibu);
        kwIbu = view.findViewById(R.id.tv_kw_ibu);
        tglKawin = view.findViewById(R.id.tv_tgl_kawin);
        nikAyah = view.findViewById(R.id.tv_nik_ayah);
        namaAyah = view.findViewById(R.id.tv_nama_ayah);
        usiaAyah = view.findViewById(R.id.tv_usia_ayah);
        kerjaAyah = view.findViewById(R.id.tv_kerja_ayah);
        alamatAyah = view.findViewById(R.id.tv_alamat_ayah);
        kwAyah = view.findViewById(R.id.tv_kw_ayah);
        nikPelapor = view.findViewById(R.id.tv_nik_pelapor);
        namaPelapor = view.findViewById(R.id.tv_nama_pelapor);
        usiaPelapor = view.findViewById(R.id.tv_usia_pelapor);
        kerjaPelapor = view.findViewById(R.id.tv_kerja_pelapor);
        alamatPelapor = view.findViewById(R.id.tv_alamat_pelapor);

        akteNull=view.findViewById(R.id.tv_akte_null_detail);
        layout=view.findViewById(R.id.layout_akte);

        if (akte.isEmpty()){
            layout.setVisibility(View.GONE);
            akteNull.setVisibility(View.VISIBLE);
        }else{
            namaKK.setText(akte.get(0).toString());
            noKK.setText(akte.get(1).toString());
            namaBayi.setText(akte.get(2).toString());
            genderBayi.setText(akte.get(3).toString());
            tempatDilahirkan.setText(akte.get(4).toString());
            tempatKelahiran.setText(akte.get(5).toString());
            hariKelahiran.setText(akte.get(6).toString());
            waktuKelahiran.setText(akte.get(7).toString()+" / "+akte.get(8).toString());
            infoKelahiran.setText(akte.get(9).toString()+" / "+akte.get(10).toString()+" / "+akte.get(11).toString());
            ukuranBayi.setText(akte.get(12).toString()+" kg / "+akte.get(13).toString()+" cm");
            nikIbu.setText(akte.get(14).toString());
            namaIbu.setText(akte.get(15).toString());
            usiaIbu.setText(akte.get(16).toString()+" / "+akte.get(17).toString());
            kerjaIbu.setText(akte.get(18).toString());
            alamatIbu.setText(akte.get(19).toString());
            kwIbu.setText(akte.get(20).toString());
            tglKawin.setText(akte.get(21).toString());
            nikAyah.setText(akte.get(22).toString());
            namaAyah.setText(akte.get(23).toString());
            usiaAyah.setText(akte.get(24).toString()+" / "+akte.get(25).toString());
            kerjaAyah.setText(akte.get(26).toString());
            alamatAyah.setText(akte.get(27).toString());
            kwAyah.setText(akte.get(28).toString());
            nikPelapor.setText(akte.get(29).toString());
            namaPelapor.setText(akte.get(30).toString());
            usiaPelapor.setText(akte.get(31).toString()+" / "+akte.get(32).toString());
            kerjaPelapor.setText(akte.get(33).toString());
            alamatPelapor.setText(akte.get(34).toString());

        }

        return view;
    }
}