package com.sebasdev.gravilitytest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sebasdev.gravilitytest.fragment.AppsFragment;
import com.sebasdev.gravilitytest.fragment.CategoriesFragment;
import com.sebasdev.gravilitytest.interfaces.FragmentInteractionListener;
import com.sebasdev.gravilitytest.model.App;
import com.sebasdev.gravilitytest.model.Category;
import com.sebasdev.gravilitytest.util.DataManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener {

    public static final int FRAGMENT_CATEGORIES = 0;
    public static final int FRAGMENT_APPS = 1;

    public static boolean isOnline;

    private CategoriesFragment categoriesFragment;
    private AppsFragment appsFragment;
    private int currentFragment;
    private boolean portrait = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        isOnline = isConnected();

        if (!isOnline) {
            TextView tvNetworkAlert = (TextView) findViewById(R.id.tvNetworkAlert);
            tvNetworkAlert.setVisibility(View.VISIBLE);
        }

        categoriesFragment = new CategoriesFragment();
        appsFragment = new AppsFragment();

        portrait = getResources().getBoolean(R.bool.portrait);

        if(portrait){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Loader loader = new Loader();
        loader.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    // Options menu click, process menu icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.i("currentFragment", "currentFragment es: " + currentFragment + "\nHome id: " + R.id.home + ", selected id: " + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                if (currentFragment == FRAGMENT_APPS) {
                    setFragment(FRAGMENT_CATEGORIES);
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }
    }

    private void setFragment(int fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // add the fragment to container
        if (fragment == FRAGMENT_CATEGORIES) {

            transaction.setCustomAnimations(R.anim.pop_in, R.anim.pop_out);
            transaction.replace(R.id.main_content, categoriesFragment);
            currentFragment = FRAGMENT_CATEGORIES;

            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Categorias");

        } else {
            if (fragment == FRAGMENT_APPS) {

                transaction.setCustomAnimations(R.anim.set_in, R.anim.set_out);
                transaction.replace(R.id.main_content, appsFragment);

                if (getSupportActionBar() != null)
                    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);

                currentFragment = FRAGMENT_APPS;

            } else {
                Log.e("setFragment", "fragment no especidicado");
            }
        }

        transaction.commit();
    }

    private void setFragments() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_content, categoriesFragment);
        transaction.commit();

        transaction.replace(R.id.display_content, appsFragment);
        transaction.commit();
    }

    private void updateAppsFragment() {
        // TODO: 19/03/16 update info fragment
    }

    private boolean isConnected() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    @Override
    public void onSetApps(Category category) {

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(category.getLabel());

        // TODO: 20/03/16 verify if it's tablet or landcape
        appsFragment.setApps(DataManager.getAppsByCategory(category));
        setFragment(FRAGMENT_APPS);
    }

    private class Loader extends AsyncTask<Void, Void, Boolean> {

        private final String DEBUG_TAG = "MainActivity.Loader";
        private ProgressDialog mDialog;
        private String error = "";

        @Override
        protected void onPreExecute() {
            mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage("Cargando Datos");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // if it's online but there is a error, load the values from database and show error, else only load from database
            if (isOnline) {

                try {
                    DataManager.getServiceData(MainActivity.this);
                    return true;
                } catch (IOException e) {
//                e.printStackTrace();
                    error = "No se pudo obtener respuesta del servidor";
                    Log.d(DEBUG_TAG, error);
                } catch (JSONException e) {
//                e.printStackTrace();
                    error = "No se pudo procesar la respuesta, parece que no es un JSON válido";
                    Log.d(DEBUG_TAG, error);
                }

                DataManager.getDBData(MainActivity.this);
                return false;

            } else {
                DataManager.getDBData(MainActivity.this);
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mDialog.dismiss();

            if (!result)
                showErrorDialog();
//            if (result) {
            if(portrait){
                setFragment(FRAGMENT_CATEGORIES);
            } else {
                setFragments();
            }
//            } else {
//                showErrorDialog();
//            }
        }

        private void showErrorDialog() {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Error")
                    .setMessage(error)
                    .setPositiveButton("Aceptar", null)
                    .show();
        }
    }
}
