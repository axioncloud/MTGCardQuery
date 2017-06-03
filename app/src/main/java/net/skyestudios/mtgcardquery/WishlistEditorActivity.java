package net.skyestudios.mtgcardquery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.skyestudios.mtgcardquery.adapters.CardViewAdapter;
import net.skyestudios.mtgcardquery.data.Card;
import net.skyestudios.mtgcardquery.data.Wishlist;
import net.skyestudios.mtgcardquery.views.CardView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WishlistEditorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView card_listview;
    private CardViewAdapter cardViewAdapter;
    private Intent cardViewIntent;
    private File wishlistFile;
    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wishlist_editor);

        cardViewIntent = new Intent(this, CardViewActivity.class);

        int wishlist_position = getIntent().getIntExtra("wishlist_position", -1);
        wishlistFile = new File(getFilesDir(), "wishlists.bin");


        card_listview = (ListView) findViewById(R.id.list_cards);

        card_listview.setAdapter((cardViewAdapter = new CardViewAdapter(this, R.layout.layout_card_view)));
        card_listview.setOnItemClickListener(this);

        cards = loadWishlists().get(wishlist_position).getCards();

        cardViewAdapter.addAll(CardView.createListFromCards(cards));
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cardViewIntent.putExtra(CardViewActivity.VIEW_TYPE, CardViewActivity.VIEW_CARD);
        cardViewIntent.putExtra(CardViewActivity.CARD, cards.get(i));
        startActivityForResult(cardViewIntent, 0);
    }
}
