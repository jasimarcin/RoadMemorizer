package com.marcin.jasi.roadmemorizer.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.CurrentLocationFragment;
import com.marcin.jasi.roadmemorizer.databinding.MainActivityBinding;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;
import com.marcin.jasi.roadmemorizer.general.view.appToolbar.AppToolbarData;
import com.marcin.jasi.roadmemorizer.main.di.DaggerMainActivityComponent;
import com.marcin.jasi.roadmemorizer.main.di.MainActivityComponent;
import com.marcin.jasi.roadmemorizer.main.di.MainActivityModel;
import com.marcin.jasi.roadmemorizer.roadsArchive.RoadsArchiveFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static com.marcin.jasi.roadmemorizer.general.Constants.REQUEST_CODE_ASK_PERMISSION;

@PerActivityScope
public class MainActivity extends AppCompatActivity {

    public static final int CURRENT_LOCATION_ID = R.id.currentLocationFragment;
    public static final int ROADS_ARCHIVE_ID = R.id.roadsArchiveFragment;

    @Inject
    LocationTrackerMediator locationTrackerMediator;
    private MainActivityComponent component;

    private MainActivityBinding binding;
    private PermissionHelper permissionHelper = new PermissionHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDependencies();
        setupBinding();
    }

    private void initDependencies() {
        component = DaggerMainActivityComponent
                .builder()
                .applicationComponent(((Application) getApplication()).getApplicationComponent())
                .mainActivityModel(new MainActivityModel(this))
                .build();

        component.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationTrackerMediator
                .getCurrentLocationPermissionState()
                .set(permissionHelper.getPermission(android.Manifest.permission.ACCESS_FINE_LOCATION));
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSION:
                for (String permission : permissions) {
                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        handleChangeLocationPermission(grantResults[0]);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void handleChangeLocationPermission(int grantResult) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            setGrantedPermissions(true);
        } else {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, R.string.localizationPermission,
                    Toast.LENGTH_LONG).show());

            setGrantedPermissions(false);
        }
    }

    private void setGrantedPermissions(boolean b) {
        locationTrackerMediator.getCurrentLocationPermissionState().set(b);
        locationTrackerMediator.getLocationPermissionChange().onNext(b);
    }

    @Override
    protected void onDestroy() {
        dispose();
        super.onDestroy();
    }

    public MainActivityComponent getComponent() {
        return component;
    }

}
