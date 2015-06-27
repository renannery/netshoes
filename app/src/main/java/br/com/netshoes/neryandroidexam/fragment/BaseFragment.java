package br.com.netshoes.neryandroidexam.fragment;

/**
 * Created by nery on 6/26/2015.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(layoutToInflate(), container, false);
            ButterKnife.inject(this, rootView);
            doOnCreated(rootView);
            setUpToolbar(rootView);
        } else {
            setUpToolbar(rootView);
        }
        return rootView;
    }

    protected abstract int layoutToInflate();
    protected abstract void doOnCreated(View view);
    protected abstract void setUpToolbar(View view);

    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup parentViewGroup = (ViewGroup) rootView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViewsInLayout();
            }
        }
    }
}

