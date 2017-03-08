package net.skyestudios.mtgcardquery;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by arkeonet64 on 3/6/2017.
 */

class CardAssetProcesser extends AsyncTask<Void, Void, Void> {
    private String fragmentPrefix;
    private Activity activity;
    private int fileFragments;

    private long elapsedMinutes;
    private long secondsDisplay;
    private long elapsedMillis;
    private File allCardsFile;
    private Boolean isForcedUpdate;
    private long allCardsFileLength;

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     */
    public CardAssetProcesser(Activity activity) {
        super();
        this.activity = activity;
        this.fragmentPrefix = "JSONfragment_";
        this.fileFragments = 12;
        this.allCardsFileLength = 0;
    }

    public void setForcedUpdate(Boolean forcedUpdate) {
        isForcedUpdate = forcedUpdate;
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
        //check current MTGJSON.com version
        //compare local version
        //if same then cancel
        //else continue
        try {
            URL url = new URL("https://mtgjson.com/json/version.json");
            URLConnection connection = url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            LineNumberReader LNR = new LineNumberReader(
                    new InputStreamReader(connection.getInputStream()));
            File versionFile = new File(activity.getFilesDir(), "MTGJSON.version");
            String versionID = LNR.readLine().replace("\"", "");
            if (versionFile.exists() &&
                    !isForcedUpdate) {
                LineNumberReader LNR2 = new LineNumberReader(
                        new InputStreamReader(
                                new FileInputStream(versionFile)));
                String currentVersionID = LNR2.readLine();
                if (versionID.equals(currentVersionID)) {
                    LNR.close();
                    LNR2.close();
                    cancel(true);
                } else {
                    LNR.close();
                    LNR2.close();
                    FileWriter FW = new FileWriter(versionFile);
                    FW.write(versionID);
                    FW.close();
                }
            } else {
                versionFile.createNewFile();
                allCardsFile = new File(activity.getFilesDir(), "AllCards.json");
                if (allCardsFile.exists()) {
                    URL allCardsURL = new URL("https://mtgjson.com/json/AllCards-x.json");
                    URLConnection allCardsConnection = allCardsURL
                            .openConnection();
                    allCardsConnection.setDoInput(true);
                    allCardsConnection.connect();
                    FileOutputStream FOS = new FileOutputStream(allCardsFile);
                    InputStream IS = allCardsConnection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = IS.read(buffer)) != -1) {
                        FOS.write(buffer, 0, read);
                        allCardsFileLength += read;
                    }
                    IS.close();
                    FOS.flush();
                    FOS.close();
                } else {
                    allCardsFile.createNewFile();
                    URL allCardsURL = new URL("https://mtgjson.com/json/AllCards-x.json");
                    URLConnection allCardsConnection = allCardsURL
                            .openConnection();
                    allCardsConnection.setDoInput(true);
                    allCardsConnection.connect();
                    FileOutputStream FOS = new FileOutputStream(allCardsFile);
                    InputStream IS = allCardsConnection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = IS.read(buffer)) != -1) {
                        FOS.write(buffer, 0, read);
                        allCardsFileLength += read;
                    }
                    IS.close();
                    FOS.flush();
                    FOS.close();
                }
                FileWriter FW = new FileWriter(versionFile);
                FW.write(versionID);
                FW.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isCancelled()) {
            fragmentJSON();

            for (int i = 0; i < fileFragments; i++) {
                processFragment(i);
            }

            long deltaTime = System.currentTimeMillis() - initialTime;

            elapsedMillis = deltaTime % 1000;
            long elapsedSeconds = deltaTime / 1000;
            secondsDisplay = elapsedSeconds % 60;
            elapsedMinutes = elapsedSeconds / 60;
        }
        return null;
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
        CardAssetProcessorNotification.notify(activity, String.format("%s \\\\%dm:%ds:%dms//", "Complete", elapsedMinutes, secondsDisplay, elapsedMillis), 0);
    }

    private void fragmentJSON() {
        try {
            FileInputStream FIS = new FileInputStream(allCardsFile);
            FileOutputStream FOS = new FileOutputStream(allCardsFile);


            StringBuilder SB = new StringBuilder();

            //TODO Convert readLine style to buffered array
            ////Read byte array and count number of opens and closes on brackets
            ////Fragment using (fileLength * fragmentIndex + 1) / fileFragments
            ////Using same range style (line is between minLine and maxLine), find spot to fragment file,
            /////Using that offset start there and repeat until finished

            /*int numLines = lines.size();

            int lineFragmentSize = numLines / fileFragments;
            int lineBufferSize = 100;

            int fileLineNumber = 0;


            for (int fragmentIndex = 0; fragmentIndex < fileFragments; fragmentIndex++) {
                int numOpens = 0;
                line = "null";

                File fragment = new File(activity.getFilesDir(),
                        fragmentPrefix + fragmentIndex + ".json");
                FileWriter FW = new FileWriter(fragment);

                int minLine = (lineFragmentSize * (fragmentIndex + 1)) - lineBufferSize;
                int maxLine = (lineFragmentSize * (fragmentIndex + 1)) + lineBufferSize;
                if (fragmentIndex >= 1) {
                    SB.append("{\n");
                    numOpens++;
                }

                for (int fragmentLineNumber = fileLineNumber;
                     fragmentLineNumber < numLines &&
                             line != null &&
                             lines.get(fragmentLineNumber) != null;
                     fragmentLineNumber++,
                             fileLineNumber++) {

                    line = lines.get(fragmentLineNumber);

                    if (line.contains("{")) {
                        numOpens++;
                    }
                    if (line.contains("}")) {
                        numOpens--;
                    }

                    if (numOpens == 1 &&
                            ((fragmentLineNumber >= minLine &&
                                    fragmentLineNumber <= maxLine &&
                                    fragmentIndex != fileFragments))) {
                        SB.append(line.replace(",", "")).append("\n");
                        line = null;
                    } else {
                        SB.append(line).append("\n");
                    }
                }
                SB.append("}");
                FW.write(SB.toString());
                FW.close();
                SB.delete(0, SB.length());
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                while (jreader.hasNext()) {
                    String tagName = jreader.nextName();
                    switch (tagName) {
                        //<editor-fold desc="JSON Property Statements">
                        case "layout":
                            jreader.nextString();
                            break;
                        case "name":
                            jreader.nextString();
                            break;
                        case "manaCost":
                            jreader.nextString();
                            break;
                        case "cmc":
                            jreader.nextDouble();
                            break;
                        case "colors":
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        case "type":
                            jreader.nextString();
                            break;
                        case "types":
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        case "subtypes":
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        case "text":
                            jreader.nextString();
                            break;
                        case "power":
                            jreader.nextString();
                            break;
                        case "toughness":
                            jreader.nextString();
                            break;
                        case "imageName":
                            jreader.nextString();
                            break;
                        case "printings":
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        case "legalities":
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
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        case "rulings":
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
                            jreader.nextString();
                            break;
                        case "supertypes":
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        case "starter":
                            jreader.nextBoolean();
                            break;
                        case "loyalty":
                            jreader.nextInt();
                            break;
                        case "hand":
                            jreader.nextInt();
                            break;
                        case "life":
                            jreader.nextInt();
                            break;
                        case "names":
                            jreader.beginArray();
                            while (jreader.hasNext()) {
                                jreader.nextString();
                            }
                            jreader.endArray();
                            break;
                        //</editor-fold>
                        default:
                            Log.i(TAG, "doInBackground: Tag unrecognized:" +
                                    "\n\t\ttagName = " + tagName);
                            break;
                    }
                }
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
        }
    }

}
