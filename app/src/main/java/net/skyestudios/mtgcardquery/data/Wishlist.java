package net.skyestudios.mtgcardquery.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkeonet64 on 4/25/2017.
 */

public class Wishlist implements Serializable {
    String name;
    List<Card> cards;

    public Wishlist(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }
}
