package org.techtown.betweenus_android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.techtown.betweenus_android.R;
import org.techtown.betweenus_android.base.BaseActivity;
import org.techtown.betweenus_android.databinding.MainActivityBinding;
import org.techtown.betweenus_android.manager.CurrentUser;
import org.techtown.betweenus_android.manager.Token;
import org.techtown.betweenus_android.manager.ViewModelFactory;
import org.techtown.betweenus_android.model.Study;
import org.techtown.betweenus_android.viewmodel.MainViewModel;
import org.techtown.betweenus_android.viewmodel.StudyViewModel;
import org.techtown.betweenus_android.widget.recyclerview.adapter.MainListAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<MainActivityBinding> implements NavigationView.OnNavigationItemSelectedListener {

    private List<Study> studies = new ArrayList<>();
    private MainViewModel mainViewModel;
    private StudyViewModel studyViewModel;
    MainListAdapter mainListAdapter = new MainListAdapter(studies, this, this);

    int index = 0;
    boolean isLoading = false;

    @Override
    protected int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initText();
        binding.navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        studyViewModel.getStudyList();

        studyViewModel.getData().observe(this, studyList -> {
            studies = studyList;
            binding.studyRecyclerView.setAdapter(new MainListAdapter(studies, this, this));
        });

        clickEvent();
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(MainViewModel.class);
        studyViewModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(StudyViewModel.class);
    }

    private void initText() {

    }

    private void clickEvent() {

        binding.create.setOnClickListener(v -> startActivity(new Intent(this, StudyWriteActivity.class)));

        binding.menuBtn.setOnClickListener(v -> binding.main.openDrawer(GravityCompat.START));
    }

    @Override
    public void onBackPressed() {

        if (binding.main.isDrawerOpen(GravityCompat.START)) {
            binding.main.closeDrawers();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_me :
                intent = new Intent(this, MyPageActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_found:
                intent = new Intent(this, FoundStudyActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_apply:
                intent = new Intent(this, ApplyStudyActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_logout:
                new CurrentUser(this,"betweenUs.db",null,1).delete();
                new Token(this).setToken("");
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
        }

        overridePendingTransition(0, 0);
        binding.main.closeDrawers();

        return false;
    }
}
