package net.skyestudios.mtgcardquery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView drawerList;
    private ArrayAdapter<String> drawerListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);


        drawerListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drawerListStrings));
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerList.setAdapter(drawerListAdapter);

    }
}
