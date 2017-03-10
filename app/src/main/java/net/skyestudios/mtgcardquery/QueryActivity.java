package net.skyestudios.mtgcardquery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class QueryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        CardImageDownloader CID = new CardImageDownloader("", this);
        CID.executeOnExecutor(CardImageDownloader.THREAD_POOL_EXECUTOR);

        CardAssetProcesser cardAssetProcesser = new CardAssetProcesser(this);
        cardAssetProcesser.setForcedUpdate();
        cardAssetProcesser.executeOnExecutor(CardAssetProcesser.THREAD_POOL_EXECUTOR);
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
