package net.skyestudios.mtgcardquery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.widget.AdapterView.*;

public class DecksViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_view);
		ListView decksList = (ListView) findViewById(R.id.deck_list);
		decksList.setOnItemClickListener(null);
    }
}
