package net.skyestudios.mtgcardquery.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import net.skyestudios.mtgcardquery.data.Deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by arkeonet64 on 5/12/2017.
 */

public class DeckViewAdapter extends ArrayAdapter<Deck> {

    List<Deck> decks;

    public DeckViewAdapter(Context context, int resource) {
        super(context, resource);
        decks = new ArrayList<>();
    }

    @Override
    public void add(Deck object) {
        super.add(object);
        decks.add(object);
    }

    @Override
    public void addAll(Collection<? extends Deck> collection) {
        super.addAll(collection);
        decks.addAll(collection);
    }
}
