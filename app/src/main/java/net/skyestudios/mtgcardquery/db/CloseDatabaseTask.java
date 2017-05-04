package net.skyestudios.mtgcardquery.db;

import android.os.AsyncTask;

/**
 * Created by arkeonet64 on 4/30/2017.
 */

public class CloseDatabaseTask extends AsyncTask<Void, Void, Void> {
    private MTGCardDataSource mtgCardDataSource;

    public CloseDatabaseTask(MTGCardDataSource mtgCardDataSource) {
        this.mtgCardDataSource = mtgCardDataSource;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mtgCardDataSource.closeDb();
        return null;
    }
}
