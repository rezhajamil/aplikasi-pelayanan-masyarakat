package com.dina.aplikasipelayananmasyarakat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        Button btnOrder1 = (Button) view.findViewById(R.id.btn_add_order1);
        ExtendedFloatingActionButton btnOrder2 = (ExtendedFloatingActionButton) view.findViewById(R.id.btn_add_order2);
        ExtendedFloatingActionButton btnAkte = (ExtendedFloatingActionButton) view.findViewById(R.id.btn_add_akte);

        Animation rotateOpenAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_animation);
        Animation rotateCloseAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_close_animation);
        Animation fromBottomAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.from_bottom_animation);
        Animation toBottomAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.to_bottom_animation);

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
//                btnOrder2.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
//                btnAkte.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
                fab.startAnimation(rotateOpenAnimation);
                btnOrder2.setVisibility(View.VISIBLE);
                btnAkte.setVisibility(View.VISIBLE);
                btnOrder2.startAnimation(fromBottomAnimation);
                btnAkte.startAnimation(fromBottomAnimation);
            }

            private void closeFABMenu(){
                isFABOpen=false;
//                btnOrder2.animate().translationY(0);
//                btnAkte.animate().translationY(0);
                fab.startAnimation(rotateCloseAnimation);
                btnOrder2.setVisibility(View.INVISIBLE);
                btnAkte.setVisibility(View.INVISIBLE);
                btnOrder2.startAnimation(toBottomAnimation);
                btnAkte.startAnimation(toBottomAnimation);
            }
        });

        return  view;
    }


}