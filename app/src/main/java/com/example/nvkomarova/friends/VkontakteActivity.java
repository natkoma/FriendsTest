package com.example.nvkomarova.friends;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.nvkomarova.friends.utils.PreferencesManager;

import java.net.URLEncoder;

public class VkontakteActivity extends Activity {

    private WebView webview;
    private ProgressBar progress;

    private String vk_redirect_url = "https://oauth.vk.com/blank.html";
    private String vk_api_id = "6293999";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        //Получаем элементы
        webview = (WebView) findViewById(R.id.web);
        progress = (ProgressBar) findViewById(R.id.progress_bar);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.clearCache(true);

        //Чтобы получать уведомления об окончании загрузки страницы
        webview.setWebViewClient(new VkWebViewClient());

        String url = "https://oauth.vk.com/authorize?client_id=" + vk_api_id + "&display=page&redirect_uri="+ URLEncoder.encode(vk_redirect_url) + "&scope=friends,photos&response_type=token&v=5.52";
        webview.loadUrl(url);
        webview.setVisibility(View.VISIBLE);
    }

    class VkWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);
            parseUrl(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }
    }

    private void parseUrl(String url) {
        try {
            if( url == null ) {
                return;
            }
            if( url.startsWith(vk_redirect_url) ) {
                if( !url.contains("error") ) {
                    String[] auth = VKUtil.parseRedirectUrl(url);
                    webview.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);

                    //Запоминаем данные
                    PreferencesManager.setVKToken(auth[0]);
                    PreferencesManager.setVkUid(auth[1]);

                    String id = PreferencesManager.getVKUid();

                    //Возвращаем данные
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            } else if( url.contains("error?err") ) {
                setResult(RESULT_CANCELED);
                finish();
            }
        } catch( Exception e ) {
            e.printStackTrace();
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
