package com.sebasdev.gravilitytest;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sebasdev.gravilitytest.fragment.AppsFragment;
import com.sebasdev.gravilitytest.fragment.CategoriesFragment;
import com.sebasdev.gravilitytest.interfaces.FragmentInteractionListener;
import com.sebasdev.gravilitytest.model.App;
import com.sebasdev.gravilitytest.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener {

    public static final int FRAGMENT_CATEGORIES = 0;
    public static final int FRAGMENT_APPS = 1;

    public static List<App> apps = new ArrayList<>();
    public static List<Category> categories = new ArrayList<>();

    static {
        categories.add(new Category(1, "Deportes"));
        categories.add(new Category(2, "Entretenimiento"));
        categories.add(new Category(3, "Juegos"));
        categories.add(new Category(4, "Adultos"));

        apps.add(new App("App uno", "Autor de app", "USD 1.9", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur lacus nulla, rutrum sodales condimentum a, ullamcorper pretium lorem.", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(0)));
        apps.add(new App("App dos", "Autor de app", "USD 0.5", "App de prueba 2", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(0)));
        apps.add(new App("App tres", "Autor de app", "USD 0.0", "App de prueba 3", "http://es.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211808744.png", categories.get(0)));
    }

    private CategoriesFragment categoriesFragment;
    private AppsFragment appsFragment;
    private int currentFragment;
    private boolean portrait = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        categoriesFragment = new CategoriesFragment();
        appsFragment = new AppsFragment();

        portrait = getResources().getBoolean(R.bool.portrait);

        if(portrait){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setFragment(FRAGMENT_CATEGORIES);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setFragments();
        }
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.app_name);
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

    @Override
    public void onSetApps(Category category) {
        // TODO: 17/03/16 set the aplications for specific category
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(category.getLabel());

        setFragment(FRAGMENT_APPS);
    }
}
