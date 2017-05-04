package net.skyestudios.mtgcardquery.assets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arkeonet64 on 3/6/2017.
 */
public class AssetDownloader extends AsyncTask<String, Void, Bitmap> {
    private final ImageView imageView;

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     * @param cardImageView
     */
    public AssetDownloader(ImageView cardImageView) {
        super();
        imageView = cardImageView;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            String cardString = params[0].replace(' ', '+');
            String url = "http://magiccards.info/query?q=" + cardString;

            URL pageurl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) pageurl.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //Parse Document page (response)
            Document document = Jsoup.parse(response.toString());
            String imageLocation = document.getElementsByAttributeValueMatching("src", "http://magiccards.info/scans").get(0).attr("src");

            URL imageurl = new URL(imageLocation);
            HttpURLConnection imageConnection = (HttpURLConnection) imageurl.openConnection();
            InputStream imageStream = imageConnection.getInputStream();

            return BitmapFactory.decodeStream(imageStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param bitmap The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
    }
}
