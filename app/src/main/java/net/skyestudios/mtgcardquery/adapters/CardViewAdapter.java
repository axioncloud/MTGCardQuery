package net.skyestudios.mtgcardquery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.views.CardView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by arkeonet64 on 5/1/2017.
 */

public class CardViewAdapter extends ArrayAdapter<CardView> {

    List<CardView> cardViews;

    public CardViewAdapter(Context context, int resource) {
        super(context, resource);
        cardViews = new ArrayList<>();
    }

    @Override
    public void add(CardView object) {
        super.add(object);
        cardViews.add(object);
    }

    @Override
    public void addAll(Collection<? extends CardView> collection) {
        super.addAll(collection);
        cardViews = (List<CardView>) collection;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_item_card, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.card_name);
        TextView manacost = (TextView) rowView.findViewById(R.id.card_mana_cost);
        TextView text = (TextView) rowView.findViewById(R.id.card_text);
        name.setText(cardViews.get(position).getName());
        manacost.setText(cardViews.get(position).getManaCost());
        text.setText(cardViews.get(position).getText());
        return rowView;
    }
}
