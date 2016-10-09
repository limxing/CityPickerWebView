package me.leefeng.citypicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Created by limxing on 16/10/8.
 */

public class CityPicker implements JavaScriptListener {

    private final Animation inAnim;
    private final Animation outAnim;
    private final Activity context;
    private final Animation bgAnim;
    private final Animation bgAnimOut;
    private ViewGroup rootView;
    private WebView pickerview;
    private ViewGroup decorView;
    private boolean isShow;
    private CityPickerListener listener;

    public CityPicker(Activity context,CityPickerListener listener) {
        this.context=context;
        this.listener=listener;
        inAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        outAnim = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
        bgAnim = AnimationUtils.loadAnimation(context, R.anim.alertview_bgin);
        bgAnimOut = AnimationUtils.loadAnimation(context, R.anim.alertview_bgout);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) context.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.citypickerview, decorView, false);
        pickerview = (WebView) rootView.findViewById(R.id.pickerview);
        //设置编码
        pickerview.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        pickerview.getSettings().setJavaScriptEnabled(true);
        pickerview.addJavascriptInterface(new JavaScriptObject(this), "myObj");
        pickerview.loadUrl("file:///android_asset/page/leefeng.html");
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cancle();
            }
        });
    }

    public void show() {
        if (isShow) {
            return;
        }
        isShow = true;
        decorView.addView(rootView);
        pickerview.startAnimation(inAnim);
        rootView.startAnimation(bgAnim);

    }

    public void close() {
        pickerview.startAnimation(outAnim);
        rootView.startAnimation(bgAnimOut);
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                isShow = false;
                decorView.removeView(rootView);
            }
        }, 300);
    }

    @Override
    public void cancle() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }

    @Override
    public void city(final String name) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.getCity(name);
                close();
            }
        });

    }

    public boolean isShow() {
        return isShow;
    }
}
