package cyan.intellicyan.cyanosql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cyan.intellicyan.Config;

/**
 * Created by apple on 2017/3/26.
 */

public class DBClient extends SQLiteOpenHelper implements IDBClient {

    public static DBClient getInstance(Context ctx, String name) {
        return SingleInstanceHolder.init(ctx, name);
    }

    private static class SingleInstanceHolder {
        private static DBClient _t = null;

        private static DBClient init(Context ctx, String name) {
            if (_t == null)
                _t = new DBClient(ctx, name, null, Config.DB_VERSION);
            return _t;
        }
    }

    public List<Map<String, String>> retrieve(String tableName, String[] cols, String[] where, String[] cause) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
        } catch (Exception ex) {
        } finally {

        }
        return list;
    }

    public boolean insert(String tableName, String[] cols, String[] vals) {
        try {
            return true;
        } catch (Exception ex) {
            return false;
        } finally {

        }
    }

    public boolean update(String tableName, String[] cols, String[] vals, String[] where, String[] cause) {
        try {
            return true;
        } catch (Exception ex) {
            return false;
        } finally {

        }
    }

    public boolean delete(String tableName, String[] where, String[] cause) {
        try {
            return true;
        } catch (Exception ex) {
            return false;
        } finally {

        }
    }


    private DBClient(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //-----------下面的方法是用来多数据库结构判定的--------------

    /**
     * 对比字段
     * @param db
     * @param tableName
     * @param cols 要插入数据的字段
     */
    public void compareCols(SQLiteDatabase db,String tableName,String [] cols){

    }

}
