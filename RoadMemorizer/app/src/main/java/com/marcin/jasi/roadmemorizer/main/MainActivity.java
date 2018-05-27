package com.marcin.jasi.roadmemorizer.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.currentLocation.CurrentLocationFragment;
import com.marcin.jasi.roadmemorizer.databinding.MainActivityBinding;
import com.marcin.jasi.roadmemorizer.general.view.appToolbar.AppToolbarData;
import com.marcin.jasi.roadmemorizer.roadsArchive.RoadsArchiveFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    public static final int CURRENT_LOCATION_ID = R.id.currentLocationFragment;
    public static final int ROADS_ARCHIVE_ID = R.id.roadsArchiveFragment;

    private MainActivityBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBinding();
    }

    private void setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.toolbar.setItems(getFragmentsList());
        binding.toolbar.setClickListener(item -> handleToolbarSelect(item.getId()));
    }

    private void handleToolbarSelect(int id) {
        switch (id) {
            case CURRENT_LOCATION_ID:
                getNavigation().navigate(R.id.action_global_currentLocationFragment);
                break;
            case ROADS_ARCHIVE_ID:
                getNavigation().navigate(R.id.action_global_roadsArchiveFragment);
                break;
        }
    }

    @NonNull
    private NavController getNavigation() {
        return Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @NonNull
    private List<AppToolbarData> getFragmentsList() {
        List<AppToolbarData> dataList = new ArrayList<>();
        dataList.add(new AppToolbarData(CURRENT_LOCATION_ID, CurrentLocationFragment.TITLE));
        dataList.add(new AppToolbarData(ROADS_ARCHIVE_ID, RoadsArchiveFragment.TITLE));
        return dataList;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return getNavigation().navigateUp();
    }

    public void setHeader(String header) {
        binding.toolbar.setHeader(header);
    }

    private void dispose() {
        binding.toolbar.dispose();
    }

}
