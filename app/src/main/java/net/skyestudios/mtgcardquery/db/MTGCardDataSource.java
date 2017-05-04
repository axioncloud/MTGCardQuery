package net.skyestudios.mtgcardquery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.skyestudios.mtgcardquery.data.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkeonet64 on 4/4/2017.
 */

public class MTGCardDataSource {
    private SQLiteDatabase database;
    private long rowId;
    private long rowIdIndex;
    private MTGCardSQLiteHelper databaseHelper;

    public MTGCardDataSource(Context context) {
        databaseHelper = new MTGCardSQLiteHelper(context);
        rowIdIndex = 1;
        rowId = 0;
    }

    public List<Card> query(String sql) {
        ArrayList<Card> cards = new ArrayList<>();

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Card card = new Card();
            card.setName(cursor.getString(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.name.toString())));
            card.setText(cursor.getString(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.text.toString())));
            card.setCmc(cursor.getDouble(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.cmc.toString())));
            card.setManaCost(cursor.getString(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.manaCost.toString())));
            card.setLoyalty(cursor.getInt(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.loyalty.toString())));
            card.setColors(cursor.getString(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.colors.toString())));
            card.setColorIdentity(cursor.getString(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.colorIdentity.toString())));
            card.setType(cursor.getString(cursor.getColumnIndex(MTGCardSQLiteHelper.CardColumns.type.toString())));
            cards.add(card);
        }
        cursor.close();
        return cards;
    }

    public void resetRowIdIndex() {
        rowIdIndex = 1;
    }

    public void openDb() {
        database = databaseHelper.getWritableDatabase();
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccessful() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void closeDb() {
        database.close();
    }

    public void insertCard(Card card) {
        ContentValues values = new ContentValues();

        values.put(MTGCardSQLiteHelper.CardColumns.name.toString(), card.getName());
        values.put(MTGCardSQLiteHelper.CardColumns.layout.toString(), card.getLayout());
        values.put(MTGCardSQLiteHelper.CardColumns.cmc.toString(), card.getCmc());
        values.put(MTGCardSQLiteHelper.CardColumns.manaCost.toString(), card.getManaCost());
        values.put(MTGCardSQLiteHelper.CardColumns.colors.toString(), card.getColors());
        values.put(MTGCardSQLiteHelper.CardColumns.type.toString(), card.getType());
        values.put(MTGCardSQLiteHelper.CardColumns.types.toString(), card.getTypes());
        values.put(MTGCardSQLiteHelper.CardColumns.subtypes.toString(), card.getSubtypes());
        values.put(MTGCardSQLiteHelper.CardColumns.text.toString(), card.getText());
        values.put(MTGCardSQLiteHelper.CardColumns.power.toString(), card.getPower());
        values.put(MTGCardSQLiteHelper.CardColumns.toughness.toString(), card.getToughness());
        values.put(MTGCardSQLiteHelper.CardColumns.imageName.toString(), card.getImageName());
        values.put(MTGCardSQLiteHelper.CardColumns.printings.toString(), card.getPrintings());
        values.put(MTGCardSQLiteHelper.CardColumns.source.toString(), card.getSource());
        values.put(MTGCardSQLiteHelper.CardColumns.rulings.toString(), card.getRulings());
        values.put(MTGCardSQLiteHelper.CardColumns.colorIdentity.toString(), card.getColorIdentity());
        values.put(MTGCardSQLiteHelper.CardColumns.legalities.toString(), card.getLegalities());
        values.put(MTGCardSQLiteHelper.CardColumns.supertypes.toString(), card.getSupertypes());
        values.put(MTGCardSQLiteHelper.CardColumns.starter.toString(), card.getStarter());
        values.put(MTGCardSQLiteHelper.CardColumns.loyalty.toString(), card.getLoyalty());
        values.put(MTGCardSQLiteHelper.CardColumns.hand.toString(), card.getHand());
        values.put(MTGCardSQLiteHelper.CardColumns.life.toString(), card.getLife());
        values.put(MTGCardSQLiteHelper.CardColumns.names.toString(), card.getNames());

        rowId = database.insert(MTGCardSQLiteHelper.MAIN_TABLE_NAME, null, values);
    }

    public void execRAWSQL(String sql) {
        database.execSQL(sql);
    }
}
