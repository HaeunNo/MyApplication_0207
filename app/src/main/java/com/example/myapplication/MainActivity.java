package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import net.daum.mf.map.api.MapView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnView;
    private int currentFrag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnView = findViewById(R.id.bnView);

        // 메인액티비티의 기본 프레임 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment1()).commit();

        // 메뉴 버튼 누를 시 해당 프래그먼트로 이동
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        currentFrag = 1;
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment1()).commit();
                        break;
                    case R.id.tab2: //얘가 지도
                        if(currentFrag == 2){

                        }else{
                            currentFrag = 2;
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment_map()).commit();
                        }
                        break;
                    case R.id.tab3:
                        break;
                    case R.id.tab4:
                        break;
                }
                return true;
            }
        });
    }


}