package net.skyestudios.mtgcardquery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import net.skyestudios.mtgcardquery.data.Card;

public class CardViewActivity extends AppCompatActivity {

    public final static String VIEW_TYPE = "VIEW_TYPE";
    public final static String VIEW_CARD = "CARD";
    public final static String VIEW_CARD_IMAGE = "IMAGE";
    public static final String CARD = "CARD";
    private Intent cardViewIntent;
    private Context context;
    private Card card;

    TextView name;
    TextView colors;
    TextView manaCost;
    TextView cmc;
   // TextView types;
    TextView cardText;

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
        }else{
            throw new IllegalArgumentException();
        }
        if ((card = (Card) extras.getSerializable(CARD)) == null)
        {
            throw new IllegalArgumentException();
        }
        setup();
    }

    private void setup() {
        name = (TextView) this.findViewById(R.id.name_textView);
        name.setText(card.getName());
        colors = (TextView) this.findViewById(R.id.colors_textView);
        colors.setText(card.getColors());
        manaCost = (TextView) this.findViewById(R.id.manaCost_textView);
        manaCost.setText(card.getManaCost().toString());
        cmc = (TextView) this.findViewById(R.id.cmc_textView);
        cmc.setText(card.getCmc().toString());
        /*types = (TextView) this.findViewById(R.id.types_textView);
        String superType = card.getSupertypes().toString();
        String subType = card.getSubtypes().toString();
        types.setText(superType + " - " + subType);*/
        cardText = (TextView) this.findViewById(R.id.cardText_textView);
        cardText.setText(card.getText());
    }
}
