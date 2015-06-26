package br.com.netshoes.neryandroidexam.helper;

/**
 * Created by nery on 6/26/2015.
 */

import android.app.Fragment;

import br.com.netshoes.neryandroidexam.fragment.ShotDetailFragment;
import br.com.netshoes.neryandroidexam.fragment.ShotsListFragment;

public class CallManager {

    public static final Fragment shotsListFragment() {
        Fragment fragment = new ShotsListFragment();
        return fragment;
    }

    public static final Fragment shotDetailsFragment() {
        Fragment fragment = new ShotDetailFragment();
        return fragment;
    }
}
