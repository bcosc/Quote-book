package bcosc.thequotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Bryan on 4/30/2015.
 */
public class DisplayDeleteQuote extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_delete_quote);

        final EditText DelinputQuote = (EditText) findViewById(R.id.DeleditQuote);
        final EditText DelinputName = (EditText) findViewById(R.id.DeleditName);

        Button deleteFromQuoteBook = (Button) findViewById(R.id.DeleteButton);
        deleteFromQuoteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityResult = new Intent(getApplicationContext(), QuoteBook.class);
                activityResult.putExtra("delQuote", DelinputQuote.getText().toString());
                Log.d("QuoteBook-OnClick", "About to Delete a quote");
                activityResult.putExtra("delName", DelinputName.getText().toString());
                Log.d("QuoteBook-OnClick", "About to Delete a name");
                setResult(RESULT_OK, activityResult);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_delete_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
