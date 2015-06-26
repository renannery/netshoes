package br.com.netshoes.neryandroidexam.activity;

/**
 * Created by nery on 6/26/2015.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import br.com.netshoes.neryandroidexam.R;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutToInflate());
        ButterKnife.inject(this);
        fragmentManager = getFragmentManager();
        setToolbar();

        if(savedInstanceState == null) {
            doOnFirstTime();
        }

        doOnCreated(savedInstanceState);
    }

    protected abstract void doOnFirstTime();
    protected abstract void doOnCreated(Bundle savedInstanceState);

    protected abstract int layoutToInflate();

    public void setToolbar() {
        setToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        if(this.toolbar != null) {
            setSupportActionBar(this.toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    public void setFragmentMain(Fragment fragment, boolean backStack) {
        String fragmentName = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction;

        if(fragmentManager.findFragmentByTag(fragmentName) != null) {
            Log.d("FRAGMENT", "fragment already added");
            if(fragmentManager.findFragmentByTag(fragmentName).isVisible()) {
                return;
            } else {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(fragmentName)).commit();
            }
        } else {
            Log.d("FRAGMENT", "fragment is not added");
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        if(backStack) {
            fragmentTransaction
                    .addToBackStack(fragmentName);
        }

        fragmentTransaction
                .replace(R.id.flMainContent, fragment, fragmentName)
                .commit();

        fragmentManager.executePendingTransactions();
    }

    protected void resetToHomeFragment() {
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }
}
