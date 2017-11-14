package com.zk.walkwayapp.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.zk.walkwayapp.R;
import com.zk.walkwayapp.utils.Util;

public class ChooseAddressActivity extends Activity {
    private EditText keywrods;
    private  WebView webview;
    private String textKeyWords;
    private Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationcomponent);
        keywrods = (EditText) findViewById(R.id.location_keywords);
        webview = (WebView) findViewById(R.id.location_WebView);
        webview.setBackgroundColor(Color.argb(00, 256, 256, 256));
        keywrods.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LocationComponentListener();
            }
        });
    }
    /**
     * 实现与activity通信的方法，并加载目标地图信息
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void LocationComponentListener()
    {
        try {

            Log.e("LocationComponent", "开始搜索");
            textKeyWords = keywrods.getText().toString();
            textKeyWords = "银行，学校，公园";
            textKeyWords = Util.getkeywords(textKeyWords);
            Log.e("LocationComponent", "关键字"+ textKeyWords);
            webview.getSettings().setJavaScriptEnabled(true);
            String url = "http://m.amap.com/picker/?keywords="+
                    textKeyWords+"&key=035a7298cddc6488961e9cba167c71c3";
            webview.loadUrl(url);
            webview.setWebViewClient(new WebViewClient());
        } catch (Exception e) {
            Toast.makeText(this, "检查输入信息，错误提示："+e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }




}
