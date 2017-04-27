package net.skyestudios.mtgcardquery.data;

import android.content.Context;

import net.skyestudios.mtgcardquery.assets.AssetProcessor;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by arkeonet64 on 4/6/2017.
 */

public final class Settings implements Serializable {
    private Context applicationContext;
    private GregorianCalendar lastUpdateDate;
    private String databaseVersion;
    private Boolean databaseOpened = false;
    private MTGCardDataSource mtgCardDataSource;
    private AssetProcessor assetProcessor;

    public Settings(Context context) {
        applicationContext = context;
        mtgCardDataSource = new MTGCardDataSource(applicationContext);
        assetProcessor = new AssetProcessor(applicationContext, mtgCardDataSource);
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

    public void openDb(Context context) {
        mtgCardDataSource = new MTGCardDataSource(context);
        mtgCardDataSource.openDb();
        //Check version table
        assetProcessor.setForcedUpdate();
    }
}
