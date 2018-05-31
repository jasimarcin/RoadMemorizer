package com.marcin.jasi.roadmemorizer.roadsArchive.presentation;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.databinding.RoadsArchiveFragmentBinding;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;
import com.marcin.jasi.roadmemorizer.general.common.presentation.LinearLayoutManagerWrapper;
import com.marcin.jasi.roadmemorizer.general.common.presentation.VerticalRecyclerDivider;
import com.marcin.jasi.roadmemorizer.main.MainActivity;
import com.marcin.jasi.roadmemorizer.roadsArchive.di.DaggerRoadsArchiveComponent;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.adapter.RoadsArchiveRecyclerAdapter;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel.RoadsArchiveViewModel;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.ROADS_ARCHIVE_FRAGMENT_TITLE;
import static com.marcin.jasi.roadmemorizer.general.Constants.ROAD_ID_KEY;

@PerFragment
public class RoadsArchiveFragment extends CommonFragment {

    public static final String TITLE = ROADS_ARCHIVE_FRAGMENT_TITLE;

    @Inject
    ViewModelProvider.Factory viewModelProvider;
    @Inject
    RoadsArchiveRecyclerAdapter adapter;

    private CompositeDisposable disposable = new CompositeDisposable();
    private RoadsArchiveViewModel viewModel;
    private RoadsArchiveFragmentBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.roads_archive_fragment,
                container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDependencies();

        viewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(RoadsArchiveViewModel.class);

        binding.recyclerView.addItemDecoration(new VerticalRecyclerDivider(
                (int) getResources().getDimension(R.dimen.recycler_view_padding), true));
        binding.recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.setListener(this::openRoadLoadFragment);
    }

    private void openRoadLoadFragment(long roadId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROAD_ID_KEY, roadId);

        Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_roadsArchiveFragment_to_roadLoaderFragment, bundle);
    }

    private void initDependencies() {
        DaggerRoadsArchiveComponent
                .builder()
                .mainActivityComponent(((MainActivity) getActivity()).getComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        disposable.add(viewModel.getRoads()
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
                .subscribe(items -> adapter.updateList(items), Timber::d));
    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
