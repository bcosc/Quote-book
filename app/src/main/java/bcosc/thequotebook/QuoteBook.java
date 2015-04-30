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

    public final static String EXTRA_MESSAGE = "message";
    private final int ADD_QUOTE_REQUEST = 0;

    private int count = 0;
    private ArrayList<Quote> quoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_book);

        RelativeLayout touch = (RelativeLayout) findViewById(R.id.touch);
        final TextView quoteText = (TextView) findViewById(R.id.quote);
        final TextView personText = (TextView) findViewById(R.id.person);

        quoteList = new ArrayList<Quote>();

        Quote quote4 = new Quote("Maybe I'm gay.", "Elias Loucagos");
        quoteList.add(quote4);

        Quote quote1 = new Quote("OMG SKIP", "Gowtam Lal");
        quoteList.add(quote1);

        Quote quote2 = new Quote("tree flower street street sign street sidewalk", "John Coners");
        quoteList.add(quote2);

        Quote quote3 = new Quote("Im down.", "Ronak Patel");
        quoteList.add(quote3);

        Quote quote5 = new Quote("SUP NOOBS", "Kai Burgin");
        quoteList.add(quote5);

        Quote quote6 = new Quote("Can you talk to rima, tell her that i miss the times she gave me dome", "Sam Chen");
        quoteList.add(quote6);
        //Add more quotes here

        final SQLDataHelper DBmanager = new SQLDataHelper(this);

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rand_num;
                rand_num = randInt(1,15);

                SQLiteDatabase db = DBmanager.getReadableDatabase();
                //String selectQuery = "SELECT * FROM "+ SQLContract.FeedEntry.TABLE_NAME +
                //        " WHERE " + SQLContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " = " +rand_num +"";
                //Log.d("query",selectQuery);
                //Cursor cursor = db.rawQuery(selectQuery, null);

                //Cursor cursor = db.rawQuery(
                //    "SELECT * FROM "+ SQLContract.FeedEntry.TABLE_NAME + "ORDER BY RANDOM() LIMIT 15", null);
                Cursor cursor = db.query( SQLContract.FeedEntry.TABLE_NAME+" Order BY RANDOM() LIMIT 1",
                        new String[] { "*" }, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    Log.d("QuoteText","should be" + cursor.getString(2));
                    Log.d("QuoteText","should be"+cursor.getString(3));
                    quoteText.setText(cursor.getString(2));
                    personText.setText(cursor.getString(3));
                }

                //if (count < quoteList.size()) {
                //    Quote q = quoteList.get(count);
                //    quoteText.setText(q.getQuote());
                //    personText.setText(q.getPerson());
                //    count = count + 1;
                //} else {
                //    count = 0;
                //}
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
        startActivityForResult(intent, ADD_QUOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_QUOTE_REQUEST:
                if (resultCode == RESULT_OK) {
                    String newQuote = data.getStringExtra("newQuote");
                    Log.d("QuoteBook received", "QuoteBook received a new quote: " + newQuote);
                    String newName = data.getStringExtra("newName");
                    Log.d("QuoteBook received", "QuoteBook received a new quote: " + newName);
                    Quote newquote = new Quote(newQuote, newName);
                    quoteList.add(newquote);

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

                    //SQLDataHelper DBreadHelper = new SQLDataHelper(this);
                    //SQLiteDatabase dbreader = DBreadHelper.getReadableDatabase();
                    //String selectQuery = "SELECT * FROM "+ SQLContract.FeedEntry.TABLE_NAME +";";
                    //Cursor cursor = dbreader.rawQuery(selectQuery, null);
                    //if (cursor.moveToFirst()) {
                    //    do {
                    //        Log.d("We got a working db!!", "check?"+cursor.getString(0));
                    //    }while(cursor.moveToNext());
                    //}

                } else {
                    Log.e("QuoteBook", "Unable to get result from DisplayAddQuote");
                }
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
