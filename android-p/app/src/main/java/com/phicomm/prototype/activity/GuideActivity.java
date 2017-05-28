package com.phicomm.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.phicomm.prototype.R;
import com.phicomm.prototype.adapter.GuideViewPagerAdapter;
import com.phicomm.prototype.constants.AppConstans;
import com.phicomm.prototype.utils.SpfUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiming.zeng on 2017/5/2.
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private List<View> views;
    private Button startBtn;    //启动主页面
    private GuideViewPagerAdapter mAdapter;
    private ImageView[] dots;
    private int currentIndex;

    // 引导页布局资源
    private static final int[] pics = {R.layout.view_guilde1, R.layout.view_guilde2,
            R.layout.view_guilde3, R.layout.view_guilde4, R.layout.view_guilde5};

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
        getWindow().setBackgroundDrawable(null);
        views = new ArrayList<View>();
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            //最后一页
            if (i == pics.length - 1) {
                startBtn = (Button) view.findViewById(R.id.btn_enter);
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        mAdapter = new GuideViewPagerAdapter(views);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 设置底部小点选中状态
                setCurDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        initDots();
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setImageResource(R.drawable.guide_dot_selected);
        dots[currentIndex].setImageResource(R.drawable.guide_dot_unselected);
        currentIndex = position;
    }

    private void initDots() {
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dots_Layout);
        dots = new ImageView[pics.length];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = (ImageView) dotLayout.getChildAt(i);
            dots[i].setImageResource(R.drawable.guide_dot_unselected);
        }
        currentIndex = 0;
        dots[currentIndex].setImageResource(R.drawable.guide_dot_selected);
    }

    @Override
    public void afterInitView() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, LoginCloudActivity.class);
        startActivity(intent);
        SpfUtils.put(this, AppConstans.FIRST_OPEN, true);
        finish();
    }
}
