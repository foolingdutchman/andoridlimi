package com.limi88.financialplanner.ui.mine;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.pojo.Pickers;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.adapters.MeProfileAdapter;
import com.limi88.financialplanner.ui.customers.ClientPresenter;
import com.limi88.financialplanner.ui.customers.ProvincePickActivity;

import com.limi88.financialplanner.ui.widget.MyScrollPickerView;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import gun0912.tedbottompicker.TedBottomPicker;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

public class MeProfileActivity extends BaseFragmentActivity implements MineContract.MeProfileView,View.OnClickListener {
    @Bind(R.id.common_title)
    TextView mTitleView;
    @Bind(R.id.tv_header_back)
    TextView mLeftTitle;
    @Bind(R.id.ll_left_btn)
    LinearLayout mLeftBtn;
    @Bind(R.id.rv_me_profile)
    RecyclerView mRecyclerView;
    @Bind(R.id.btn_new_cancel)
    Button mCancelButton;
    @Bind(R.id.btn_new_confirm)
    Button mConfirmButton;
    private MeProfileAdapter meProfileAdapter;
    private User user;
    private int provinceId = -1;

    private String gender = "";
    MineContract.MineView mineView;
    private Map<String, String> textParams;
    private Map<String, String> fileParams = new HashMap<>();
    private Dialog dialog;
    private View inflate;
    private TextView mPickerConfirmText;
    private TextView mAreaView;
    private EditText mNameView;
    private EditText mOrganizationView;
    private EditText mDesView;
    private ImageView mHeaderView;
    private ImageView mProfileView;
    private LinearLayout backBtn;

    private MyScrollPickerView myScrollPickerView;
    private Window dialogWindow;
    private WindowManager mWidowManager;
    private WindowManager.LayoutParams lp;
    private Display d;
    private Province[] provinces = null;
    private List<String> imgList;
    @Inject
    ClientPresenter clientPresenter;
    @Inject
    MinePresenter mPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_me_profile;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initViewsAndEvents() {
        initializeInjector();
        mTitleView.setText("我的资料");
        mTitleView.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText("我的资料");
        mLeftBtn.setVisibility(View.VISIBLE);
        mCancelButton.setVisibility(View.GONE);
        mPresenter.setProfileView(this);
        user = DataCenter.getCurrentUser();
        meProfileAdapter = new MeProfileAdapter(this, user, provinces);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(meProfileAdapter);
        mCancelButton.setOnClickListener(this);
        mConfirmButton.setOnClickListener(this);
        setResult(Constants.MINE_FLAG);
        meProfileAdapter.setOnItemClickListener((view, obj, position) -> {
            if (user != null && user.getData() != null) {

                if (position == 0) {
                    mHeaderView = (ImageView) view;
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        loadHeaderView();
                    }

                } else if (position == 1 || position == 4) {
                    if (position == 1) {
                        user.getData().setName((String) obj);
                        mNameView = (EditText) view;
                    } else if (position == 4) {
                        user.getData().setOrganization((String) obj);
                        mOrganizationView = (EditText) view;
                    }
                } else if (position == 3) {
                    mAreaView = (TextView) view;
                    startActivityForResult(new Intent(this,ProvincePickActivity.class),Constants.PICK_PROVINCE_CODE);
//                    CityPickingFragment cityPickingFragment = new CityPickingFragment();
//                    cityPickingFragment.setMineView(MeProfileActivity.this);
//                    this.getSupportFragmentManager().beginTransaction()
//                            .add(R.id.main_container, cityPickingFragment).commit();

                } else if (position == 2) {

                    if (dialog == null) {
                        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
                    }
                    //填充对话框的布局
                    inflate = LayoutInflater.from(this).inflate(R.layout.gender_pick_layout, null);
                    //初始化控件
                    myScrollPickerView = (MyScrollPickerView) inflate.findViewById(R.id.msp_gender);
                    mPickerConfirmText = (TextView) inflate.findViewById(R.id.tv_gender_confirm);

                    mPickerConfirmText.setOnClickListener(v1 -> {
                                ((TextView) view).setText(gender);
                                user.getData().setGender(gender);
                                dialog.dismiss();
                            }
                    );
                    initPickerData();
                    initPickerLinstener();
                    //将布局设置给Dialog
                    dialog.setContentView(inflate);
                    //获取当前Activity所在的窗体
                    dialogWindow = dialog.getWindow();
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity(Gravity.BOTTOM);
                    //获得窗体的属性
                    mWidowManager = this.getWindowManager();
                    lp = dialogWindow.getAttributes();
                    d = mWidowManager.getDefaultDisplay(); // 获取屏幕宽、高用
                    lp.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.5
                    lp.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.8
                    lp.y = 0;//设置Dialog距离底部的距离
                    dialogWindow.setAttributes(lp);
                    dialog.show();//显示对话框
                } else if (position == 5) {
                    mDesView = (EditText) view;
                    user.getData().setDesc((String) obj);

                } else if (position == 6) {
                    Log.i("myprofile", "onclicked--------" + (int) obj);
                    mProfileView = (ImageView) view;
                    if (((int) obj) == 1) {

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                    Constants.PROFILE1_REQUEST_CODE);
                        } else {
                            loadProfileView(0);
                        }

                    } else if (((int) obj) == 2) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                    Constants.PROFILE2_REQUEST_CODE);
                        } else {
                            loadProfileView(1);
                        }
                    } else if (((int) obj) == 3) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                    Constants.PROFILE3_REQUEST_CODE);
                        } else {
                            loadProfileView(2);
                        }

                    }
                }
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }


    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void loadProfileView(int position) {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(this)
                .showCameraTile(false)
                .setOnImageSelectedListener(uri -> {
                    // here is selected uri
                    Glide.with(this).load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .crossFade()
                            .bitmapTransform(new CropSquareTransformation(this))
                            .into(mProfileView);
//                                ((ImageView)v).setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
//                    fileParams.put("user[avatar]", uri.getPath());
//                    user.getData().setAvatarUrl(uri.getPath());

                    if (user.getData() != null) {
                        imgList = user.getData().getPersonalImages();
                    }
                    if (imgList == null) {
                        imgList = new ArrayList<String>();
                        user.getData().setPersonalImages(imgList);
                    }
                    if (position > user.getData().getPersonalImages().size() - 1) {
                        user.getData().getPersonalImages().add(uri.getPath());
                    } else {
                        user.getData().getPersonalImages().remove(position);
                        user.getData().getPersonalImages().add(position, uri.getPath());
                    }

                    Log.i("newClient", uri.toString());
                    Log.i("newClient", uri.getPath());
                })
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                loadHeaderView();
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------denied");
            }
        }
        if (requestCode == Constants.PROFILE1_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                loadProfileView(0);

                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE1------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE1------------denied");
            }
        }
        if (requestCode == Constants.PROFILE2_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                loadProfileView(1);
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE2------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE2------------denied");
            }
        }
        if (requestCode == Constants.PROFILE3_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                loadProfileView(2);
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE3------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE3------------denied");
            }
        }
    }

    private void loadHeaderView() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(this)
                .showCameraTile(false)
                .setOnImageSelectedListener(uri -> {
                    // here is selected uri
                    Glide.with(this).load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(this))
                            .into(mHeaderView);
//                                ((ImageView)v).setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
                    fileParams.put("user[avatar]", uri.getPath());
                    user.getData().setAvatarUrl(uri.getPath());
                    Log.i("newClient", uri.toString());
                    Log.i("newClient", uri.getPath());
                })
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }


    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }



    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_cancel:
                finish();
                break;
            case R.id.btn_new_confirm:
                mNameView = meProfileAdapter.getNameView();
                mDesView = meProfileAdapter.getDiscView();
                mOrganizationView = meProfileAdapter.getOrgnView();
                if (mNameView != null) {
                    user.getData().setName(mNameView.getText().toString());
                }
                if (mOrganizationView != null) {
                    user.getData().setOrganization(mOrganizationView.getText().toString());
                }
                if (mDesView != null) {

                    user.getData().setDesc(mDesView.getText().toString());
                }
                mPresenter.uploadUserInfo(user);
                break;
            case R.id.ll_left_btn:
                onBackPressed();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode== Constants.PICK_PROVINCE_CODE&&resultCode==Constants.PROVINCE_PICK_SUCCESS) {
            mAreaView.setText(data.getStringExtra(Constants.PROVINCE_FLAG));
        }
    }

    private void initPickerData() {

        List<Pickers> list = new ArrayList<Pickers>();
        String[] id = new String[]{"1", "2"};
        String[] name = new String[]{"男", "女"};
        for (int j = 0; j < name.length; j++) {
            list.add(new Pickers(name[j], id[j]));
        }
        // 设置数据，默认选择第一条
        myScrollPickerView.setData(list);
        myScrollPickerView.setSelected(0);
        gender = name[0];

    }

    private void initPickerLinstener() {

        myScrollPickerView.setOnSelectListener(pickers -> gender = pickers.getShowConetnt());
    }


    @Override
    public void setCity(Province province) {
        this.mAreaView.setText(province.getName());
        provinceId = province.getId();
        user.getData().setProvinceId(provinceId);
    }

    @Override
    public void setProvinces(Province[] provinces) {
        this.provinces = provinces;
        if (meProfileAdapter != null) {
            meProfileAdapter.setUser(user, provinces);
            meProfileAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void dismiss() {
        setResult(Constants.UPDATE_USER_INFO_CODE);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.setProfileView(null);
        RefWatcher refWatcher = MainApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }


    public void backPre(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
