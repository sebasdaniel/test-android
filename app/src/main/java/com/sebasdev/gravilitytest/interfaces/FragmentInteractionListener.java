package com.sebasdev.gravilitytest.interfaces;

import android.net.Uri;

import com.sebasdev.gravilitytest.model.Category;

/**
 * Created by m4605 on 17/03/16.
 */
public interface FragmentInteractionListener {

    // Implemented by fragment categories
    void onSetApps(Category category);
}
