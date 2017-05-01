package net.skyestudios.mtgcardquery.db;

import android.os.AsyncTask;

/**
 * Created by arkeonet64 on 4/30/2017.
 */

public class OpenDatabaseTask extends AsyncTask<Void, Void, Void> {
    private MTGCardDataSource mtgCardDataSource;

    public OpenDatabaseTask(MTGCardDataSource mtgCardDataSource) {
        this.mtgCardDataSource = mtgCardDataSource;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mtgCardDataSource.openDb();
        return null;
    }
}
