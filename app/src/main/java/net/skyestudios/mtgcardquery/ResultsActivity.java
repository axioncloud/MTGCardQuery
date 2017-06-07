package net.skyestudios.mtgcardquery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.skyestudios.mtgcardquery.adapters.CardViewAdapter;
import net.skyestudios.mtgcardquery.data.Card;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;
import net.skyestudios.mtgcardquery.db.OpenDatabaseTask;
import net.skyestudios.mtgcardquery.views.CardView;

import java.util.List;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    CardViewAdapter cardViewAdapter;
    MTGCardDataSource mtgCardDataSource;
    private String queryString;
    private ProgressDialog waitingDialog;
    private List<Card> cards;
    private ListView resultsList;
    private Intent cardViewIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_results);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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
        resultsList = (ListView) findViewById(R.id.results_list);
        resultsList.setAdapter((cardViewAdapter = new CardViewAdapter(this, R.layout.layout_card_view)));
        resultsList.setOnItemClickListener(this);
        cardViewIntent = new Intent(this, CardViewActivity.class);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cardViewIntent.putExtra(CardViewActivity.VIEW_TYPE, CardViewActivity.VIEW_CARD);
        cardViewIntent.putExtra(CardViewActivity.CARD, cards.get(i));
        startActivityForResult(cardViewIntent, 0);
    }


    private class QueryDatabase extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            cards = mtgCardDataSource.query(queryString);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            waitingDialog.dismiss();
            Toast.makeText(ResultsActivity.this, String.format(Locale.US, "%d records found", cards.size()), Toast.LENGTH_SHORT).show();
            cardViewAdapter.addAll(CardView.createListFromCards(cards));
            super.onPostExecute(aVoid);
        }
    }
}
