package net.skyestudios.mtgcardquery;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.skyestudios.mtgcardquery.data.Card;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;
import net.skyestudios.mtgcardquery.db.OpenDatabaseTask;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    MTGCardDataSource mtgCardDataSource;
    private String queryString;
    private ProgressDialog waitingDialog;
    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        queryString = getIntent().getStringExtra("queryString");
        waitingDialog = new ProgressDialog(this);
        waitingDialog.setIndeterminate(false);
        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDialog.setCancelable(false);
        waitingDialog.setMessage("Searching database for cards");
        waitingDialog.show();
        mtgCardDataSource = new MTGCardDataSource(this);
        new OpenDatabaseTask(mtgCardDataSource).execute();
        new QueryDatabase().execute();
        //Open DB
        //query
        //get results
        //display results and close DB
    }


    private class QueryDatabase extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
//            cards = mtgCardDataSource.query(queryString);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //create list of CardViews and insert them into ResultsActivity's List
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            waitingDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}
