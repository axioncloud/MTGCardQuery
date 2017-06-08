package net.skyestudios.mtgcardquery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.skyestudios.mtgcardquery.adapters.CardViewAdapter;
import net.skyestudios.mtgcardquery.data.Card;
import net.skyestudios.mtgcardquery.data.Wishlist;
import net.skyestudios.mtgcardquery.db.MTGCardDataSource;
import net.skyestudios.mtgcardquery.db.OpenDatabaseTask;
import net.skyestudios.mtgcardquery.views.CardView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
    private File wishlistFile;
    private List<Wishlist> wishlists;
    private ArrayList<String> wishlistNames;

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
        registerForContextMenu(resultsList);

        resultsList.setFastScrollEnabled(true);

        wishlistNames = new ArrayList<>();

        wishlistFile = new File(getFilesDir(), "wishlists.bin");
        wishlists = loadWishlists();
        if (wishlists != null) {
            for (Wishlist wishlist :
                    wishlists) {
                wishlistNames.add(wishlist.getName());
            }
        } else {
            try {
                wishlistFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveWishlists(List<Wishlist> wishlists) {
        try {
            FileOutputStream FOS = new FileOutputStream(wishlistFile);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(wishlists);
            OOS.close();
            FOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Wishlist> loadWishlists() {
        try {
            FileInputStream FIS = new FileInputStream(wishlistFile);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            ArrayList<Wishlist> wishlists = (ArrayList<Wishlist>) OIS.readObject();
            OIS.close();
            FIS.close();
            return wishlists;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_results_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.view_card:
                cardViewIntent.putExtra(CardViewActivity.VIEW_TYPE, CardViewActivity.VIEW_CARD);
                cardViewIntent.putExtra(CardViewActivity.CARD, cards.get(info.position));
                startActivityForResult(cardViewIntent, 0);
                break;
            case R.id.add_to_wishlist:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Wishlists")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setItems(wishlistNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (wishlists.get(i).contains(cards.get(info.position))) {
                                    new AlertDialog.Builder(ResultsActivity.this)
                                            .setTitle("Error")
                                            .setMessage("Card is already in selected wishlist")
                                            .setNegativeButton("Dismiss", null)
                                            .show();
                                } else {
                                    wishlists.get(i).addCard(cards.get(info.position));
                                    saveWishlists(wishlists);
                                }

                                dialogInterface.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create();

                if (wishlistNames.size() == 0) {
                    alertDialog.setMessage("Uh, oh! There are no wishlists! Please go to the wishlist submenu in the navigation panel.");
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                alertDialog.show();
                break;
        }
        return true;
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
