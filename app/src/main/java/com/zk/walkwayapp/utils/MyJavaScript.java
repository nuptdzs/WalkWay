package com.zk.walkwayapp.utils;


import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * 用来处理与JavaScript交互的类
 * @author Administrator
 *
 */
public class MyJavaScript {  
    private WebView webview;  
    //使用一个handler来处理加载事件  
    private Handler handler;  
    
    public static String PlaceName = "";
    public MyJavaScript(WebView webView,Handler handler){
        this.handler = handler;  
        webview = webView;
        
    }  
    /* 
     * java调用显示网页，异步 
     * 传递目标点 PlaceName
     */  
    @JavascriptInterface
    public void show(){  
      handler.post(new Runnable() {           
        public void run() {  
        //重要：url的生成,传递数据给网页  
        	
            String url = "javascript:mapInit('" + PlaceName + "')";  
           webview.loadUrl(url);
           
           Log.e("MyJavaScript", "javascript:mapInit("+PlaceName+")");
        }  
       });  
    }  
    
    /**
     * JS调用Android，传回坐标点
     * @param point
     */
    @JavascriptInterface
    public void call(final String point){
    	handler.post(new Runnable() {           
            public void run() {  
            	Log.e("MyJavaScript", point);
//            	StartSearch(point);
            }  
           });  
    	
    	
    }
    
}  
