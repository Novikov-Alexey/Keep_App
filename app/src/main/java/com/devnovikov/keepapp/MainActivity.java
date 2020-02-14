package com.devnovikov.keepapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.bottom_app_bar) BottomAppBar bottomAppBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    DatabaseHelper databaseHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottomappbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initBottomAppBar();
        databaseHelper = new DatabaseHelper(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_notes:
                Toast.makeText(this, "nav_notes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notifications:
                Toast.makeText(this, "nav_notifications", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_archive:
                Toast.makeText(this, "nav_archive", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_trash:
                Toast.makeText(this, "nav_trash", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        menuItem.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initBottomAppBar() {
        bottomAppBar.replaceMenu(R.menu.menu_bottomappbar);
        bottomAppBar.setOnMenuItemClickListener(
                (item)-> {
                    switch (item.getItemId()) {
                        case R.id.app_bar_check:
                            Toast.makeText(MainActivity.this, "app_bar_check", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.app_bar_draw:
                            Toast.makeText(MainActivity.this, "app_bar_draw", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.app_bar_micro:
                            Toast.makeText(MainActivity.this, "app_bar_micro", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.app_bar_picture:
                            Toast.makeText(MainActivity.this, "app_bar_picture", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    return true;
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }

    private void showRecord() {
        //last added record show on top
        mRecyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(_sGridLayoutManager);
        RecyclerView.Adapter adapter = new com.devnovikov.keepapp.Adapter(MainActivity.this, databaseHelper.getAllData(Constants.C_UPDATE_TIMESTAMP + " DESC"));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("EDIT_MODE", false);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }
}
