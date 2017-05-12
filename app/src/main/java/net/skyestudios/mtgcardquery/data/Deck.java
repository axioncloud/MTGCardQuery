package net.skyestudios.mtgcardquery.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkeonet64 on 4/25/2017.
 */

public class Deck implements Serializable {
    String name;
    DeckType deckType;
    Integer capacity;
    List<Card> cards;

    public Deck(String name, DeckType deckType) {
        this.name = name;
        this.deckType = deckType;
        switch (deckType) {
            case Commander:
                capacity = 100;
                break;
            case Legacy:
                capacity = 60;
                break;
            case Modern:
                capacity = 60;
                break;
            case Standard:
                capacity = 60;
                break;
            default:
                break;
        }
        this.cards = new ArrayList<>();
    }
}
