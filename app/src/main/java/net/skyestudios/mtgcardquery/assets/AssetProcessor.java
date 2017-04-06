package net.skyestudios.mtgcardquery.assets;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import net.skyestudios.mtgcardquery.data.Card;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by arkeonet64 on 3/6/2017.
 */

public class AssetProcessor extends AsyncTask<Void, String, Void> {
    private final MTGCardDataSource mtgCardDataSource;
    private String fragmentPrefix;
    private Activity activity;
    private int fileFragments;
    private long elapsedMinutes;
    private long secondsDisplay;
    private long elapsedMillis;
    private File allCardsFile;
    private Boolean isForcedUpdate;
    private long allCardsFileLength;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     *
     * @param activity current Activity running
     */
    public AssetProcessor(Activity activity, MTGCardDataSource cardDataSource) {
        super();
        this.activity = activity;
        this.fragmentPrefix = "JSONfragment_";
        this.fileFragments = 12;
        this.allCardsFileLength = 0;
        this.connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        this.isForcedUpdate = false;
        this.mtgCardDataSource = cardDataSource;
    }

    /**
     * Sets the flag to forcefully update
     */
    public void setForcedUpdate() {
        isForcedUpdate = true;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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
        long initialTime = System.currentTimeMillis();

        if (!isCancelled() && activeNetworkInfo.isConnected()) {
            updateCards();
        }

        if (!isCancelled()) {
            fragmentJSON();
            for (int i = 0; i < fileFragments; i++) {
                processFragment(i);
            }
        }

        long deltaTime = System.currentTimeMillis() - initialTime;
        elapsedMillis = deltaTime % 1000;
        long elapsedSeconds = deltaTime / 1000;
        secondsDisplay = elapsedSeconds % 60;
        elapsedMinutes = elapsedSeconds / 60;
        return null;
    }

    private void updateCards() {
        try {
            URL url = new URL("https://mtgjson.com/json/version.json");
            URLConnection connection = url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            BufferedInputStream BIS = new BufferedInputStream(connection.getInputStream());
            File versionFile = new File(activity.getFilesDir(), "MTGJSON.version");
            byte[] versionBytes = new byte[512];
            int readBytes = BIS.read(versionBytes);
            String versionID = new String(versionBytes, 0, readBytes, "UTF-8").replace("\"", "");
            if (versionFile.exists() &&
                    !isForcedUpdate) {
                BufferedInputStream BIS2 = new BufferedInputStream(new FileInputStream(versionFile));
                readBytes = BIS2.read(versionBytes);
                String currentVersionID = new String(versionBytes, 0, readBytes, "UTF-8").replace("\"", "");
                if (versionID.equals(currentVersionID)) {
                    BIS.close();
                    BIS2.close();
                    cancel(false);
                } else {
                    BIS.close();
                    BIS2.close();
                    BufferedOutputStream BOS = new BufferedOutputStream(new FileOutputStream(versionFile));
                    BOS.write(versionID.getBytes("UTF-8"));
                    BOS.close();
                }
            } else {
                versionFile.createNewFile();
                allCardsFile = new File(activity.getFilesDir(), "AllCards.json");
                if (allCardsFile.exists()) {
                    URL allCardsURL = new URL("https://mtgjson.com/json/AllCards-x.json");
                    URLConnection allCardsConnection = allCardsURL
                            .openConnection();
                    allCardsConnection.connect();
                    BufferedOutputStream BOS = new BufferedOutputStream(new FileOutputStream(allCardsFile));
                    InputStream IS = allCardsConnection.getInputStream();
                    byte[] buffer = new byte[2048];
                    int bufferSize;
                    while ((bufferSize = IS.read(buffer)) != -1) {
                        BOS.write(buffer, 0, bufferSize);
                        allCardsFileLength += bufferSize;
                    }
                    IS.close();
                    BOS.close();
                } else {
                    allCardsFile.createNewFile();
                    URL allCardsURL = new URL("https://mtgjson.com/json/AllCards-x.json");
                    URLConnection allCardsConnection = allCardsURL
                            .openConnection();
                    allCardsConnection.connect();
                    BufferedOutputStream BOS = new BufferedOutputStream(new FileOutputStream(allCardsFile));
                    InputStream IS = allCardsConnection.getInputStream();
                    byte[] buffer = new byte[2048];
                    int bufferSize;
                    while ((bufferSize = IS.read(buffer)) != -1) {
                        BOS.write(buffer, 0, bufferSize);
                        allCardsFileLength += bufferSize;
                    }
                    IS.close();
                    BOS.close();
                    BOS = new BufferedOutputStream(new FileOutputStream(versionFile));
                    BOS.write(versionID.getBytes("UTF-8"));
                    BOS.close();
                }
                isForcedUpdate = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        mtgCardDataSource.closeDb();
        if (!isCancelled()) {
            AssetProcessorNotification.notify(activity, String.format("%s \\\\%dm:%ds:%dms//",
                    "Successful", elapsedMinutes, secondsDisplay, elapsedMillis), 0);
        } else {
            AssetProcessorNotification.notify(activity, String.format("%s \\\\%dm:%ds:%dms//",
                    "Cancelled", elapsedMinutes, secondsDisplay, elapsedMillis), 0);
        }
    }

    private void fragmentJSON() {
        try {
            BufferedInputStream FIS = new BufferedInputStream(new FileInputStream(allCardsFile));

            String bufferString;

            byte[] buffer = new byte[4096];

            int bufferFragmentSize = (int) (allCardsFileLength / fileFragments);
            int bufferPaddingSize = buffer.length;

            int fileCharacterNumber = 0;

            String overFlowStrngBuffer = null;
            BufferedOutputStream BOS = null;
            File fragment = null;
            int numOpens = 0;

            for (int fragmentIndex = 0; fragmentIndex < fileFragments; ) {
                int bufferSize = 0;

                if (overFlowStrngBuffer == null) {
                    fragment = new File(activity.getFilesDir(),
                            fragmentPrefix + fragmentIndex + ".json");
                    BOS = new BufferedOutputStream(new FileOutputStream(fragment));
                }

                int minBufferSize = (bufferFragmentSize * (fragmentIndex + 1)) - bufferPaddingSize;
                int maxBufferSize = (bufferFragmentSize * (fragmentIndex + 1)) + bufferPaddingSize;

                while ((bufferSize = FIS.read(buffer)) != -1) {
                    bufferString = new String(buffer, 0, bufferSize, "UTF-8");

                    int opens = StringUtils.countMatches(bufferString, '{');
                    int closes = StringUtils.countMatches(bufferString, '}');

                    if (fileCharacterNumber >= minBufferSize &&
                            fileCharacterNumber <= maxBufferSize) {

                        int closingIndex = searchClosingIndex(bufferString, numOpens);

                        if (fragmentIndex < fileFragments - 1) {
                            String partition0 = bufferString.substring(0, closingIndex + 1) + "}";

                            BOS.write(partition0.getBytes("UTF-8"));
                            BOS.flush();
                            BOS.close();

                            fragment = new File(activity.getFilesDir(),
                                    fragmentPrefix + ++fragmentIndex + ".json");
                            BOS = new BufferedOutputStream(new FileOutputStream(fragment));
                            overFlowStrngBuffer = "{" + bufferString.substring(closingIndex + 2);

                            BOS.write(overFlowStrngBuffer.getBytes("UTF-8"));

                            fileCharacterNumber += bufferSize;

                            numOpens = StringUtils.countMatches(overFlowStrngBuffer, "{") -
                                    StringUtils.countMatches(overFlowStrngBuffer, "}");
                            break;
                        } else {
                            if (fileCharacterNumber + bufferSize == allCardsFileLength) {
                                BOS.write(bufferString.getBytes("UTF-8"));
                                BOS.flush();
                                BOS.close();
                                fragmentIndex++;
                                break;
                            } else {
                                numOpens += opens;
                                numOpens -= closes;
                                BOS.write(buffer, 0, bufferSize);
                            }
                        }
                    } else {
                        numOpens += opens;
                        numOpens -= closes;
                        BOS.write(buffer, 0, bufferSize);
                    }
                    fileCharacterNumber += bufferSize;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int searchClosingIndex(String searchString, int numOpens) {
        int opens = 0, closes = 0;
        for (int characterIndex = 0; characterIndex < searchString.length(); characterIndex++) {
            if (searchString.charAt(characterIndex) == '{') {
                opens++;
            } else if (searchString.charAt(characterIndex) == '}') {
                closes++;
            }
            if (numOpens + opens - closes == 1) {
                return characterIndex;
            }
        }
        return -1;
    }

    private void processFragment(int fragmentIndex) {
        try {
            String TAG = "INFO";
            File fragment = new File(activity.getFilesDir(), fragmentPrefix + fragmentIndex + ".json");
            FileInputStream FIS = new FileInputStream(fragment);
            InputStreamReader ISR = new InputStreamReader(FIS);
            BufferedReader BR = new BufferedReader(ISR);
            JsonReader jreader = new JsonReader(BR);
            jreader.beginObject();                              //Start of File Object
            while (jreader.hasNext()) {
                jreader.nextName();                             //Start of Card Object
                jreader.beginObject();
                Card card = new Card();
                while (jreader.hasNext()) {
                    String tagName = jreader.nextName();
                    switch (tagName) {
                        //<editor-fold desc="JSON Property Statements">
                        case "name":
                            String name = jreader.nextString();
                            card.setName(name);
                            break;
                        case "layout":
                            String layout = jreader.nextString();
                            card.setLayout(layout);
                            break;
                        case "manaCost":
                            String manaCost = jreader.nextString();
                            card.setManaCost(manaCost);
                            break;
                        case "cmc":
                            Double cmc = jreader.nextDouble();
                            card.setCmc(cmc);
                            break;
                        case "colors":
                            ArrayList<String> colors = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String color = jreader.nextString();
                                colors.add(color);
                            }
                            jreader.endArray();
                            card.setColors(colors.toString());
                            break;
                        case "type":
                            String type = jreader.nextString();
                            card.setType(type);
                            break;
                        case "types":
                            ArrayList<String> types = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String t_type = jreader.nextString();
                                types.add(t_type);
                            }
                            jreader.endArray();
                            card.setTypes(types.toString());
                            break;
                        case "subtypes":
                            ArrayList<String> subtypes = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String subtype = jreader.nextString();
                                subtypes.add(subtype);
                            }
                            jreader.endArray();
                            card.setSubtypes(subtypes.toString());
                            break;
                        case "text":
                            String text = jreader.nextString();
                            card.setText(text);
                            break;
                        case "power":
                            String power = jreader.nextString();
                            card.setPower(power);
                            break;
                        case "toughness":
                            String toughness = jreader.nextString();
                            card.setToughness(toughness);
                            break;
                        case "imageName":
                            String imageName = jreader.nextString();
                            card.setImageName(imageName);
                            break;
                        case "printings":
                            ArrayList<String> printings = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String printing = jreader.nextString();
                                printings.add(printing);
                            }
                            jreader.endArray();
                            card.setPrintings(printings.toString());
                            break;
                        case "legalities":
                            //TODO: Retreive legalities from complex array
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.beginObject();
                                while (jreader.hasNext()) {
                                    tagName = jreader.nextName();
                                    jreader.nextString();
                                }
                                jreader.endObject();
                            }
                            jreader.endArray();
                            break;
                        case "colorIdentity":
                            ArrayList<String> colorIdentities = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String colorIdentity = jreader.nextString();
                                colorIdentities.add(colorIdentity);
                            }
                            jreader.endArray();
                            card.setColorIdentity(colorIdentities.toString());
                            break;
                        case "rulings":
                            //TODO: Retreive rulings from complex array
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.beginObject();
                                while (jreader.hasNext()) {
                                    tagName = jreader.nextName();
                                    jreader.nextString();
                                }
                                jreader.endObject();
                            }
                            jreader.endArray();
                            break;
                        case "source":
                            String source = jreader.nextString();
                            card.setSource(source);
                            break;
                        case "supertypes":
                            ArrayList<String> supertypes = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String supertype = jreader.nextString();
                                supertypes.add(supertype);
                            }
                            jreader.endArray();
                            card.setSupertypes(supertypes.toString());
                            break;
                        case "starter":
                            Boolean starter = jreader.nextBoolean();
                            card.setStarter(starter);
                            break;
                        case "loyalty":
                            Integer loyalty = jreader.nextInt();
                            card.setLoyalty(loyalty);
                            break;
                        case "hand":
                            Integer hand = jreader.nextInt();
                            card.setHand(hand);
                            break;
                        case "life":
                            Integer life = jreader.nextInt();
                            card.setLife(life);
                            break;
                        case "names":
                            ArrayList<String> names = new ArrayList<>();
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                String n_name = jreader.nextString();
                                names.add(n_name);
                            }
                            jreader.endArray();
                            card.setNames(names.toString());
                            break;
                        //</editor-fold>
                        default:
                            Log.i(TAG, "doInBackground: Tag unrecognized:" +
                                    "\n\t\ttagName = " + tagName);
                            break;
                    }
                }
                mtgCardDataSource.createCard(card);
                jreader.endObject();                            //End of Card Object
            }
            jreader.endObject();                                //End of File Object
            jreader.close();                                    //Close file
            BR.close();
            ISR.close();
            FIS.close();
            fragment.delete();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DEBUG", "processFragment: Exception encountered @ Fragment: " + fragmentIndex);
        }
    }
}
