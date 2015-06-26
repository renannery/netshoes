package br.com.netshoes.neryandroidexam.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.netshoes.neryandroidexam.R;
import br.com.netshoes.neryandroidexam.adapter.ShotsAdapter;
import br.com.netshoes.neryandroidexam.helper.JsonManager;
import br.com.netshoes.neryandroidexam.interfaces.EndlessRecyclerOnScrollListener;
import br.com.netshoes.neryandroidexam.interfaces.OnItemClickListener;
import br.com.netshoes.neryandroidexam.model.GenericBus;
import br.com.netshoes.neryandroidexam.model.Shot;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by nery on 6/26/2015.
 */
public class ShotsListFragment extends BaseFragment {
    @InjectView(R.id.rvShots)
    RecyclerView rvShots;

    @InjectView(R.id.progressbar)
    ProgressBar progressbar;

    private ShotsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Shot> shots;
    private int currentPage = 1;
    private int totalPages = 50;

    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_shots_list;
    }

    @Override
    protected void doOnCreated(View view) {
        JsonManager.getInstance().popularShots(currentPage);

        shots = new ArrayList<>();
        rvShots.setHasFixedSize(true);
        rvShots.setLayoutManager(mLayoutManager);

        mAdapter = new ShotsAdapter(getActivity(), shots);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvShots.setLayoutManager(linearLayoutManager);
        rvShots.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                JsonManager.getInstance().popularShots(current_page++);
            }
        });
        rvShots.setAdapter(mAdapter);
    }

    @Override
    protected void setUpToolbar(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(GenericBus event) {
        if (event.getKey() == GenericBus.SHOTS_LIST) {
            if (event.getObjects() != null) {
                ArrayList<Shot> shotsResult = event.getObjects();
                mAdapter.addItems(shotsResult);
                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClickItem(View view, Object object) {

                    }
                });

                onItemsLoadComplete();
            } else {
                Toast.makeText(getActivity(), "Ops, something wrong =(", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onItemsLoadComplete() {
        progressbar.setVisibility(View.GONE);
    }
}
