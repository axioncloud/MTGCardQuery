package net.skyestudios.mtgcardquery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class WishlistEditorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private Wishlist wishlist;
    private ListView card_listview;
    private CardViewAdapter cardViewAdapter;
    private Intent cardViewIntent;
    private File wishlistFile;
    private List<Card> cards;
    private Toolbar toolbar;
    private ImageView button_close_multichoice;
    private int wishlist_position;
    private ImageView button_delete_cards;
    private List<CardView> selected_cards;
    private ImageView button_select_all_cards;
    private boolean all_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wishlist_editor);

        cardViewIntent = new Intent(this, CardViewActivity.class);

        wishlist_position = getIntent().getIntExtra("wishlist_position", -1);
        wishlistFile = new File(getFilesDir(), "wishlists.bin");

        card_listview = (ListView) findViewById(R.id.list_cards);

        card_listview.setAdapter((cardViewAdapter = new CardViewAdapter(this, R.layout.layout_card_view)));
        card_listview.setOnItemClickListener(this);
        card_listview.setOnItemLongClickListener(this);

        cards = loadWishlists().get(wishlist_position).getCards();

        cardViewAdapter.addAll(CardView.createListFromCards(cards));

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        wishlist = loadWishlists().get(wishlist_position);

        toolbar.setTitle(wishlist.getName());
        setSupportActionBar(toolbar);

        selected_cards = new ArrayList<>();

        all_selected = false;
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
        if (card_listview.getChoiceMode() != ListView.CHOICE_MODE_MULTIPLE) {
            cardViewIntent.putExtra(CardViewActivity.VIEW_TYPE, CardViewActivity.VIEW_CARD);
            cardViewIntent.putExtra(CardViewActivity.CARD, cards.get(i));
            startActivityForResult(cardViewIntent, 0);
        } else {
            if (selected_cards.contains(cardViewAdapter.getItem(i))) {
                view.setBackgroundColor(Color.TRANSPARENT);
                selected_cards.remove(cardViewAdapter.getItem(i));
                if (all_selected) {
                    all_selected = false;
                    button_select_all_cards.setImageResource(R.drawable.ic_select_all_white_48dp);
                }
            } else {
                view.setBackgroundColor(Color.LTGRAY);
                selected_cards.add(cardViewAdapter.getItem(i));
                if (selected_cards.size() == cardViewAdapter.getCount()) {
                    all_selected = true;
                    button_select_all_cards.setImageResource(R.drawable.ic_deselect_all);
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (card_listview.getChoiceMode() != ListView.CHOICE_MODE_MULTIPLE) {
            card_listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            card_listview.setItemsCanFocus(false);
            toolbar.setTitle("");

            button_close_multichoice = new ImageView(this);
            button_close_multichoice.setBackgroundResource(R.drawable.ic_clear_white_48dp);
            button_close_multichoice.setAdjustViewBounds(true);

            Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

            button_close_multichoice.setLayoutParams(params);
            button_close_multichoice.setOnClickListener(this);
            toolbar.addView(button_close_multichoice);

            /*************************************************************************************************/

            button_delete_cards = new ImageView(this);
            button_delete_cards.setBackgroundResource(R.drawable.ic_delete_white_48dp);
            button_delete_cards.setAdjustViewBounds(true);

            params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;

            button_delete_cards.setLayoutParams(params);
            button_delete_cards.setOnClickListener(this);
            toolbar.addView(button_delete_cards);

            /*************************************************************************************************/

            button_select_all_cards = new ImageView(this);
            button_select_all_cards.setBackgroundResource(R.drawable.ic_select_all_white_48dp);
            button_select_all_cards.setAdjustViewBounds(true);

            params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;

            button_select_all_cards.setLayoutParams(params);
            button_select_all_cards.setOnClickListener(this);
            toolbar.addView(button_select_all_cards);

            /*************************************************************************************************/

            selected_cards.add(cardViewAdapter.getItem(i));
            view.setBackgroundColor(Color.LTGRAY);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == button_close_multichoice) {
            for (int i = 0; i < selected_cards.size(); i++) {
                card_listview.getChildAt(cardViewAdapter.getPosition(selected_cards.get(i))).setBackgroundColor(Color.TRANSPARENT);
            }
            selected_cards.clear();
            resetToolbar();
        } else if (view == button_select_all_cards) {
            if (!all_selected) {
                all_selected = true;
                button_select_all_cards.setImageResource(R.drawable.ic_deselect_all);
                if (selected_cards.size() > 0 && selected_cards.size() != cardViewAdapter.getCount()) {
                    for (int i = 1; i < card_listview.getChildCount(); i++) {
                        selected_cards.add(cardViewAdapter.getItem(i));
                        card_listview.getChildAt(cardViewAdapter.getPosition(selected_cards.get(i))).setBackgroundColor(Color.LTGRAY);
                    }
                } else {
                    for (int i = 0; i < card_listview.getChildCount(); i++) {
                        selected_cards.add(cardViewAdapter.getItem(i));
                        card_listview.getChildAt(cardViewAdapter.getPosition(selected_cards.get(i))).setBackgroundColor(Color.LTGRAY);
                    }
                }
            } else {
                all_selected = false;
                button_select_all_cards.setImageResource(R.drawable.ic_select_all_white_48dp);
                for (int i = 0; i < selected_cards.size(); i++) {
                    card_listview.getChildAt(cardViewAdapter.getPosition(selected_cards.get(i))).setBackgroundColor(Color.TRANSPARENT);
                }
                selected_cards.clear();
            }
        } else if (view == button_delete_cards) {
            for (int i = 0; i < selected_cards.size(); i++) {
                cardViewAdapter.remove(selected_cards.get(i));
                cardViewAdapter.notifyDataSetChanged();
            }

            wishlist.getCards().removeAll(Card.createListFromCardViews(selected_cards));
            selected_cards.clear();
            List<Wishlist> wishlists = loadWishlists();
            wishlists.set(wishlist_position, wishlist);
            saveWishlists(wishlists);
            resetToolbar();
        }
    }

    private void resetToolbar() {
        toolbar.removeView(button_close_multichoice);
        toolbar.removeView(button_delete_cards);
        toolbar.removeView(button_select_all_cards);
        card_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        card_listview.setItemsCanFocus(true);
        toolbar.setTitle(loadWishlists().get(wishlist_position).getName());
    }
}
