package cyan.intellicyan.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cyan.intellicyan.Config;
import cyan.intellicyan.db.tables.TableTask;

/**
 * Created by apple on 2017/3/9.
 * @deprecated
 */

public class DBH extends SQLiteOpenHelper {
    private static DBH dbh = null;

    public static DBH getInstance(Context ctx) {
        if (dbh == null) {
            dbh = new DBH(ctx, "intellicyan", null, Config.DB_VERSION);
        }
        return dbh;
    }


    private DBH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableTask.makeTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
