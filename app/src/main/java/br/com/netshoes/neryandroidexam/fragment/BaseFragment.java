package br.com.netshoes.neryandroidexam.fragment;

/**
 * Created by nery on 6/26/2015.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.netshoes.neryandroidexam.R;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    private static String TOOLBAR_NULL_EXCEPTION = "There's no toolbar associated with this fragment.";

    private Toolbar toolbar;
    private Bundle savedInstanceState;
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

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public void setSavedInstanceState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    protected void doOnStateRestored(Bundle savedInstanceState) { }
    protected void doOnWithoutRestore() { }

    public Toolbar getToolbar() {
        if(toolbar == null) {
            throw new NullPointerException(TOOLBAR_NULL_EXCEPTION);
        }
        return toolbar;
    }
}

