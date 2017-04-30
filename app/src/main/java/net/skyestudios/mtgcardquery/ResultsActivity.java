package net.skyestudios.mtgcardquery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        queryString = getIntent().getStringExtra("queryString");
        //Open DB
        //query
        //get results
        //display results and close DB
    }
}
