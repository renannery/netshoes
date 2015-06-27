package br.com.netshoes.neryandroidexam.helper;

/**
 * Created by nery on 6/26/2015.
 */

import android.app.Fragment;
import android.os.Bundle;

import br.com.netshoes.neryandroidexam.fragment.ShotDetailsFragment;
import br.com.netshoes.neryandroidexam.fragment.ShotsListFragment;

public class CallManager {

    public static final Fragment shotsListFragment() {
        Fragment fragment = new ShotsListFragment();
        return fragment;
    }

    public static final Fragment shotDetailsFragment(Bundle args) {
        Fragment fragment = new ShotDetailsFragment();
        if (!args.isEmpty()) {
            fragment.setArguments(args);
        }

        return fragment;
    }
}
