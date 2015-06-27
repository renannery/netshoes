package br.com.netshoes.neryandroidexam.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.netshoes.neryandroidexam.R;
import br.com.netshoes.neryandroidexam.activity.MainActivity;
import br.com.netshoes.neryandroidexam.adapter.ShotsAdapter;
import br.com.netshoes.neryandroidexam.helper.CallManager;
import br.com.netshoes.neryandroidexam.helper.JsonManager;
import br.com.netshoes.neryandroidexam.helper.PageUtils;
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

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ShotsAdapter mAdapter;
    private ArrayList<Shot> shots;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_shots_list;
    }

    @Override
    protected void doOnCreated(View view) {
        JsonManager.getInstance().popularShots(1);

        shots = new ArrayList<>();
        rvShots.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });



        mAdapter = new ShotsAdapter(getActivity(), shots);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if(current_page <= PageUtils.pagesCount) {
                    JsonManager.getInstance().popularShots(current_page++);
                }
            }
        };
        rvShots.setLayoutManager(linearLayoutManager);
        rvShots.setOnScrollListener(endlessRecyclerOnScrollListener);
        rvShots.setAdapter(mAdapter);
    }

    @Override
    protected void setUpToolbar(View view) {

    }

    private void refreshItems() {
        mAdapter.getItems().clear();
        endlessRecyclerOnScrollListener.reset(0, false);
        JsonManager.getInstance().popularShots(1);
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
                        Shot shot = (Shot) object;
                        Bundle args = new Bundle();
                        args.putLong(Shot.SHOT_ID, shot.getId());

                        ((MainActivity) getActivity()).setFragmentMain(CallManager.shotDetailsFragment(args));
                    }
                });

                onItemsLoadComplete();
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.error_something_wrong), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        progressbar.setVisibility(View.GONE);
    }
}
