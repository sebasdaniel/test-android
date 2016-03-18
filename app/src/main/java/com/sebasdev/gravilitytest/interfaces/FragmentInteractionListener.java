package com.sebasdev.gravilitytest.interfaces;

import android.net.Uri;

import com.sebasdev.gravilitytest.model.Category;

/**
 * Created by m4605 on 17/03/16.
 */
public interface FragmentInteractionListener {

    // All fragments
    void onChangeFragment(int fragment);

    // Fragment categories
    void onSetApps(Category category);
}
