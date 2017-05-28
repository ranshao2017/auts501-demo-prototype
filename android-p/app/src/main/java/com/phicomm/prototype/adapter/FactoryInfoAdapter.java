package com.phicomm.prototype.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phicomm.prototype.BuildConfig;
import com.phicomm.prototype.R;
import com.phicomm.prototype.utils.AppInfoUtils;
import com.phicomm.prototype.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rongwei.huang on 2017/4/27.
 */

public class FactoryInfoAdapter extends RecyclerView.Adapter<FactoryInfoAdapter.ViewHolder> {
    private Context mContext;

    private List<Pair> mDatas;

    private OnItemClickListner mOnItemClickListener;

    public void addOnItemClickListener(OnItemClickListner l) {
        mOnItemClickListener = l;
    }

    public FactoryInfoAdapter(Context context) {
        mContext = context;
        String[] keys = context.getResources().getStringArray(R.array.factory_info_items);
        mDatas = new ArrayList<>();

        for (int i = 0; i < keys.length; i++) {
            mDatas.add(i, new Pair(keys[i], getValue(keys[i])));
        }
    }

    private String getValue(String key) {
        if (key.equals(getVaById(R.string.target_sdk))) {
            return String.valueOf(AppInfoUtils.getTargetSdkVersion(mContext));
        } else if (key.equals(getVaById(R.string.qudao))) {
            String chanel = AppInfoUtils.getChannel(mContext);
            int versionCode = AppInfoUtils.getAppVersionCode(mContext);
            return (AppInfoUtils.isInProductionEnvironment() ? "N_" : "Y_") + chanel + "_" + versionCode;
        } else if (key.equals(getVaById(R.string.umeng_key))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "UMENG_APPKEY"));
        } else if (key.equals(getVaById(R.string.umeng_channel))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "UMENG_CHANNEL"));
        } else if (key.equals(getVaById(R.string.baichuan))) {
            return String.valueOf(String.valueOf(BuildConfig.SUPPORT_BAICHUANG));
        } else if (key.equals(getVaById(R.string.buggly_id))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "BUGLY_APPID"));
        } else if (key.equals(getVaById(R.string.buggly_enable))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "BUGLY_ENABLE_DEBUG"));
        } else if (key.equals(getVaById(R.string.push_app_id))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "PUSH_APPID"));
        } else if (key.equals(getVaById(R.string.push_app_key))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "PUSH_APPKEY"));
        } else if (key.equals(getVaById(R.string.push_app_secret))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "PUSH_APPSECRET"));
        } else if (key.equals(getVaById(R.string.android_auth))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "android:authorities"));
        } else if (key.equals(getVaById(R.string.flavor))) {
            return BuildConfig.FLAVOR;
        } else if (key.equals(getVaById(R.string.build_type))) {
            return BuildConfig.BUILD_TYPE;
        }

        return "";
    }

    private String getVaById(int id) {
        return mContext.getString(id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_factory_info, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pair<String, String> pair = mDatas.get(position);

        holder.tvItem.setText(pair.first);
        holder.tvDedail.setText(pair.second);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItem;
        private TextView tvDedail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.item);
            tvDedail = (TextView) itemView.findViewById(R.id.detail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                Pair<String, String> p = mDatas.get(getPosition());
                mOnItemClickListener.onItemClick(v, getPosition(), p.first);
            }
        }
    }

    public interface OnItemClickListner {
        void onItemClick(View view, int postion, String key);
    }
}
