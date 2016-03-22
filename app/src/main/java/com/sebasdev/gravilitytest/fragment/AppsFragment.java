package com.sebasdev.gravilitytest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebasdev.gravilitytest.MainActivity;
import com.sebasdev.gravilitytest.R;
import com.sebasdev.gravilitytest.adapter.AppsAdapter;
import com.sebasdev.gravilitytest.interfaces.FragmentInteractionListener;
import com.sebasdev.gravilitytest.interfaces.ItemClickListener;
import com.sebasdev.gravilitytest.model.App;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AppsFragment extends Fragment implements ItemClickListener<App> {

    private FragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View view;
    private App selectedApp = null;
    private ArrayList<App> apps = new ArrayList<>();

    public AppsFragment() {
        // Required empty public constructor
    }


    public void setApps(ArrayList<App> apps) {
        this.apps = apps;
    }

    public void updateApps(ArrayList<App> apps) {
        this.apps = apps;
        updateLayoutManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_apps, container, false);
        setRecyclerView();

        return view;
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

    /**
     * When touch an app
     */
    @Override
    public void onItemClick(App item) {
        selectedApp = item;
        showDialog();
    }

    /**
     * Open the information dialog
     */
    private void showDialog() {
        if (selectedApp != null)
            createInfoDialog().show();
        else
            Log.e("CreateDialog", "La app seleccionada es null");
    }

    private void setRecyclerView() {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_apps);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        updateLayoutManager();
    }

    private void updateLayoutManager() {

        if (MainActivity.portrait) {
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
        } else {
            // use a grid layout manager with 2 columns
            mLayoutManager = new GridLayoutManager(getContext(), 2);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new AppsAdapter(apps, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Dialog that show the app information
     */
    public AlertDialog createInfoDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_app_detail, null);

        builder.setView(v);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.FadeDialogAnimation;

        ImageView image = (ImageView) v.findViewById(R.id.infoImageApp);
        TextView tvName = (TextView) v.findViewById(R.id.infoTvAppName);
        TextView tvAuthor = (TextView) v.findViewById(R.id.infoTvAppAuthor);
        TextView tvPrice = (TextView) v.findViewById(R.id.infoTvAppPrice);
        TextView tvDescription = (TextView) v.findViewById(R.id.infoTvAppDescription);

        if (MainActivity.isOnline) {
            Picasso.with(getContext()).load(selectedApp.getImage()).into(image);
        } else {
            image.setImageBitmap(selectedApp.getImageBitmap());
        }

        tvName.setText(selectedApp.getName());
        tvAuthor.setText("Author: " + selectedApp.getAuthor());
        tvPrice.setText("Price: " + selectedApp.getPrice());
        tvDescription.setText(selectedApp.getDescription());

        Button btnClose = (Button) v.findViewById(R.id.btnCloseInfo);

        btnClose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        return dialog;
    }
}
