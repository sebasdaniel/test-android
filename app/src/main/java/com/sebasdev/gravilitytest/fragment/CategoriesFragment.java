package com.sebasdev.gravilitytest.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sebasdev.gravilitytest.MainActivity;
import com.sebasdev.gravilitytest.R;
import com.sebasdev.gravilitytest.adapter.CategoriesAdapter;
import com.sebasdev.gravilitytest.interfaces.FragmentInteractionListener;
import com.sebasdev.gravilitytest.interfaces.ItemClickListener;
import com.sebasdev.gravilitytest.model.Category;
import com.sebasdev.gravilitytest.util.DataManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CategoriesFragment extends Fragment implements ItemClickListener<Category> {

    private FragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        setRecyclerView(v);

        return v;
    }

    private void setRecyclerView(View v) {

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_categories);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CategoriesAdapter(DataManager.getCategories(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(Category category) {
        mListener.onSetApps(category);
    }
}
