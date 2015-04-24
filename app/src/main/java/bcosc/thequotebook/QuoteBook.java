package bcosc.thequotebook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Scanner;
import android.content.Intent;
import android.widget.Toast;


public class QuoteBook extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "message";

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_book);

        RelativeLayout touch = (RelativeLayout) findViewById(R.id.touch);
        final TextView quoteText = (TextView) findViewById(R.id.quote);
        final TextView personText = (TextView) findViewById(R.id.person);

        final ArrayList<Quote> quoteList = new ArrayList<Quote>();

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


        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count < quoteList.size()) {
                    Quote q = quoteList.get(count);
                    quoteText.setText(q.getQuote());
                    personText.setText(q.getPerson());
                    count = count + 1;
                } else {
                    count = 0;
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
    public void doThis(MenuItem item){
        Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show();
    }
    public void addQuote(MenuItem item){
        Intent intent = new Intent(this, DisplayAddQuote.class);
        startActivity(intent);
    }
}
