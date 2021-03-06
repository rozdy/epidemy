package com.rozdy.epidemy.ui.options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rozdy.epidemy.R;

public class OptionsListActivity extends AppCompatActivity
        implements OptionsListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_list);
    }

    public void onItemSelected(String id) {
        Bundle arguments = new Bundle();
        arguments.putString(OptionsDetailFragment.ARG_ITEM_ID, id);
        OptionsDetailFragment fragment = new OptionsDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.options_detail_container, fragment)
                .commit();
    }
}
