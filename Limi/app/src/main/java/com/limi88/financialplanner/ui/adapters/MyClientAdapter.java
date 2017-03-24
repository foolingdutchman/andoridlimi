package com.limi88.financialplanner.ui.adapters;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hehao
 * Date on 16/9/21.
 * Email: hao.he@limi88.com
 */
public class MyClientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE1 = 1;
    private static final int FOOT_TYPE = 2;
    private Context context;
    private List<Real> mRealClients;
    private OnItemClickListener onItemClickListener;

    @Inject
    public MyClientAdapter(Context context, List<Real> mRealClients) {
        this.context = context;
        this.mRealClients = mRealClients;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setmRealClients(List<Real> mRealClients) {
        this.mRealClients = mRealClients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE1) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.clientlist_myclient_item, parent, false));
        } else
            return new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof ViewHolder) {
                if (mRealClients.size() != 0) {

                    ((ViewHolder) holder).mNameView.setText(mRealClients.get(position).getName());
                    ((ViewHolder) holder).mRemarkView.setText(mRealClients.get(position).getStatus());
                    ((ViewHolder) holder).mCallView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("您确定拨打此号码？")
                                    .setContentText(mRealClients.get(position).getPhone())
                                    .setConfirmText("确定")
                                    .setConfirmClickListener(
                                            new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mRealClients.get(position).getPhone()));
                                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        // TODO: Consider calling
                                                        ToastUtils.showToast("您没有授权拨打电话");
                                                        return;
                                                    } else context.startActivity(intent);
                                                }
                                            }
                                    ).setCancelText("取消")
                                    .setCancelClickListener(null).show();

                        }
                    });
                    String level = mRealClients.get(position).getLevel();
                    if (level.equals("A") || level.equals("B")) {
                        ((ViewHolder) holder).mtagView.setVisibility(View.VISIBLE);
                        if (level.equals("A")) {
                            ((ViewHolder) holder).mtagView.setBackground(context.getResources().getDrawable(R.drawable.shape_tag_red));
                        } else
                            ((ViewHolder) holder).mtagView.setBackground(context.getResources().getDrawable(R.drawable.shape_tag_yellow));
                        ((ViewHolder) holder).mtagView.setText(level + "类");
                    }else   ((ViewHolder) holder).mtagView.setVisibility(View.GONE);

                    Glide.with(context).load(mRealClients.get(position).getAvatar())
                            .placeholder(R.mipmap.ic_default_logo)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(((ViewHolder) holder).mIconView);
                    if ("".equals(mRealClients.get(position).getPhone()) || mRealClients.get(position).getPhone() == null) {
                        ((ViewHolder) holder).mCallView.setVisibility(View.INVISIBLE);
                    } else {
                        ((ViewHolder) holder).mCallView.setVisibility(View.VISIBLE);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, null, position);
                        }
                    });
                }
            }

    }

    @Override
    public int getItemCount() {
        if (mRealClients.size() != 0) {
            return mRealClients.size() + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() - 1) {
            return TYPE1;
        } else return FOOT_TYPE;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIconView;
        RelativeLayout mCallView;
        TextView mNameView;
        TextView mRemarkView;
        ImageView mCallIcon;
        TextView mtagView;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconView = (ImageView) itemView.findViewById(R.id.iv_client_icon);
            mNameView = (TextView) itemView.findViewById(R.id.tv_client_name);
            mtagView = (TextView) itemView.findViewById(R.id.tv_client_tag);
            mRemarkView = (TextView) itemView.findViewById(R.id.tv_client_remark);
            mCallView = (RelativeLayout) itemView.findViewById(R.id.rl_call);
            mCallIcon = (ImageView) itemView.findViewById(R.id.iv_call);
        }
    }

    class ViewHolder6 extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)


        public ViewHolder6(View itemView) {
            super(itemView);


        }
    }
}




