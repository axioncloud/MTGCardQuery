package net.skyestudios.mtgcardquery;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by arkeonet64 on 3/6/2017.
 */
public class CardImageDownloader extends AsyncTask<Void, Void, Void> {
    private String html;
    private BitmapDrawable drawable;
    private Activity activity;

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     */
    public CardImageDownloader(String html, Activity activity) {
        super();
        this.html = html;
        this.activity = activity;
    }

    public Drawable getDrawable() {
        return drawable;
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
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(Jsoup.parse(html)
                    .getElementsByTag("img")
                    .get(1)
                    .attr("src"));
            URLConnection connection = url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            drawable = (BitmapDrawable) Drawable.createFromStream(connection.getInputStream(), "Card Image");
            return null;
        } catch (IOException e) {
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
     * @param aVoid The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        ((ImageView) activity.findViewById(R.id.cardImage_ImageView)).setImageDrawable(drawable);

        final File DCIM_Directory = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                activity.getString(R.string.app_name));
        if (!DCIM_Directory.exists()) {
            DCIM_Directory.mkdirs();
        }

        //Save image to MTG Card Query's DCIM directory
        activity.findViewById(R.id.cardImage_ImageView).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    File savedImage = new File(DCIM_Directory, "Akroma's Memorial.png");
                    savedImage.createNewFile();
                    FileOutputStream FOS = new FileOutputStream(savedImage);
                    Bitmap bitmap = drawable.getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, FOS);
                    FOS.flush();
                    FOS.close();
                    Toast.makeText(activity, "Image: {" + savedImage.getName() + "} saved", Toast.LENGTH_LONG).show();

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, savedImage.getName());
                    values.put(MediaStore.Images.Media.DESCRIPTION, "A MTG Card");
                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                    values.put(MediaStore.Images.ImageColumns.BUCKET_ID, savedImage.getName().toLowerCase().hashCode());
                    values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, savedImage.getName().toLowerCase());
                    values.put(MediaStore.Images.ImageColumns.TITLE, savedImage.getName());
                    values.put("_data", savedImage.getAbsolutePath());

                    ContentResolver contentResolver = activity.getContentResolver();
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        CardAssetProcessorNotification.notify(activity, "Complete", 0);
    }
}
