package com.example.admin.userpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.admin.EbookFragment;
import com.example.admin.R;
import com.example.admin.userpart.ui.faculty.FacultyFragment;
import com.example.admin.userpart.ui.gallary.GallaryFragment;
import com.example.admin.userpart.ui.home.HomeFragment;
import com.example.admin.userpart.ui.notice.NoticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserMainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private FloatingActionButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        replaceFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        home = findViewById(R.id.home);
        //navController = Navigation.findNavController(this,R.id.frame_layout);
        bottomNavigationView.setBackground(null);
        //NavigationUI.setupWithNavController(bottomNavigationView,navController);
        bottomNavigationView.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.ebook:
                    replaceFragment(new EbookFragment());
                    break;

                case R.id.gallary:
                    replaceFragment(new GallaryFragment());
                    break;

                case R.id.faculty:
                    replaceFragment(new FacultyFragment());
                    break;

                case R.id.notice:
                    replaceFragment(new NoticeFragment());
                    break;

            }
            return true;
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}