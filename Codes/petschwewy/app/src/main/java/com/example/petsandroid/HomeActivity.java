package com.example.petsandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mfire;




    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // Toolbar toolbar = findViewById(R.id.toolbar);
//       setSupportActionBar(toolbar);


        View header;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        ImageView nav_head_image = header.findViewById(R.id.imageView);
        TextView headerTitle = header.findViewById(R.id.headertitle);
        TextView headerSubtitle = header.findViewById(R.id.headersubtitle);

        nav_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(FirebaseAuth.getInstance()!=null) {

                    Toast.makeText(HomeActivity.this, "clicked on nav imgae header", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, ProfileSettingsActivity.class));

                }
                else{
                    Toast.makeText(HomeActivity.this, "clicked on nav imgae header", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));

                }
            }
        });








        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow,
                R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /*NavInflater inflater = navController.getNavInflater();
        NavGraph graph;
        if (isUserLoggedIn) {
            graph = inflater.inflate(R.navigation.mobile_navigation);
        } else {
            graph = inflater.inflate(R.navigation.mobile_navigation_duplicate);
        }*/
        //navController.setGraph(graph);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Successfully logged out of the app", Toast.LENGTH_LONG).show();
                //recreate();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}