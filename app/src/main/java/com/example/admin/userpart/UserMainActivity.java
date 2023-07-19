package com.example.admin.userpart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.userpart.ui.ebook.EbookFragment;
import com.example.admin.userpart.ui.routine.RoutineFragment;
import com.example.admin.userpart.ui.gallary.GallaryFragment;
import com.example.admin.userpart.ui.home.HomeFragment;
import com.example.admin.userpart.ui.notice.NoticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton home;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_user_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        home = findViewById(R.id.home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawernavigationiew);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);




        bottomNavigationView.setBackground(null);
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

                case R.id.routine:
                    replaceFragment(new RoutineFragment());
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.faculty:
                Toast.makeText(this, "faculty", Toast.LENGTH_SHORT).show();
                break;

            case R.id.department:
                Toast.makeText(this, "Department", Toast.LENGTH_SHORT).show();
                break;

            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;

            case R.id.themes:
                Toast.makeText(this, "themes", Toast.LENGTH_SHORT).show();
                break;


            case R.id.share:
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.developer:
                Toast.makeText(this, "developer", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}