package com.limi88.financialplanner.ui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.PermissionUtils;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hehao
 * Date on 16/9/22.
 * Email: hao.he@limi88.com
 */
public class NewCustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String[] titles = {
            "头像", "姓名", "性别", "生日"
            , "手机号", "地区", "客户状态", "客户分类", "标签"
    };
    private static final int TYPE1 = 1;
    private static final int FOOT_TYPE = 3;
    private static final int TYPE2 = 2;
    private Context context;

    private View.OnClickListener onClickListener;
    private OnItemClickListener onItemClickListener;
    private EditText nameView;
    private EditText mobileView;
    private Real real;
    private List<Province> provinces;

    @Inject
    public NewCustomerAdapter(Context context, Real real, List<Province> provinces) {
        this.context = context;
        this.real = real;
        this.provinces = provinces;
        this.provinces = Constants.PROVINCES_LIST;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public void setReal(Real real) {
        this.real = real;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE1) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.addlist_item2, parent, false));
        } else if (viewType == TYPE2) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.addlist_item1, parent, false));
        }

        return new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            if (position == 0) {
                ((ViewHolder) holder).mArrowView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mIconView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.GONE);
                if (real != null && real.getAvatar() != null && !real.getAvatar().equals("")) {
                    if (real.getAvatar() instanceof String) {
                        Glide.with(context).load(real.getAvatar())
                                .placeholder(R.mipmap.ic_default_avatar)
                                .crossFade()
                                .bitmapTransform(new CropCircleTransformation(context))
                                .into(((ViewHolder) holder).mIconView);

                    }
//                    else if (real.getAvatar() instanceof LinkedHashMap) {
//                        String avatar= (String) ((LinkedHashMap) real.getAvatar()).get("url");
//                        Glide.with(context).load(avatar)
//                                .placeholder(R.mipmap.ic_default_logo)
//                                .crossFade()
//                                .bitmapTransform(new CropCircleTransformation(context))
//                                .into(((ViewHolder) holder).mIconView);
//
//                    }

                }

//                ((ViewHolder) holder).mIconView.setOnClickListener(v -> onItemClickListener.onItemClick(v, null, position));
                holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(((ViewHolder) holder).mIconView, null, position));
            } else if (position == 1 || position == 4) {
                ((ViewHolder) holder).mIconView.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.GONE);
                ((ViewHolder) holder).mArrowView.setVisibility(View.GONE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.VISIBLE);
                if (position == 1) {
                    if (real != null && real.getName() != null && !real.getName().equals("")) {
                        ((ViewHolder) holder).mEdittext.setText(real.getName());
                    }
                    nameView = ((ViewHolder) holder).mEdittext;

                } else if (position == 4) {
                    if (real != null && real.getPhone() != null && !real.getPhone().equals("")) {
                        ((ViewHolder) holder).mEdittext.setText(real.getPhone());
                    }
                    mobileView = ((ViewHolder) holder).mEdittext;
                    mobileView.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                ((ViewHolder) holder).mEdittext.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        //隐藏软键盘
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        //添加搜索
                        String s = ((ViewHolder) holder).mEdittext.getText().toString();
//                        onItemClickListener.onItemClick(((ViewHolder) holder).mEdittext, s, position);
                    }
                    return true;
                });
                holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(((ViewHolder) holder).mEdittext, null, position));
            } else if (position == 2 || position >= 5) {
                ((ViewHolder) holder).mIconView.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mArrowView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.GONE);
                if (position == 2 && real != null && real.getGender() != null && !real.getGender().equals("")) {
                    ((ViewHolder) holder).mtagView.setText(real.getGender());
                } else if (position == 5 && real != null && real.getProvinceId() != -1 && provinces != null && provinces.size() != 0) {
                    for (int i = 0; i < provinces.size(); i++) {
                        if (provinces.get(i).getId() == real.getProvinceId()) {
                            ((ViewHolder) holder).mtagView.setText(provinces.get(i).getName());
                            break;
                        } else {
                            ((ViewHolder) holder).mtagView.setText("暂未设置");
                        }
                    }

                } else if (position == 6 && real != null && real.getStatus() != null && !real.getStatus().equals("")) {
                    ((ViewHolder) holder).mtagView.setText(real.getStatus());
                } else if (position == 7 && real != null && real.getLevel() != null && !real.getLevel().equals("")) {
                    ((ViewHolder) holder).mtagView.setText(real.getLevel());
                } else if (position == 8 && real != null && real.getTags() != null && !real.getTags().equals("")) {
                    ((ViewHolder) holder).mtagView.setText(real.getTags());
                }


//                ((ViewHolder) holder).mtagView.setOnClickListener(v -> onItemClickListener.onItemClick(v, null, position));
                holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(((ViewHolder) holder).mtagView, null, position));
            } else if (position == 3) {
                ((ViewHolder) holder).mIconView.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mArrowView.setVisibility(View.GONE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.GONE);
                if (position == 3 && real != null && real.getBirthday() != null && !real.getBirthday().equals("")) {
                    ((ViewHolder) holder).mtagView.setText(real.getBirthday());
                }
//                ((ViewHolder) holder).mtagView.setOnClickListener(v -> onItemClickListener.onItemClick(v, null, position));
                holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(((ViewHolder) holder).mtagView, null, position));
            }
            ((ViewHolder) holder).mNameView.setText(titles[position]);
        }

    }

    @Override
    public int getItemCount() {

        return titles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() - 1) {
            if (position == 4 || position == 5 || position == 8) {
                return TYPE2;
            } else return TYPE1;
        } else return FOOT_TYPE;
    }

    public EditText getNameView() {
        return nameView;
    }

    public EditText getMobileView() {
        return mobileView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIconView;
        ImageView mArrowView;
        TextView mNameView;
        EditText mEdittext;
        TextView mtagView;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconView = (ImageView) itemView.findViewById(R.id.iv_header);
            mArrowView = (ImageView) itemView.findViewById(R.id.iv_item_arrow);
            mNameView = (TextView) itemView.findViewById(R.id.tv_item_name);
            mtagView = (TextView) itemView.findViewById(R.id.tv_item_value);
            mEdittext = (EditText) itemView.findViewById(R.id.et_item_value);
        }
    }

    class ViewHolder6 extends RecyclerView.ViewHolder {

        public ViewHolder6(View itemView) {
            super(itemView);

        }
    }
}