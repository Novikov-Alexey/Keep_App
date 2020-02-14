package com.devnovikov.keepapp;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class SecondActivity extends AppCompatActivity {


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_bottomappbar, menu);
//        return true;
//    }


    @BindView(R.id.bottom_app_bar) BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);


//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_bottomappbar, bottomAppBar.getMenu());
        bottomAppBar.replaceMenu(R.menu.menu_bottomappbar);
        bottomAppBar.setOnMenuItemClickListener(
                (item)-> {
                    switch (item.getItemId()) {
                        case R.id.app_bar_check:
                            Toast.makeText(SecondActivity.this, "app_bar_check", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.app_bar_draw:
                            Toast.makeText(SecondActivity.this, "app_bar_draw", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.app_bar_micro:
                            Toast.makeText(SecondActivity.this, "app_bar_micro", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.app_bar_picture:
                            Toast.makeText(SecondActivity.this, "app_bar_picture", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    return true;
                });

    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        Toast.makeText(SecondActivity.this, "fab", Toast.LENGTH_SHORT).show();
    }



//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.app_bar_check:
//                Toast.makeText(this, "app_bar_check", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.app_bar_draw:
//                Toast.makeText(this, "app_bar_draw", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.app_bar_micro:
//                Toast.makeText(this, "app_bar_micro", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.app_bar_picture:
//                Toast.makeText(this, "app_bar_picture", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
}
