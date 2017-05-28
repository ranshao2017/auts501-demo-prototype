package com.phicomm.prototype.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phicomm.prototype.R;
import com.phicomm.prototype.bean.CloudRouterList;

import java.util.List;

/**
 * Created by qisheng.lv on 2017/4/19.
 */

public class CloudRouterAdapter extends RecyclerView.Adapter<CloudRouterAdapter.Holder> {

    private Context mContext;
    private List<CloudRouterList.CloudRouter> mDatas;
    private ItemClickListener mListener;

    public interface ItemClickListener {
        void onItemClick(int position, CloudRouterList.CloudRouter cloudRouter);

        void onUnBindRouterClick(CloudRouterList.CloudRouter cloudRouter);
    }

    public CloudRouterAdapter(Context context, List<CloudRouterList.CloudRouter> list) {
        mContext = context;
        mDatas = list;
    }

    public void refreshData(List<CloudRouterList.CloudRouter> list) {
        if (list != null) {
            mDatas = list;
            notifyDataSetChanged();
        }
    }

    public void removeRouter(String devSn) {
        if (mDatas == null || mDatas.isEmpty()) {
            return;
        }

        CloudRouterList.CloudRouter temp = null;
        for (CloudRouterList.CloudRouter router : mDatas) {
            if (router.getDevMac().equals(devSn)) {
                temp = router;
                break;
            }
        }
        if (temp != null) {
            mDatas.remove(temp);
            notifyDataSetChanged();
        }
    }

    public void setItemListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_cloud_router, null, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final int updatePosition = holder.getAdapterPosition();
        CloudRouterList.CloudRouter cloudRouter = mDatas.get(holder.getAdapterPosition());
        if (cloudRouter == null) {
            return;
        }
        String name = TextUtils.isEmpty(cloudRouter.getDevName()) ? cloudRouter.getDevMac() : cloudRouter.getDevName();
        holder.tvName.setText(name);
        holder.tvMac.setText(cloudRouter.getDevMac());
        String online = cloudRouter.getOnline().equals("1") ? mContext.getString(R.string.cloud_router_online) : mContext.getString(R.string.cloud_router_outline);
        holder.tvOnline.setText(online);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(updatePosition, mDatas.get(updatePosition));
                }
            }
        });
        holder.btnUnBindRouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onUnBindRouterClick(mDatas.get(updatePosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvMac;
        TextView tvOnline;
        TextView btnUnBindRouter;

        public Holder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvMac = (TextView) view.findViewById(R.id.tv_mac);
            tvOnline = (TextView) view.findViewById(R.id.tv_online);
            btnUnBindRouter = (TextView) view.findViewById(R.id.btn_unbind_router);
        }
    }

}
