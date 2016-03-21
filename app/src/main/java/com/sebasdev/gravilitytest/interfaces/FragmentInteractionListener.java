package com.sebasdev.gravilitytest.interfaces;

import com.sebasdev.gravilitytest.model.Category;

/**
 * Created by m4605 on 17/03/16.
 * Interface for fragments interact with activity
 */
public interface FragmentInteractionListener {

    // Implemented by fragment categories
    void onSelectCategory(Category category);
}
