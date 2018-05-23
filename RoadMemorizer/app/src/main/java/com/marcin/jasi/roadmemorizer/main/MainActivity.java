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
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
