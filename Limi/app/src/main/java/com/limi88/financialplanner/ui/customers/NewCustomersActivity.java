package com.limi88.financialplanner.ui.customers;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.pojo.Pickers;
import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.pojo.clients.SearchConditions;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.ui.adapters.NewCustomerAdapter;
import com.limi88.financialplanner.ui.widget.MyScrollPickerView;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import gun0912.tedbottompicker.TedBottomPicker;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hehao
 * Date on 16/10/31.
 * Email: hao.he@sunanzq.com
 */
public class NewCustomersActivity extends BaseFragmentActivity implements ClientContract.AddClientView, View.OnClickListener {
    private static final int DATA_TYPE_1 = 111;
    private static final int DATA_TYPE_2 = 222;
    private static final int DATA_TYPE_3 = 333;
    private ClientPresenter mPresenter;
    @Bind(R.id.common_title)
    TextView mTitleView;
    @Bind(R.id.tv_header_back)
    TextView mLeftView;
    @Bind(R.id.rv_add_customer)
    RecyclerView mRecycleView;
    @Bind(R.id.btn_delete)
    Button mDeleteBtn;
    @Bind(R.id.btn_new_confirm)
    Button mConfirmBtn;
    @Bind(R.id.ll_left_btn)
    LinearLayout backLayout;
    private NewCustomerAdapter mCustomerAdapter;
    private Map<String, String> uploadList = new HashMap<>();
    private Dialog dialog;
    private View inflate;
    private TedBottomPicker tedBottomPicker;

    private TextView mPickerConfirmText;
    private TextView mAreaView;
    private TextView mTagsView;
    private ImageView mHeaderView;
    private MyScrollPickerView myScrollPickerView;
    private Window dialogWindow;
    private WindowManager mWidowManager;
    private WindowManager.LayoutParams lp;
    private Display d;
    private String gender = "";
    private String birthDate = "";
    private String clientsName = "";
    private String mobile = "";
    private String area = "";
    private boolean isFromWeb = false;
    int provinceId = -1;
    String status = "";
    String catergory = "";
    String tags = "";
    private List<Pickers> list; // 滚动选择器数据
    private String[] id;
    private String[] name;
    private List<Province> provinceList;
    private ClientContract.ClientView mClientView;

    private SearchConditions searchConditions;
    private Real mReal;
    private Bundle mBundle;
    private Intent mIntent;
    private SweetAlertDialog mAlertDialog;
    @Inject
    ClientPresenter clientPresenter;


    @Override
    protected void getBundleExtras(Bundle extras) {
        mBundle = extras;
        if (mBundle != null) {
            mReal = mBundle.getParcelable(Constants.MODIFY_CUSTOMER_FLAG);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_new_customers;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initViewsAndEvents() {

        initializeInjector();
        mTitleView.setVisibility(View.GONE);

        mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在上传");
        mIntent = getIntent();
        clientPresenter.setAddClientView(this);
        clientPresenter.getProvinceInfo();

        backLayout.setVisibility(View.VISIBLE);
        backLayout.setOnClickListener(this);
        setResult(Constants.CLIENTS_FLAG);
        if (mReal != null) {
            mDeleteBtn.setVisibility(View.VISIBLE);
            mLeftView.setText(R.string.customer_edit_customer);
            isFromWeb = true;
        } else {
            mDeleteBtn.setVisibility(View.GONE);
            mLeftView.setText(R.string.customer_new_customer);
        }
        mCustomerAdapter = new NewCustomerAdapter(this, mReal, provinceList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleView.setAdapter(mCustomerAdapter);
        mCustomerAdapter.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mCustomerAdapter.setOnItemClickListener((view1, obj, position) -> {

            if (position == 0) {
                mHeaderView = (ImageView) view1;
//                PermissionUtils.getInstance(this).checkStoragePermission();
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
                    clientsName = (String) obj;
                } else if (position == 4) {
                    mobile = (String) obj;
                }
            } else if (position == 3) {
                birthDate = "未设置";
                ((TextView) (view1)).setText("未设置");
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        (v, year, monthOfYear, dayOfMonth) -> {
                            monthOfYear++;
                            if (year >= now.get(Calendar.YEAR)&&monthOfYear>=now.get(Calendar.MONTH)&&dayOfMonth>now.get(Calendar.DAY_OF_MONTH)) {
                                ToastUtils.showToast(R.string.birthday_error);
                            } else {
                                birthDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                                ((TextView) (view1)).setText(birthDate);
                            }
                        }, now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(this.getFragmentManager(), "请选择日期");

            } else if (position == 2 || position == 6 || position == 7) {
                if (position == 2) {
                    if (dialog == null) {
                        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
                    }
                    //填充对话框的布局
                    inflate = LayoutInflater.from(this).inflate(R.layout.gender_pick_layout, null);
                    //初始化控件
                    myScrollPickerView = (MyScrollPickerView) inflate.findViewById(R.id.msp_gender);
                    mPickerConfirmText = (TextView) inflate.findViewById(R.id.tv_gender_confirm);

                    mPickerConfirmText.setOnClickListener(v1 -> {
                                ((TextView) view1).setText(gender);
                                uploadList.put("gender", gender);
                                dialog.dismiss();
                            }
                    );
                    initPickerData(DATA_TYPE_1);
                    initPickerLinstener(DATA_TYPE_1);
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

                } else if (position == 6) {
                    if (dialog == null) {
                        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
                    }
                    //填充对话框的布局
                    inflate = LayoutInflater.from(this).inflate(R.layout.gender_pick_layout, null);
                    //初始化控件
                    myScrollPickerView = (MyScrollPickerView) inflate.findViewById(R.id.msp_gender);
                    mPickerConfirmText = (TextView) inflate.findViewById(R.id.tv_gender_confirm);

                    mPickerConfirmText.setOnClickListener(v1 -> {
                                ((TextView) view1).setText(status);
                                uploadList.put("status", status);
                                dialog.dismiss();
                            }
                    );
                    initPickerData(DATA_TYPE_2);
                    initPickerLinstener(DATA_TYPE_2);
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
                } else if (position == 7) {
                    if (dialog == null) {
                        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
                    }
                    //填充对话框的布局
                    inflate = LayoutInflater.from(this).inflate(R.layout.gender_pick_layout, null);
                    //初始化控件
                    myScrollPickerView = (MyScrollPickerView) inflate.findViewById(R.id.msp_gender);
                    mPickerConfirmText = (TextView) inflate.findViewById(R.id.tv_gender_confirm);

                    mPickerConfirmText.setOnClickListener(v1 -> {
                                ((TextView) view1).setText(catergory );
                                uploadList.put("catergory", catergory);
                                dialog.dismiss();
                            }
                    );
                    initPickerData(DATA_TYPE_3);
                    initPickerLinstener(DATA_TYPE_3);
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
                }
//       将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog.show();//显示对话框

            } else if (position == 5) {

                startActivityForResult(new Intent(this, ProvincePickActivity.class), Constants.PICK_PROVINCE_CODE);
//                CityPickingFragment cityPickingFragment = new CityPickingFragment();
//                cityPickingFragment.setAddClientView(NewCustomerActivity.this);
//                cityPickingFragment.setmPresenter(mPresenter);
//
//                this.getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_container, cityPickingFragment).commit();
                mAreaView = (TextView) view1;
            } else if (position == 8) {
                mTagsView = (TextView) view1;
                Intent intent = new Intent(this, CustomerTagsActivity.class);
                String tags = mTagsView.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("tags", tags);
                bundle.putParcelable("searchConditions", searchConditions);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constants.TAG_PICK_CODE);
//                CustomerTagsFragment fragment = new CustomerTagsFragment();
//                fragment.setAddClientView(NewCustomerActivity.this);
//                fragment.setSearchConditions(searchConditions);
//                String tags=mTagsView.getText().toString();
//                fragment.setTags(tags);
//                this.getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_container, fragment).commit();

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
    protected boolean isBindEventBusHere() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                loadHeaderView();
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------denied");
            }
        }
        if (requestCode == Constants.CAMERA_REQUEST_CODE) {

        }
    }

    private void loadHeaderView() {
        tedBottomPicker = new TedBottomPicker.Builder(this)
                .showCameraTile(true)
                .setTitle("请选择图片")
                .setOnImageSelectedListener(uri -> {
                    // here is selected uri
                    Glide.with(this).load(uri)
                            .placeholder(R.mipmap.ic_launcher)
                            .crossFade()
                            .bitmapTransform(new CropCircleTransformation(this))
                            .into(mHeaderView);
//                                ((ImageView)v).setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
                    uploadList.put("photo", uri.getPath());
                    Log.i("newClient", uri.toString());
                    Log.i("newClient", uri.getPath());
                })
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

    public void setmClientView(ClientContract.ClientView mClientView) {
        this.mClientView = mClientView;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_new_confirm) {
            clientsName = mCustomerAdapter.getNameView().getText().toString();
            mobile = mCustomerAdapter.getMobileView().getText().toString();
            if (clientsName.equals("") || clientsName == null) {
                ToastUtils.showToast("客户姓名不能为空");
                return;
            } else {
                showDataLoading();
                mConfirmBtn.setClickable(false);
                uploadList.put("name", clientsName);
                uploadList.put("phone", mobile);
                uploadList.put("gender", gender);
                uploadList.put("birthDate", birthDate);
                uploadList.put("provinceId", provinceId + "");
                uploadList.put("status", status);
                uploadList.put("catergory", catergory);
                uploadList.put("tags", tags);
                if (mReal != null) {
                    uploadList.put("id", mReal.getId() + "");
                }
                clientPresenter.creatNewClient(uploadList);
                setResult(Constants.MODIFY_CUSTOMER_CODE, mIntent);
//                mClientView.addNewClient(uploadList);
            }

        } else if (v.getId() == R.id.btn_delete) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("您确定删除该用户？")
                    .setConfirmText("确定")
                    .setConfirmClickListener(
                            sweetAlertDialog -> {
                                sweetAlertDialog.dismissWithAnimation();
                                clientPresenter.deleteClient(mReal.getId() + "");
                                setResult(Constants.DELETE_CUSTOMER_CODE, mIntent);
                            }
                    ).setCancelText("取消")
                    .setCancelClickListener(null).show();
        } else if (v.getId() == R.id.ll_left_btn) {
            finish();
        }
    }

    private void initPickerData(int i) {
        switch (i) {
            case DATA_TYPE_1:
                list = new ArrayList<Pickers>();
                id = new String[]{"1", "2"};
                name = new String[]{"男", "女"};
                for (int j = 0; j < name.length; j++) {
                    list.add(new Pickers(name[j], id[j]));
                }
                // 设置数据，默认选择第一条
                myScrollPickerView.setData(list);
                myScrollPickerView.setSelected(0);
                gender = name[0];
                break;
            case DATA_TYPE_2:
                list = new ArrayList<Pickers>();
                id = new String[]{"1", "2"};
                name = new String[]{"已签单", "未签单"};
                for (int j = 0; j < name.length; j++) {
                    list.add(new Pickers(name[j], id[j]));
                }
                // 设置数据，默认选择第一条
                myScrollPickerView.setData(list);
                myScrollPickerView.setSelected(0);
                status = name[0];
                break;
            case DATA_TYPE_3:
                list = new ArrayList<Pickers>();
                id = new String[]{"1", "2", "3", "4"};
                name = new String[]{"A", "B", "C", "D"};
                for (int j = 0; j < name.length; j++) {
                    list.add(new Pickers(name[j], id[j]));
                }
                // 设置数据，默认选择第一条
                myScrollPickerView.setData(list);
                myScrollPickerView.setSelected(0);
                catergory = name[0];
                break;

        }
    }

    private void initPickerLinstener(int i) {
        switch (i) {
            case DATA_TYPE_1:
                myScrollPickerView.setOnSelectListener(pickers -> gender = pickers.getShowConetnt());
                break;
            case DATA_TYPE_2:
                myScrollPickerView.setOnSelectListener(pickers -> status = pickers.getShowConetnt());
                break;
            case DATA_TYPE_3:
                myScrollPickerView.setOnSelectListener(pickers -> catergory = pickers.getShowConetnt());
                break;
        }
    }


    private void dismiss() {
        finish();
    }

    @Override
    public void setCity(Province province) {
        if (province != null) {
            this.area = province.getName();
            mAreaView.setText(area);
            provinceId = province.getId();
        }
    }

    @Override
    public void loadProvince(List<Province> provinces) {
        this.provinceList = provinces;
        mCustomerAdapter.setProvinces(provinces);
        mCustomerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDataLoading() {
        mAlertDialog.dismiss();
    }

    @Override
    public void hideDataLoading() {
        mAlertDialog.show();
    }


    @Override
    public void setTags(String tags) {
        mTagsView.setText(tags);
        this.tags = tags;
    }

    @Override
    public void dismissAddClientUI() {


        if (mClientView != null) {
            mClientView.loadDataToUI();
        }
        hideDataLoading();
        mConfirmBtn.setClickable(true);
        dismiss();
    }

    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }

    public void setmPresenter(ClientPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mClientView = null;
        RefWatcher refWatcher = MainApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PICK_PROVINCE_CODE && resultCode == Constants.PROVINCE_PICK_SUCCESS) {
            mAreaView.setText(data.getStringExtra(Constants.PROVINCE_FLAG));
        } else if (requestCode == Constants.TAG_PICK_CODE && resultCode == Constants.TAG_PICK_SUCCESS) {
            mTagsView.setText(data.getStringExtra(Constants.TAGS_FLAG));
        }
        if (tedBottomPicker!=null) {
            tedBottomPicker.onActivityResult(requestCode,resultCode,data);
        }


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
