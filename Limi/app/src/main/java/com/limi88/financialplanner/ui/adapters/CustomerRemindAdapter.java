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
import com.limi88.financialplanner.pojo.clients.Reminds;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hehao
 * Date on 16/9/30.
 * Email: hao.he@limi88.com
 */
public class CustomerRemindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE1 = 1;
    private static final int FOOT_TYPE = 2;
    private Context context;
    private Reminds mReminds;
    private List<Real>todayList;
    private List<Real>tomorrowList;
    private List<Real>nextWeekList;
    private OnItemClickListener onItemClickListener;

    @Inject
    public CustomerRemindAdapter(Context context, Reminds mReminds) {
        this.context = context;
        this.mReminds = mReminds;
        if (mReminds!=null) {
            todayList=mReminds.getToday();
            tomorrowList=mReminds.getTomorrow();
            nextWeekList=mReminds.getNextWeek();
        }

    }

    public void setmReminds(Reminds mReminds) {
        this.mReminds = mReminds;
        if (mReminds!=null) {
            todayList=mReminds.getToday();
            tomorrowList=mReminds.getTomorrow();
            nextWeekList=mReminds.getNextWeek();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE1) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.clientitem_remind_item, parent, false));
        } else
            return new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            if (mReminds != null&&todayList!=null&&tomorrowList!=null&&nextWeekList!=null) {
                if (todayList.size()!=0&&position>=0&&position<todayList.size()) {
                    if (position==0) {
                        ((ViewHolder) holder).mRemarkView.setText("今天");
                    }else ((ViewHolder) holder).mRemarkView.setVisibility(View.GONE);

                    ((ViewHolder) holder).mNameView.setText(todayList.get(position).getName());
                    ((ViewHolder) holder).mtagView.setBackground(context.getResources().getDrawable(R.drawable.shape_tag_red));
                    ((ViewHolder) holder).mtagView.setText("今天");
                    String url= todayList.get(position ).getAvatar().toString();

                    Glide.with(context).load(url)
                            .placeholder(R.mipmap.ic_default_logo)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(((ViewHolder) holder).mIconView);
                    if ("".equals(todayList.get(position).getPhone()) ||
                            todayList.get(position).getPhone() == null) {
                        ((ViewHolder) holder).mCallView.setVisibility(View.INVISIBLE);
                    } else {
                        ((ViewHolder) holder).mCallView.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).mCallView.setTag(todayList.get(position).getPhone());
                    }
                }else if (tomorrowList.size()!=0&&position>=todayList.size()
                        &&position<todayList.size()+tomorrowList.size()) {
                    if (position==todayList.size()) {
                        ((ViewHolder) holder).mRemarkView.setText("明天");
                    }else ((ViewHolder) holder).mRemarkView.setVisibility(View.GONE);

                    ((ViewHolder) holder).mNameView.setText(tomorrowList.get(position -todayList.size()).getName());
                    ((ViewHolder) holder).mtagView.setBackground(context.getDrawable(R.drawable.shape_tag_yellow));
                    ((ViewHolder) holder).mtagView.setText("明天");
                    String url= tomorrowList.get(position - todayList.size()).getAvatar().toString();
                    Glide.with(context).load(url)
                            .placeholder(R.mipmap.ic_launcher)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(((ViewHolder) holder).mIconView);
                    if ("".equals(tomorrowList.get(position - todayList.size()).getPhone()) ||
                            tomorrowList.get(position- todayList.size()).getPhone() == null) {
                        ((ViewHolder) holder).mCallView.setVisibility(View.INVISIBLE);
                    } else {
                        ((ViewHolder) holder).mCallView.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).mCallView.setTag(tomorrowList.get(position- todayList.size()).getPhone());
                    }

                }else if (nextWeekList.size()!=0) {
                    if (position ==todayList.size()+tomorrowList.size()) {
                        ((ViewHolder) holder).mRemarkView.setText("下周");
                    }else ((ViewHolder) holder).mRemarkView.setVisibility(View.GONE);

                    ((ViewHolder) holder).mNameView.setText(nextWeekList.get(position - todayList.size() - tomorrowList.size()).getName());
                    ((ViewHolder) holder).mtagView.setBackground(context.getDrawable(R.drawable.shape_tag_purple));
                    ((ViewHolder) holder).mtagView.setText("下周");

                    String url= nextWeekList.get(position -todayList.size()-tomorrowList.size()).getAvatar().toString();
                    Glide.with(context).load(url)
                            .placeholder(R.mipmap.ic_person)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(((ViewHolder) holder).mIconView);
                    if ("".equals(nextWeekList.get(position - todayList.size()-tomorrowList.size()).getPhone()) ||
                            nextWeekList.get(position - todayList.size()-tomorrowList.size()).getPhone() == null) {
                        ((ViewHolder) holder).mCallView.setVisibility(View.INVISIBLE);
                    } else {
                        ((ViewHolder) holder).mCallView.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).mCallView.setTag(nextWeekList.get(position - todayList.size()-tomorrowList.size()).getPhone());
                    }
                }


                ((ViewHolder) holder).mCallView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("您确定拨打此号码？")
                                .setContentText((String)v.getTag())
                                .setConfirmText("确定")
                                .setConfirmClickListener(
                                        new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (String)v.getTag()));
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
        if (mReminds!= null) {
            return mReminds.getToday().size()+mReminds.getTomorrow().size()+mReminds.getNextWeek().size()+ 1;
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


