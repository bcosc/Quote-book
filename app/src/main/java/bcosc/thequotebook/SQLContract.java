package bcosc.thequotebook;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Bryan on 4/28/2015.
 */
public final class SQLContract {
    public SQLContract() {}

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "QuoteDB";
        public static final String COLUMN_NAME_ENTRY_ID = "EntryID";
        public static final String COLUMN_NAME_NAME = "QuoteName";
        public static final String COLUMN_NAME_QUOTE = "QuoteText";

    }
}
