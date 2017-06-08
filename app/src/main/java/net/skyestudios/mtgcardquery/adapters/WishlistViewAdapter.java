package net.skyestudios.mtgcardquery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.skyestudios.mtgcardquery.R;
import net.skyestudios.mtgcardquery.data.Wishlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by arkeonet64 on 5/12/2017.
 */

public class WishlistViewAdapter extends ArrayAdapter<Wishlist> {

    List<Wishlist> wishlists;

    public WishlistViewAdapter(Context context, int resource) {
        super(context, resource);
        wishlists = new ArrayList<>();
    }

    @Override
    public void clear() {
        super.clear();
        wishlists.clear();
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            View rowView = inflater.inflate(R.layout.layout_item_wishlist, parent, false);
            TextView wishlist_name = (TextView) rowView.findViewById(R.id.wishlist_name);
            TextView wishlist_num_cards = (TextView) rowView.findViewById(R.id.wishlist_num_cards);
            wishlist_name.setText(wishlists.get(position).getName());
            wishlist_num_cards.setText(String.valueOf(wishlists.get(position).getNumberOfCards()));
            return rowView;
        } else {
            return convertView;
        }
    }

    @Override
    public void add(Wishlist object) {
        super.add(object);
        wishlists.add(object);
    }

    @Override
    public void addAll(Collection<? extends Wishlist> collection) {
        if (collection != null) {
            super.addAll(collection);
            wishlists.addAll(collection);
        }
    }

    @Override
    public void remove(Wishlist object) {
        super.remove(object);
        wishlists.remove(object);
    }
}
