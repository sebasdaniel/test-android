package com.sebasdev.gravilitytest.interfaces;

/**
 * Created by m4605 on 17/03/16.
 * Interface for get event click in lists, like RecyclerView
 */
public interface ItemClickListener<T> {

    void onItemClick(T item);
}
