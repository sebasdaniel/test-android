package com.sebasdev.gravilitytest.interfaces;

/**
 * Created by m4605 on 17/03/16.
 * Generic interface for implement lists clicks, like RecyclerView
 */
public interface ItemClickListener<T> {

    void onItemClick(T item);
}
