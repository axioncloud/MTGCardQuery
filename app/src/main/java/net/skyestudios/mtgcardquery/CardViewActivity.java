package net.skyestudios.mtgcardquery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.skyestudios.mtgcardquery.data.Card;

public class CardViewActivity extends AppCompatActivity {

    public final static String VIEW_TYPE = "VIEW_TYPE";
    public final static String VIEW_CARD = "CARD";
    public final static String VIEW_CARD_IMAGE = "IMAGE";
    public static final String CARD = "CARD";
    private Intent cardViewIntent;
    private Context context;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardViewIntent = getIntent();
        context = getApplicationContext();
        Bundle extras = cardViewIntent.getExtras();
        if (extras.getString(VIEW_TYPE).equals(VIEW_CARD)) {
            setContentView(R.layout.layout_card_view);
        } else if (extras.getString(VIEW_TYPE).equals(VIEW_CARD_IMAGE)) {
            setContentView(R.layout.layout_card_image_view);
        } else if ((card = (Card) extras.getSerializable(CARD)) == null) {
            throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }
        setup();
    }

    private void setup() {

    }
}
