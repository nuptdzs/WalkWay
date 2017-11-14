package com.zk.walkwayapp.view.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zk.library.common.common.CommmonSelectData;
import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.ui.dialog.AlertDialog;
import com.zk.library.common.ui.dialog.SelectDialog;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.contract.PersonInfoContract;
import com.zk.walkwayapp.model.bean.PersonInfo;
import com.zk.walkwayapp.presenter.user.PersonInfoPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置个人身体资料
 */
@ContentView(R.layout.activity_person_info)
public class PersonInfoActivity extends BaseActivity<PersonInfoContract.IPersonInfoPresenter>
        implements PersonInfoContract.IPersonInfoView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_height)
    TextView tvHeight;
    @Bind(R.id.ll_height)
    LinearLayout llHeight;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.ll_weight)
    LinearLayout llWeight;
    @Bind(R.id.btn_save)
    Button btnSave;
    private int heightIndex;
    private int weightIndex;

    @Override
    protected PersonInfoContract.IPersonInfoPresenter getPresenter() {
        return new PersonInfoPresenter(this);
    }

    @Override
    protected void addEvent() {

        toolbar.setTitle("EditPersonInfo");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<CommmonSelectData>  heights, weights;
    private String  height = "", weight = "";
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
                            case R.string.user_height:
                                height = heights.get(selectIndex).getCenterText();
                                tvHeight.setText(height);
                                personInfo.setHeight(Integer.parseInt(height.replace("cm", "")));
                                heights.get(heightIndex).setSelected(false);
                                heightIndex = selectIndex;
                                heights.get(heightIndex).setSelected(true);
                                break;
                            case R.string.user_weight:
                                weight = weights.get(selectIndex).getCenterText();
                                tvWeight.setText(weight);
                                personInfo.setWeight(Integer.parseInt(weight.replace("kg", "")));
                                weights.get(weightIndex).setSelected(false);
                                weightIndex = selectIndex;
                                weights.get(weightIndex).setSelected(true);
                                break;
                        }
                        break;
                }
            }
        });
        selectDialog.show(PersonInfoActivity.this.getSupportFragmentManager());

    }

    private PersonInfo personInfo;
    @Override
    protected void loadData() {
        presenter.load();
    }

    @Override
    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
        height = personInfo.getHeight()+"cm";
        weight = personInfo.getWeight()+"kg";
        tvHeight.setText(height);
        tvWeight.setText(weight);
        heights = new ArrayList<>();
        for (int i = 100; i <= 210; i++) {
            CommmonSelectData commmonSelectData = new CommmonSelectData();
            commmonSelectData.setCenterText(i + "cm");
            heights.add(commmonSelectData);
        }
        for (int i = 0; i < heights.size(); i++) {
            if (heights.get(i).getCenterText().equals(height)) {
                heightIndex = i;
                heights.get(i).setSelected(true);
            }
        }
        weights = new ArrayList<>();
        for (int i = 30; i <= 110; i++) {
            CommmonSelectData commmonSelectData = new CommmonSelectData();
            commmonSelectData.setCenterText(i + "kg");
            weights.add(commmonSelectData);
        }
        for (int i = 0; i < weights.size(); i++) {
            if (weights.get(i).getCenterText().equals(weight)) {
                weightIndex = i;
                weights.get(i).setSelected(true);
            }
        }
    }

    @Override
    public PersonInfo getPersonInfo() {
        return personInfo;
    }


    @OnClick({R.id.ll_height, R.id.ll_weight, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_height:
                setBaseDataDialog(heights,R.string.user_height,heightIndex);
                break;
            case R.id.ll_weight:
                setBaseDataDialog(weights,R.string.user_weight,weightIndex);
                break;
            case R.id.btn_save:
                presenter.save();
                break;
        }
    }
}
