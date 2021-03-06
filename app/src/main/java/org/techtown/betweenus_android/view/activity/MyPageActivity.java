package org.techtown.betweenus_android.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.techtown.betweenus_android.R;
import org.techtown.betweenus_android.base.BaseActivity;
import org.techtown.betweenus_android.databinding.MypageActivityBinding;
import org.techtown.betweenus_android.manager.CurrentUser;
import org.techtown.betweenus_android.model.Member;

public class MyPageActivity extends BaseActivity<MypageActivityBinding> {
    @Override
    protected int layoutId() {
        return R.layout.mypage_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMyInfo();

        binding.backArrowImg.setOnClickListener(view -> onBackPressed());

        clickEvent();
    }

    private void getMyInfo() {
        CurrentUser currentUser = new CurrentUser(this, "betweenUs.db", null, 2);
        Member myInfo = currentUser.getResult();

        Log.d("imgTag", myInfo.getprofileImg());
        if (!myInfo.getprofileImg().isEmpty()) {
            Log.d("imgTag", "Pass");
            Glide.with(this).load(myInfo.getprofileImg()).into(binding.userImageview);
        }
        binding.mypageNameText.setText(myInfo.getName());
        binding.schoolText.setText(myInfo.getSchool());
        binding.gradeText.setText(myInfo.getGrade() + "학년 " + myInfo.getSchoolClass() + "반");
    }

    private void clickEvent() {

        View.OnClickListener onClickListener = v -> Toast.makeText(this, "추후에 개발 될 예정입니다.", Toast.LENGTH_SHORT).show();

        binding.profileEditBtn.setOnClickListener(onClickListener);

        binding.profileEditBtn2.setOnClickListener(onClickListener);

        binding.profileEditBtn3.setOnClickListener(onClickListener);
    }
}
