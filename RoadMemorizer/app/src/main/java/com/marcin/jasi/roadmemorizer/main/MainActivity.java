package com.marcin.jasi.roadmemorizer.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.currentLocation.CurrentLocationFragment;
import com.marcin.jasi.roadmemorizer.databinding.MainActivityBinding;
import com.marcin.jasi.roadmemorizer.general.view.appToolbar.AppToolbarData;
import com.marcin.jasi.roadmemorizer.locationTracker.LocationTrackerService;
import com.marcin.jasi.roadmemorizer.roadsArchive.RoadsArchiveFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static final int CURRENT_LOCATION_ID = R.id.currentLocationFragment;
    public static final int ROADS_ARCHIVE_ID = R.id.roadsArchiveFragment;

    private MainActivityBinding binding;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Timber.d("service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Timber.d("service disconnected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, LocationTrackerService.class));
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

    @Override
    protected void onStart() {
        bindService(new Intent(this, LocationTrackerService.class), serviceConnection, Context.BIND_AUTO_CREATE);

        super.onStart();
    }

    @Override
    protected void onStop() {
        unbindService(serviceConnection);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, LocationTrackerService.class));
        super.onDestroy();
    }

}
