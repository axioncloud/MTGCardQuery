package net.skyestudios.mtgcardquery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Created by arkeonet64 on 4/4/2017.
 */

public class MTGCardSQLiteHelper extends SQLiteOpenHelper {

    public static final String MAIN_TABLE_NAME = "MAINTABLE";
    public static final String STAGING_TABLE_NAME = "STAGINGTABLE";
    public static final String DB_NAME = "cards.sqlite";

    public MTGCardSQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @NonNull
    public static String createMainTableString() {
        return "CREATE TABLE " + MAIN_TABLE_NAME + " (" +
                CardColumns.id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  UNIQUE , " +
                CardColumns.name + " TEXT , " +
                CardColumns.layout + " TEXT , " +
                CardColumns.cmc + " TEXT , " +
                CardColumns.manaCost + " NUMERIC , " +
                CardColumns.colors + " TEXT , " +
                CardColumns.type + " TEXT , " +
                CardColumns.types + " TEXT , " +
                CardColumns.subtypes + " TEXT , " +
                CardColumns.text + " TEXT , " +
                CardColumns.power + " TEXT , " +
                CardColumns.toughness + " TEXT , " +
                CardColumns.imageName + " TEXT , " +
                CardColumns.printings + " TEXT , " +
                CardColumns.source + " TEXT , " +
                CardColumns.rulings + " TEXT , " +
                CardColumns.colorIdentity + " TEXT , " +
                CardColumns.legalities + " TEXT , " +
                CardColumns.supertypes + " TEXT , " +
                CardColumns.starter + " TEXT , " +
                CardColumns.loyalty + " INTEGER , " +
                CardColumns.hand + " INTEGER , " +
                CardColumns.life + " INTEGER , " +
                CardColumns.names + " TEXT )";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = createMainTableString();
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public enum CardColumns {
        id,
        name, layout, manaCost,
        cmc, colors,
        type, types,
        subtypes, text,
        power, toughness,
        imageName, printings,
        legalities, colorIdentity,
        rulings, source,
        supertypes, starter,
        loyalty, hand,
        life, names
    }
}
