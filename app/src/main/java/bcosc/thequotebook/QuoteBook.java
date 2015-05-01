package bcosc.thequotebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import android.content.Intent;
import android.widget.Toast;


public class QuoteBook extends ActionBarActivity {

    //private final int ADD_QUOTE_REQUEST = 0;
    //private final int DELETE_QUOTE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_book);

        RelativeLayout touch = (RelativeLayout) findViewById(R.id.touch);
        final TextView quoteText = (TextView) findViewById(R.id.quote);
        final TextView personText = (TextView) findViewById(R.id.person);
        final SQLDataHelper DBmanager = new SQLDataHelper(this);

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = DBmanager.getReadableDatabase();
                Cursor cursor = db.query(SQLContract.FeedEntry.TABLE_NAME + " Order BY RANDOM() LIMIT 1",
                        new String[]{"*"}, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    Log.d("quoteText", "should be" + cursor.getString(3));
                    Log.d("personText", "should be" + cursor.getString(2));
                    quoteText.setText(cursor.getString(3));
                    personText.setText(cursor.getString(2));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quote_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.add_quote:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addQuote(MenuItem item){
        Intent intent = new Intent(this, DisplayAddQuote.class);
        startActivityForResult(intent, 0);
    }

    public void deleteQuote(MenuItem item){
        Intent intent = new Intent(this, DisplayDeleteQuote.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                String newQuote = data.getStringExtra("newQuote");
                Log.d("QuoteBook received", "QuoteBook received a new quote: " + newQuote);
                String newName = data.getStringExtra("newName");
                Log.d("QuoteBook received", "QuoteBook received a new quote: " + newName);

                SQLDataHelper DBmanager = new SQLDataHelper(this);
                SQLiteDatabase db = DBmanager.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(SQLContract.FeedEntry.COLUMN_NAME_NAME, newName);
                values.put(SQLContract.FeedEntry.COLUMN_NAME_QUOTE, newQuote);
                db.insert(SQLContract.FeedEntry.TABLE_NAME, null, values);
                String selectQuery = "SELECT * FROM "+ SQLContract.FeedEntry.TABLE_NAME +";";
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        Log.d("We got a working db!!", "check?"+cursor.getString(0));
                        Log.d("We got a working db!!", "check?"+cursor.getString(2));
                        Log.d("We got a working db!!", "check?"+cursor.getString(3));
                        Log.d("Cursor Count","check "+cursor.getCount());
                    }while(cursor.moveToNext());
                }
                db.close();
            case 1:
                String newdelQuote = data.getStringExtra("delQuote");
                Log.d("QuoteBook received", "QuoteBook received is about to delete a quote: " + newdelQuote);
                String newdelName = data.getStringExtra("delName");
                Log.d("QuoteBook received", "QuoteBook received is about to delete a quote: " + newdelName);

                SQLDataHelper DBmanagedeleter = new SQLDataHelper(this);
                SQLiteDatabase dbdeleter = DBmanagedeleter.getWritableDatabase();

                dbdeleter.delete(SQLContract.FeedEntry.TABLE_NAME, SQLContract.FeedEntry.COLUMN_NAME_QUOTE +
                        " = ?", new String[] {newdelQuote});
        }
    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
