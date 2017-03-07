package net.skyestudios.mtgcardquery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QueryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        //<editor-fold desc="WebView Setup">
        /*final WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new HtmlView(), "HtmlView");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:window.HtmlView" +
                                ".parseHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    }
                }, 500);
            }
        });
        ((WebView) findViewById(R.id.webView)).loadUrl("http://magiccards.info/query?q=" + "Akroma's+Memorial");*/
        //</editor-fold>

        CardAssetProcesser cardAssetProcesser = new CardAssetProcesser(this);
        cardAssetProcesser.execute();
    }

    private class HtmlView {
        @JavascriptInterface
        public void parseHTML(final String html) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new CardImageDownloader(html, QueryActivity.this).execute();
                }
            });
        }
    }
}
