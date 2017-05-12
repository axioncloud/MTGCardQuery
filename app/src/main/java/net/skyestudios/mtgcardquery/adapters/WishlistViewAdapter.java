package net.skyestudios.mtgcardquery.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

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
    public void add(Wishlist object) {
        super.add(object);
        wishlists.add(object);
    }

    @Override
    public void addAll(Collection<? extends Wishlist> collection) {
        super.addAll(collection);
        wishlists.addAll(collection);
    }
}
