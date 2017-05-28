package com.phicomm.prototype.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.phicomm.prototype.R;
import com.phicomm.prototype.constants.Images;
import com.phicomm.prototype.manager.imageloader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.iv_test)
    ImageView mIvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        testImgLoad();
        testConflict();
    }

    private void testConflict() {
//        String str1 = "fjwliairjiwoqjrjfiwqrg";
    }

    public void testImgLoad() {
        ImageLoader.getLoader(this)
                .load(Images.imageThumbUrls[4])
                .into(mIvTest);
    }

}
