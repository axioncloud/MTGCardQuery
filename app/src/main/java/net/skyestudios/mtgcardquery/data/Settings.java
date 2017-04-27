package net.skyestudios.mtgcardquery.data;

import android.content.Context;

import net.skyestudios.mtgcardquery.assets.AssetProcessor;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by arkeonet64 on 4/6/2017.
 */

public final class Settings implements Serializable {
    private transient Context applicationContext;
    private GregorianCalendar lastUpdateDate;
    private String databaseVersion;
    private Boolean databaseOpened;
    private transient MTGCardDataSource mtgCardDataSource;
    private transient AssetProcessor assetProcessor;
    public Settings(Context context) {
        applicationContext = context;
        databaseOpened = false;
        lastUpdateDate = new GregorianCalendar(TimeZone.getDefault());
        mtgCardDataSource = new MTGCardDataSource(applicationContext);
        assetProcessor = new AssetProcessor(applicationContext, mtgCardDataSource);
    }

    public static Settings load(File settingsFile) throws IOException {
        Settings settings = null;
        FileInputStream FIS = new FileInputStream(settingsFile);
        ObjectInputStream OIS = new ObjectInputStream(FIS);
        try {
            settings = (Settings) OIS.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return settings;
    }

    public void recreateAssetProcessor() {
        assetProcessor = new AssetProcessor(applicationContext, mtgCardDataSource);
    }

    public AssetProcessor getAssetProcessor() {
        return assetProcessor;
    }

    public MTGCardDataSource getMtgCardDataSource() {
        return mtgCardDataSource;
    }

    public Boolean isDatabaseOpened() {
        return databaseOpened;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public GregorianCalendar getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void openDb(Context context) {
        mtgCardDataSource = new MTGCardDataSource(context);
        mtgCardDataSource.openDb();
        //Check version table
        assetProcessor.setForcedUpdate();
    }

    public void save(File settingsFile) throws IOException {
        FileOutputStream FOS = new FileOutputStream(settingsFile);
        ObjectOutputStream OOS = new ObjectOutputStream(FOS);
        OOS.writeObject(this);
    }
}
