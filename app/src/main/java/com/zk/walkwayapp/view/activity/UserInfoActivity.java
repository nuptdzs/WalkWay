package com.zk.walkwayapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.util.PickConfig;
import com.zk.library.common.common.CommmonSelectData;
import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.ui.CircleImageView;
import com.zk.library.common.ui.dialog.AlertDialog;
import com.zk.library.common.ui.dialog.SelectDialog;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.contract.IUserInfoContract;
import com.zk.walkwayapp.model.bean.UserInfo;
import com.zk.walkwayapp.presenter.user.UserInfoPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置用户个人信息
 */
@ContentView(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity<IUserInfoContract.IUserInfoPresenter> implements IUserInfoContract.IUserInfoView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.img_avatar)
    CircleImageView imgAvatar;
    @Bind(R.id.ll_avatar)
    LinearLayout llAvatar;
    @Bind(R.id.et_nick_name)
    EditText etNickName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.ll_home_address)
    LinearLayout llHomeAddress;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.ll_company_address)
    LinearLayout llCompanyAddress;
    @Bind(R.id.tv_work_time)
    TextView tvWorkTime;
    @Bind(R.id.ll_work_time)
    LinearLayout llWorkTime;
    @Bind(R.id.btn_save)
    Button btnSave;
    private int sexIndex;
    private int REQUEST_HOME = 100;
    private final static int REQUEST_PLACE_PICKER_HOME = 1001;
    private final static int REQUEST_PLACE_PICKER_COMPANY = 1002;


    @Override
    protected IUserInfoContract.IUserInfoPresenter getPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected void addEvent() {
        toolbar.setTitle("EditUserInfo");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

        presenter.loadUserInfo();
    }

    @OnClick({R.id.ll_avatar, R.id.tv_sex, R.id.ll_home_address, R.id.ll_company_address, R.id.ll_work_time, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
                new PickPhotoView.Builder(UserInfoActivity.this)
                        .setPickPhotoSize(1)   //select max size
                        .setShowCamera(true)   //is show camera
                        .setSpanCount(4)       //SpanCount
                        .setLightStatusBar(true)  // custom theme
                        .setStatusBarColor("#ffffff")   // custom statusBar
                        .setToolbarColor("#ffffff")   // custom toolbar
                        .setToolbarIconColor("#000000")   // custom toolbar icon
                        .setSelectIconColor("#00C07F")  // custom select icon
                        .start();
                break;
            case R.id.tv_sex:
                setBaseDataDialog(sexs,R.string.user_sex,sexIndex);
                break;
            case R.id.ll_home_address:
                PlacePicker.IntentBuilder intentBuilder =
                        new PlacePicker.IntentBuilder();
                Intent intent = null;
                try {
                    intent = intentBuilder.build(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Start the intent by requesting a result,
                // identified by a request code.
                startActivityForResult(intent, REQUEST_PLACE_PICKER_HOME);
                break;
            case R.id.ll_company_address:
                PlacePicker.IntentBuilder intentBuilder1 =
                        new PlacePicker.IntentBuilder();
                Intent intent1 = null;
                try {
                    intent1 = intentBuilder1.build(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Start the intent by requesting a result,
                // identified by a request code.
                startActivityForResult(intent1, REQUEST_PLACE_PICKER_COMPANY);

                break;
            case R.id.ll_work_time:
                break;
            case R.id.btn_save:
                presenter.saveUserInfo();
                break;
        }
    }

    UserInfo userInfo;
    @Override
    public UserInfo getUserInfo() {
        userInfo.setNickName(etNickName.getText().toString());
        userInfo.setSex(tvSex.getText().equals("male")?0:1);
        return userInfo;
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        tvCompany.setText(userInfo.getCompanyAddress());
        tvHome.setText(userInfo.getHomeAddress());
        Glide.with(this).load(userInfo.getAvatorPath()).into(imgAvatar);
        etNickName.setText(userInfo.getNickName());
        tvSex.setText(userInfo.getSex()==0?"male":"female");
        sexs = new ArrayList<>();
        CommmonSelectData data0 = new CommmonSelectData();
        data0.setCenterText("male");
        CommmonSelectData data1 = new CommmonSelectData();
        data1.setCenterText("female");
        sexs.add(data0);
        sexs.add(data1);
        sexIndex = userInfo.getSex();
        sexs.get(sexIndex).setSelected(true);
    }

    String sex;
    private ArrayList<CommmonSelectData>  sexs;
    /**
     * 弹出设置基础信息对话框
     */
    private void setBaseDataDialog(ArrayList<CommmonSelectData> dataList, final int stringId, int index) {
        final SelectDialog selectDialog = new SelectDialog(false, dataList, index);
        selectDialog.setTitle(getString(stringId));
        selectDialog.setDialogDelegate(new AlertDialog.AlertDialogDelegate() {
            @Override
            public void onButtonClick(AlertDialog dialog, View view, int index) {
                super.onButtonClick(dialog, view, index);
                switch (index) {
                    case 0://取消
                        dialog.dismiss();
                        break;
                    case 1://选中退出
                        dialog.dismiss();
                        int selectIndex = selectDialog.getSelected().get(0);
                        switch (stringId) {
                            case R.string.user_sex:
                                sex = sexs.get(selectIndex).getCenterText();
                                tvSex.setText(sex);
                                userInfo.setSex(sex.endsWith("male")?0:1);
                                sexs.get(sexIndex).setSelected(false);
                                sexIndex = selectIndex;
                                sexs.get(sexIndex).setSelected(true);
                                break;
                        }
                        break;
                }
            }
        });
        selectDialog.show(this.getSupportFragmentManager());

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if(resultCode == 0){
            return;
        }
        if(data == null){
            return;
        }
        //接收选择地点的结果地址
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            ArrayList<String> selectPaths = (ArrayList<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT);
            // do something u want
            Glide.with(this).load(selectPaths.get(0)).into(imgAvatar);
            userInfo.setAvatorPath(selectPaths.get(0));
            return;
        }
        if ( resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }
            switch (requestCode){
                case REQUEST_PLACE_PICKER_COMPANY:
                    userInfo.setCompanyLat(place.getLatLng().latitude);
                    userInfo.setCompanyLng(place.getLatLng().longitude);
                    userInfo.setCompanyAddress(name.toString());
                    tvCompany.setText(name);
                    break;
                case REQUEST_PLACE_PICKER_HOME:
                    userInfo.setHomeLat( place.getLatLng().latitude);
                    userInfo.setHomeLng( place.getLatLng().longitude);
                    userInfo.setHomeAddress(name.toString());
                    tvHome.setText(name);
                    break;
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
