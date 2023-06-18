package com.gsoft.ima.di.dialog;

import android.content.Context;
import android.webkit.WebView;

import com.gsoft.ima.R;

public class WebViewDialog extends SweetDialog {

    public WebViewDialog(Context context, String title, String data)
    {
        super(context, R.layout.dialog_web, title, null);
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.loadData(data, "", "utf-8");
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }

}
