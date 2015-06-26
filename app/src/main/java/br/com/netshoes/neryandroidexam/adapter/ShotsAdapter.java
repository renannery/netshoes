package br.com.netshoes.neryandroidexam.adapter;

/**
 * Created by nery on 6/26/2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

import br.com.netshoes.neryandroidexam.R;
import br.com.netshoes.neryandroidexam.interfaces.OnItemClickListener;
import br.com.netshoes.neryandroidexam.model.Shot;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.ViewHolder> {

    private Context context;
    public ArrayList<Shot> dataset;
    private OnItemClickListener onItemClickListener;

    public ShotsAdapter(Context context, ArrayList<Shot> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_shot_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.fillHolder(dataset.get(i));

        Picasso.with(viewHolder.itemView.getContext())
                .load(dataset.get(i).getImagePath())
                .centerCrop()
                .fit()
                .into(viewHolder.ivClientPhoto);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addItem(Shot shot) {
        dataset.add(shot);
        notifyDataSetChanged();
    }

    public void addItems(Collection<Shot> shots) {
        dataset.addAll(shots);
        notifyDataSetChanged();
    }

    public ArrayList<Shot> getItems() {
        return dataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.tvUsername)
        TextView tvUsername;
        @InjectView(R.id.tvAddress)
        TextView tvAddress;
        @InjectView(R.id.tvIssue)
        TextView tvIssue;
        @InjectView(R.id.tvDistance)
        TextView tvDistance;
        @InjectView(R.id.ivClientPhoto)
        ImageView ivClientPhoto;

        private OnItemClickListener onItemClickListener;
        private Shot shot;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            this.onItemClickListener = listener;
            view.setOnClickListener(this);
            ButterKnife.inject(this, view);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onClickItem(view, shot);
        }

        public void fillHolder(Shot shot) {
            this.shot = shot;

            tvUsername.setText(String.valueOf(shot.getUser().getFirstName().concat(" ").concat(shot.getUser().getLastName())));
            tvAddress.setText(shot.getAppointment().getScheduling().getLocation().getStreet());

            tvIssue.setText(shot.getIssues().get(0).getDescription());
        }
    }
}
