package com.limi88.financialplanner.ui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;

import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by hehao
 * Date on 16/9/27.
 * Email: hao.he@limi88.com
 */
public class MeProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String[] titles = {
            "头像", "姓名", "性别", "地址"
           ,"公司"
    };
    private static final int TYPE1 = 1;
    private static final int FOOT_TYPE = 3;
    private static final int TYPE2 = 2;
    private static final int TYPE3 = 4;
    private static final int TYPE4 = 5 ;
    private Context context;
    private String avatar;
    private View.OnClickListener onClickListener;
    private OnItemClickListener onItemClickListener;
    private EditText nameView;
    private EditText orgnView;
    private EditText discView;
    private User user;
    private Province[] provinces;

    @Inject
    public MeProfileAdapter(Context context,User user,Province[] provinces) {
        this.context = context;
        this.user=user;
        this.provinces=provinces;
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
        }else if (viewType==TYPE3) {
            return new SignHolder(LayoutInflater.from(context).inflate(R.layout.me_profile_list_itme2, parent, false));

        }else if (viewType==TYPE4) {
            return new GallaryHolder(LayoutInflater.from(context).inflate(R.layout.me_profile_list_itme3, parent, false));

        }else
        return new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            if (position == 0) {
                ((ViewHolder) holder).mIconView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.GONE);
                if (user!=null&&user.getData()!=null) {
                    if (user.getData()!=null) {
                        avatar = user.getData().getAvatarUrl();
                    }

                    Glide.with(context).load(avatar)
                            .placeholder(R.mipmap.ic_default_avatar)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(((ViewHolder) holder).mIconView);
                }
//                ((ViewHolder) holder).mIconView.setOnClickListener(v -> onItemClickListener.onItemClick(v, null, position));
            holder.itemView.setOnClickListener( v -> onItemClickListener.onItemClick( ((ViewHolder) holder).mIconView,null,position));
            } else if (position == 1|| position == 4) {

                ((ViewHolder) holder).mIconView.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.GONE);
                ((ViewHolder) holder).mArrowView.setVisibility(View.GONE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.VISIBLE);
                if (position == 1) {
                    nameView=((ViewHolder) holder).mEdittext;
                    if (user != null && user.getData() != null && user.getData().getName() != "") {
                        ((ViewHolder) holder).mEdittext.setText(user.getData().getName());
                    } else if (user != null && user.getData() != null && user.getData().getPhone() != ""){
                        ((ViewHolder) holder).mEdittext.setText(user.getData().getPhone().substring(user.getData().getPhone().length()-4));
                    } else  ((ViewHolder) holder).mEdittext.setText("未设置");
                }else if (position == 4) {
                    orgnView=((ViewHolder) holder).mEdittext;
                    if (user!=null&&user.getData()!=null) {
                        ((ViewHolder) holder).mEdittext.setText(user.getData().getOrganization());
                    }
                }
//                ((ViewHolder) holder).mEdittext.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
//                    }
//                    @Override
//                    public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
//                        ((ViewHolder) holder).mEdittext.setText(sequence+"");
//                        onItemClickListener.onItemClick(((ViewHolder) holder).mEdittext, sequence+"", position);
//                    }
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                    }
//                });
                        ((ViewHolder) holder).mEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            //隐藏软键盘
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            //添加搜索
                            String s = ((ViewHolder) holder).mEdittext.getText().toString();
                            onItemClickListener.onItemClick(((ViewHolder) holder).mEdittext, s, position);
                        }
                        return true;
                    }
                });

            } else if (position == 2 ) {
                ((ViewHolder) holder).mIconView.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mArrowView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.GONE);
                String gender="未设置";
                if (user!=null&&user.getData()!=null&&user.getData().getGender()!=null&&user.getData().getGender()!="") {
                    ((ViewHolder) holder).mtagView.setText(user.getData().getGender());
                }else ((ViewHolder) holder).mtagView.setText("未设置");

//                        ((ViewHolder) holder).mtagView.setOnClickListener(v -> onItemClickListener.onItemClick(v, null, position));
                holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick( ((ViewHolder) holder).mtagView,null,position));
            } else if (position == 3 ) {
                ((ViewHolder) holder).mIconView.setVisibility(View.GONE);
                ((ViewHolder) holder).mtagView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mArrowView.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).mEdittext.setVisibility(View.GONE);
                if (user!=null&&user.getData()!=null) {

                    String area = "未设置";
                    if (provinces != null && provinces.length != 0) {
                        for (int i = 0; i < provinces.length; i++) {

                            if (user.getData().getProvinceId() == provinces[i].getId()) {
                                area = provinces[i].getName();
                            }
                        }
                    }
                    ((ViewHolder) holder).mtagView.setText(area);
                }
//                ((ViewHolder) holder).mtagView.setOnClickListener(v -> onItemClickListener.onItemClick(v, null, position));
             holder.itemView.setOnClickListener( v -> onItemClickListener.onItemClick( ((ViewHolder) holder).mtagView,null,position));
            }
            ((ViewHolder) holder).mNameView.setText(titles[position]);
        } else if (holder instanceof SignHolder) {
            if (user!=null&&user.getData()!=null) {
                ((SignHolder) holder).mEdittext.setText(user.getData().getDesc());
                discView=((SignHolder) holder).mEdittext;
            }
            ((SignHolder) holder).mEdittext.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    //添加搜索
                    String s=((SignHolder) holder).mEdittext.getText().toString();
                    onItemClickListener.onItemClick(((SignHolder) holder).mEdittext, s, position);
                }
                return true;
            });
        }else if (holder instanceof GallaryHolder) {
            if (user!=null&&user.getData()!=null) {
                List<String> imgUrls=user.getData().getPersonalImages();
                if (imgUrls.size()!=0) {
                    Glide.with(context).load(imgUrls.get(0))
                            .placeholder(R.mipmap.ic_default_logo)
                            .crossFade()
                            .bitmapTransform(new CropSquareTransformation(context))
                            .into(((GallaryHolder) holder).imageView1);
                }
                if (imgUrls.size()>1) {
                    Glide.with(context).load(imgUrls.get(1))
                            .placeholder(R.mipmap.ic_default_logo)
                            .crossFade()
                            .bitmapTransform(new CropSquareTransformation(context))
                            .into(((GallaryHolder) holder).imageView2);
                }
                if (imgUrls.size()>2) {
                    Glide.with(context).load(imgUrls.get(2))
                            .placeholder(R.mipmap.ic_default_logo)
                            .crossFade()
                            .bitmapTransform(new CropSquareTransformation(context))
                            .into(((GallaryHolder) holder).imageView3);
                }

            }
            ((GallaryHolder) holder).imageView1.setOnClickListener(view -> onItemClickListener.onItemClick(view, 1, position));
              ((GallaryHolder) holder).imageView2.setOnClickListener(view -> onItemClickListener.onItemClick(view, 2, position));
              ((GallaryHolder) holder).imageView3.setOnClickListener(view -> onItemClickListener.onItemClick(view, 3, position));

        }

    }

    @Override
    public int getItemCount() {

        return titles.length + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() - 1) {
            if (position == 4 ) {
                return TYPE2;
            } else if (position==5) {
                return TYPE3;
            }else if (position==6) {
                return TYPE4;
            }else return TYPE1;
        } else return FOOT_TYPE;
    }

    public EditText getNameView() {
        return nameView;
    }

    public EditText getOrgnView() {
        return orgnView;
    }

    public EditText getDiscView() {
        return discView;
    }

    public void setUser(User user,Province[]provinces) {
        this.user = user;
        this.provinces=provinces;

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
    class SignHolder extends RecyclerView.ViewHolder {

        EditText mEdittext;

        public SignHolder(View itemView) {
            super(itemView);
            mEdittext = (EditText) itemView.findViewById(R.id.et_sign);
        }
    }
    class GallaryHolder extends RecyclerView.ViewHolder {

        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;

        public GallaryHolder(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.iv_show1);
            imageView2 = (ImageView) itemView.findViewById(R.id.iv_show2);
            imageView3 = (ImageView) itemView.findViewById(R.id.iv_show3);
        }
    }

    class ViewHolder6 extends RecyclerView.ViewHolder {

        public ViewHolder6(View itemView) {
            super(itemView);

        }
    }


}