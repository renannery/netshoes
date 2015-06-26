package br.com.netshoes.neryandroidexam.activity;

import android.os.Bundle;

import br.com.netshoes.neryandroidexam.R;
import br.com.netshoes.neryandroidexam.helper.CallManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void doOnFirstTime() {

    }

    @Override
    protected void doOnCreated(Bundle savedInstanceState) {
        setFragmentMain(CallManager.shotsListFragment(), true);
    }

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }
}
