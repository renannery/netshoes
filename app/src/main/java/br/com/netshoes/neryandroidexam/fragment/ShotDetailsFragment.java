package br.com.netshoes.neryandroidexam.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.netshoes.neryandroidexam.R;
import br.com.netshoes.neryandroidexam.helper.JsonManager;
import br.com.netshoes.neryandroidexam.model.GenericBus;
import br.com.netshoes.neryandroidexam.model.Shot;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by nery on 6/26/2015.
 */
public class ShotDetailsFragment extends BaseFragment {

    @InjectView(R.id.rlShotItem)
    RelativeLayout rlShotItem;

    @InjectView(R.id.rlPlayer)
    RelativeLayout rlPlayer;

    @InjectView(R.id.tvShotDescription)
    TextView tvShotDescription;

    @InjectView(R.id.progressbar)
    ProgressBar progressbar;

    @InjectView(R.id.ivPlayerAvatar)
    ImageView ivPlayerAvatar;

    @InjectView(R.id.tvPlayerName)
    TextView tvPlayerName;

    @InjectView(R.id.tvViewsCount)
    TextView tvViewsCount;

    @InjectView(R.id.tvTitle)
    TextView tvTitle;

    @InjectView(R.id.ivShot)
    ImageView ivShot;

    private Shot shot;


    @Override
    protected int layoutToInflate() {
        return R.layout.fragment_shot_details;
    }

    @Override
    protected void doOnCreated(View view) {
        Bundle b = getArguments();
        if(!b.isEmpty()) {
            long shotId = b.getLong(Shot.SHOT_ID);
            JsonManager.getInstance().shotDetails(shotId);
        }
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
        if (event.getKey() == GenericBus.SHOTS_DETAILS) {
            if (event.getObject() != null) {
                shot = (Shot) event.getObject();
                onItemsLoadComplete();
            } else {
                Toast.makeText(getActivity(), "Ops, something wrong =(", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onItemsLoadComplete() {
        progressbar.setVisibility(View.GONE);
        tvShotDescription.setVisibility(View.VISIBLE);
        rlPlayer.setVisibility(View.VISIBLE);
        rlShotItem.setVisibility(View.VISIBLE);
        fillFields();
    }

    private void fillFields() {
        Picasso.with(getActivity())
                .load(shot.getImagePath())
                .centerCrop()
                .fit()
                .into(ivShot);
        Picasso.with(getActivity())
                .load(shot.getPlayer().getImagePath())
                .centerCrop()
                .fit()
                .into(ivPlayerAvatar);
        tvViewsCount.setText(String.valueOf(shot.getViewsCount()));
        tvTitle.setText(shot.getTitle());
        tvPlayerName.setText(shot.getPlayer().getName());
        tvShotDescription.setText(Html.fromHtml(shot.getDescription()));

    }
}
