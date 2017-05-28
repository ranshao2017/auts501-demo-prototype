package com.phicomm.prototype.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.phicomm.prototype.R;
import com.phicomm.prototype.adapter.FactoryInfoAdapter;
import com.phicomm.prototype.views.DividerItemDecoration;

import butterknife.BindView;

/**
 * 工厂界面
 * Created by qisheng.lv on 2017/4/26.
 */
public class FactoryActivity extends BaseActivity implements FactoryInfoAdapter.OnItemClickListner {

    @BindView(R.id.listview)
    RecyclerView mRecyclerView;

    private FactoryInfoAdapter mAdapter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_factory);
    }

    @Override
    public void afterInitView() {
        setPageTitle(getString(R.string.title_factory));
        intiRv();
    }

    private void intiRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mAdapter == null) {
            mAdapter = new FactoryInfoAdapter(this);
            mAdapter.addOnItemClickListener(this);
        }
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int postion, String key) {
        if (key.equals(getString(R.string.h5_test))) {
            startActivity(new Intent(this, WebViewActivity.class));
        }
    }
}
