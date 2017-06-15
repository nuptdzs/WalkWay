package com.zk.walkwayapp.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.presenter.LoginPresenter;
import com.zk.walkwayapp.view.MainActivity;
import com.zk.walkwayapp.view.interfaces.ILoginActivity;

import butterknife.Bind;
import butterknife.OnClick;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginActivity {

    @Bind(R.id.imgLogo)
    ImageView imgLogo;
    @Bind(R.id.etLoginName)
    EditText etLoginName;
    @Bind(R.id.etLoginPwd)
    EditText etLoginPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tvRegist)
    TextView tvRegist;
    @Bind(R.id.tvFindPwd)
    TextView tvFindPwd;

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void addEvent() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public String getUserName() {
        return etLoginName.getText().toString().trim();
    }

    @Override
    public String getUserPwd() {
        return etLoginPwd.getText().toString().trim();
    }

    /**
     * 登录
     */
    @Override
    public void login() {
        presenter.login();
    }

    /**
     * 注册
     */
    @Override
    public void regist() {
        Intent intent = new Intent();
        intent.setClass(this,RegistActivity.class);
        startActivity(intent);
    }

    /**
     * 找回密码
     */
    @Override
    public void findPwd() {
//        Intent intent = new Intent();
//        intent.setClass(this,FindPwdActivity.class);
//        startActivity(intent);
    }
    /**
     * 登录成功
     */
    @Override
    public void loginSuccess(AVUser avUser) {
        showToast("登陆成功");
        nextActivity(MainActivity.class);
        finish();
    }

    /**
     * 登录失败
     */
    @Override
    public void loginFail(String err_msg) {
        showToast(err_msg);
    }




    @OnClick({R.id.tvRegist, R.id.tvFindPwd, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRegist:
                regist();
                break;
            case R.id.tvFindPwd:
                findPwd();
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }
}
