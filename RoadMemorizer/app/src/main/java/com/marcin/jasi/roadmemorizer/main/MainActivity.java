package com.marcin.jasi.roadmemorizer.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marcin.jasi.roadmemorizer.R;

import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        NavHostFragment.findNavController(this).navigate(R.id.action_currentLocationFragment_to_mainActivity);

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
