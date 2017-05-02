package net.skyestudios.mtgcardquery.views;

import net.skyestudios.mtgcardquery.data.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkeonet64 on 5/1/2017.
 */
public class CardView {
    Card card;

    public CardView(Card card) {
        this.card = card;
    }

    public static List<CardView> createListFromCards(List<Card> cards) {
        List<CardView> cardViews = new ArrayList<>();
        for (Card card :
                cards) {
            cardViews.add(new CardView(card));
        }
        return cardViews;
    }

    public String getName() {
        return card.getName();
    }

    public String getManaCost() {
        return card.getManaCost();
    }

    public String getText() {
        return card.getText();
    }
}
