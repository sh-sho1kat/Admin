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
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.departments.facultymembers.FacultyInformation;
import com.example.admin.adminpart.faculty.update_faculty;
import com.example.admin.adminpart.students.StudentData;
import com.example.admin.databinding.ActivityLandingPageBinding;
import com.example.admin.userpart.developer.developer;
import com.example.admin.userpart.login.UserLoginActivity;
import com.example.admin.userpart.profile.MyProfile;
import com.example.admin.userpart.rating.RateUsDialogue;
import com.example.admin.userpart.ui.ebook.EbookFragment;
import com.example.admin.userpart.ui.routine.RoutineFragment;
import com.example.admin.userpart.ui.gallary.GallaryFragment;
import com.example.admin.userpart.ui.home.HomeFragment;
import com.example.admin.userpart.ui.notice.NoticeFragment;
import com.example.admin.userpart.userfaculty.DisplayFacultyInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.security.acl.Group;
import java.util.Objects;

public class UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton home;
    private boolean isMediaVisible= false;
    Intent intent;
    //private String UserId = getIntent().getStringExtra("userID");
    private ImageView circle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        
        getStudentINfo();

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                    String dept="CSE";
                    Bundle bundle = new Bundle();
                    bundle.putString("dept", dept);
                    RoutineFragment routineFragment = new RoutineFragment();
                    routineFragment.setArguments(bundle);
                    replaceFragment(routineFragment);
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

    private void getStudentINfo() {
        NavigationView navigationView1 =findViewById(R.id.drawernavigationiew);
        View HeaderView = navigationView1.getHeaderView(0);
        TextView progileId = HeaderView.findViewById(R.id.profile_id);
        ImageView profileImage = HeaderView.findViewById(R.id.profile_image);
        String id = getIntent().getStringExtra("userID");
        String imageurl =getIntent().getStringExtra("imageURL");
        Picasso.get().load(imageurl).into(profileImage);
        progileId.setText(id);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(UserMainActivity.this, MyProfile.class);
                intent1.putExtra("StudentID",getIntent().getStringExtra("userID"));
                startActivity(intent1);
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
                intent = new Intent(UserMainActivity.this, DisplayFacultyInfo.class);
                startActivity(intent);
                break;

            case R.id.department:
                break;

            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rating:
                RateUsDialogue rateUsDialogue = new RateUsDialogue(UserMainActivity.this);
                rateUsDialogue.setCancelable(false);
                rateUsDialogue.show();
                break;

            case R.id.share:
                shareapp();
                break;

            case R.id.developer:
                intent = new Intent(UserMainActivity.this, developer.class);
                startActivity(intent);
                break;

            case R.id.logout:
                intent = new Intent(UserMainActivity.this, UserLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return true;
    }

    private void shareapp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"https://console.firebase.google.com/project/university-management-sy-fb6cf/database/university-management-sy-fb6cf-default-rtdb/data");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share Via"));
    }
}