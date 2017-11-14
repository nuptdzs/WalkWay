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
import com.zk.walkwayapp.contract.IGoalContract;
import com.zk.walkwayapp.presenter.user.GoalPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

@ContentView(R.layout.activity_goal)
public class GoalActivity extends BaseActivity<IGoalContract.IGoalPresenter> implements IGoalContract.IGoalView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_step_goal)
    TextView tvStepGoal;
    @Bind(R.id.ll_step_goal)
    LinearLayout llStepGoal;
    @Bind(R.id.tv_weight_goal)
    TextView tvWeightGoal;
    @Bind(R.id.ll_weight_goal)
    LinearLayout llWeightGoal;
    @Bind(R.id.btn_save)
    Button btnSave;

    private int stepGoal,weightGoal;
    private ArrayList<CommmonSelectData> stepGoals,weightGoals;
    private int stepIndex,weightIndex;

    @Override
    public void setGoal(int stepGoal, int weightGoal) {
        this.stepGoal = stepGoal;
        this.weightGoal = weightGoal;
        tvStepGoal.setText(stepGoal+"");
        tvWeightGoal.setText(weightGoal+"kg");
        initSelectDatas();
    }

    private void initSelectDatas() {
        stepGoals = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            CommmonSelectData commmonSelectData = new CommmonSelectData();
            commmonSelectData.setCenterText(i*1000 + "");
            stepGoals.add(commmonSelectData);
        }
        for (int i = 0; i < stepGoals.size(); i++) {
            if (stepGoals.get(i).getCenterText().equals(stepGoal+"")) {
                stepIndex = i;
                stepGoals.get(i).setSelected(true);
            }
        }
        weightGoals = new ArrayList<>();
        for (int i = 30; i <= 110; i++) {
            CommmonSelectData commmonSelectData = new CommmonSelectData();
            commmonSelectData.setCenterText(i + "kg");
            weightGoals.add(commmonSelectData);
        }
        for (int i = 0; i < weightGoals.size(); i++) {
            if (weightGoals.get(i).getCenterText().equals(weightGoal+"kg")) {
                weightIndex = i;
                weightGoals.get(i).setSelected(true);
            }
        }
    }

    @Override
    protected IGoalContract.IGoalPresenter getPresenter() {
        return new GoalPresenter(this);
    }

    @Override
    protected void addEvent() {
        toolbar.setTitle("EditGoals");
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
        presenter.loadGoal();
    }


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
                            case R.string.step_goal:
                                stepGoal = Integer.parseInt(stepGoals.get(selectIndex).getCenterText());
                                tvStepGoal.setText(stepGoal+"");
                                stepGoals.get(stepIndex).setSelected(false);
                                stepIndex = selectIndex;
                                stepGoals.get(stepIndex).setSelected(true);
                                break;
                            case R.string.user_weight_goal:
                                weightGoal = Integer.parseInt(weightGoals.get(selectIndex).getCenterText().replace("kg",""));
                                tvWeightGoal.setText(weightGoal+"kg");
                                weightGoals.get(weightIndex).setSelected(false);
                                weightIndex = selectIndex;
                                weightGoals.get(weightIndex).setSelected(true);
                                break;
                        }
                        break;
                }
            }
        });
        selectDialog.show(GoalActivity.this.getSupportFragmentManager());

    }
    @OnClick({R.id.ll_step_goal, R.id.ll_weight_goal, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_step_goal:
                setBaseDataDialog(stepGoals,R.string.step_goal,stepIndex);
                break;
            case R.id.ll_weight_goal:
                setBaseDataDialog(weightGoals,R.string.user_weight_goal,weightIndex);
                break;
            case R.id.btn_save:
                presenter.saveGoal(stepGoal,weightGoal);
                break;
        }
    }
}
