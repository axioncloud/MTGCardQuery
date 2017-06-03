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

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Wishlist) obj).getName());
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public List<Card> getCards() {
        return cards;
    }
}
