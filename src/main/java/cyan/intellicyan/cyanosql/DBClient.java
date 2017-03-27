package cyan.intellicyan.cyanosql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyan.intellicyan.Config;
import cyan.intellicyan.cyanosql.sys.Infos;
import cyan.intellicyan.util.DLog;

/**
 * Created by apple on 2017/3/26.
 */

public class DBClient extends SQLiteOpenHelper implements IDBClient {

    public static DBClient getInstance(Context ctx, String name) {
        return SingleInstanceHolder.init(ctx, name);
    }

    public SQLiteDatabase getDb() {
        return getWritableDatabase();
    }

    private static class SingleInstanceHolder {
        private static DBClient _t = null;

        private static DBClient init(Context ctx, String name) {
            if (_t == null)
                _t = new DBClient(ctx, name, null, Config.DB_VERSION);
            return _t;
        }
    }


    public List<Map<String, String>> retrieve(SQLiteDatabase db, String sql, String[] selectionArgs, String[] colNames) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cs = null;
        try {
            cs = db.rawQuery(sql, selectionArgs);
            while (cs.moveToNext()) {
                Map<String, String> m = new HashMap<String, String>();
                if (colNames != null) {
                    for (int i = 0; i < colNames.length; i++) {
                        m.put(colNames[i], cs.getString(cs.getColumnIndex(colNames[i])));
                    }
                } else {
                    int colNumbers = cs.getColumnCount();
                    for (int i = 0; i < colNumbers; i++) {
                        m.put(cs.getColumnName(i), cs.getString(cs.getColumnIndex(cs.getColumnName(i))));
                    }
                }
                list.add(m);
            }
        } catch (Exception ex) {
            db.close();
        } finally {
            cs.close();
        }
        return list;
    }

    public List<Map<String, String>> retrieve(SQLiteDatabase db, String tableName, String[] cols, String where, String[] cause) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cs = null;
        try {
            cs = db.query(tableName, cols, where, cause, null, null, null, null);
            while (cs.moveToNext()) {
                Map<String, String> m = new HashMap<String, String>();
                if (cols != null) {
                    for (int i = 0; i < cols.length; i++) {
                        m.put(cols[i], cs.getString(cs.getColumnIndex(cols[i])));
                    }
                } else {
                    int colNumbers = cs.getColumnCount();
                    for (int i = 0; i < colNumbers; i++) {
                        m.put(cs.getColumnName(i), cs.getString(cs.getColumnIndex(cs.getColumnName(i))));
                    }
                }
                list.add(m);
            }
        } catch (Exception ex) {
            db.close();
        } finally {
            cs.close();
        }
        return list;
    }

    public boolean insert(SQLiteDatabase db, String tableName, String[] cols, String[] vals) {
        try {
            compareCols(db, tableName, cols);
            ContentValues cv = new ContentValues();
            for (int i = 0; i < cols.length; i++) {
                cv.put(cols[i], vals[i]);
            }
            db.insert(tableName, null, cv);
            return true;
        } catch (Exception ex) {
            db.close();
            return false;
        } finally {

        }
    }

    public boolean update(SQLiteDatabase db, String tableName, String[] cols, String[] vals, String where, String[] cause) {
        try {
            compareCols(db, tableName, cols);
            ContentValues cv = new ContentValues();
            for (int i = 0; i < cols.length; i++) {
                cv.put(cols[i], vals[i]);
            }
            db.update(tableName, cv, where, cause);
            return true;
        } catch (Exception ex) {
            db.close();
            return false;
        } finally {

        }
    }

    public boolean delete(SQLiteDatabase db, String tableName, String where, String[] cause) {
        try {
            db.delete(tableName, where, cause);
            return true;
        } catch (Exception ex) {
            db.close();
            return false;
        } finally {

        }
    }


    private DBClient(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Infos.makeTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //-----------下面的方法是用来多数据库结构判定的--------------

    /**
     * 对比字段
     *
     * @param db
     * @param tableName
     * @param cols      要插入数据的字段
     */
    public void compareCols(SQLiteDatabase db, String tableName, String[] cols) {
        Cursor csr = null;
        try {
            //先找看看表存在还是不存在
            csr = db.query(Infos.tableName, null, Infos.colColletionName + " = ?", new String[]{tableName}, null, null, null);
            if (csr.getCount() == 0) {//表不存在
                DLog.log(DBClient.class, "表不存在，创建：" + tableName);
                //先创建表
                StringBuilder sb = new StringBuilder();
                sb.append("create table ").append(tableName);
                sb.append(" ( ").append("objKeyId")
                        .append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ");
                sb.append(");");
                db.execSQL(sb.toString());
                sb.setLength(0);

                ContentValues cv = new ContentValues();
                cv.put(Infos.colColletionName, tableName);
                cv.put(Infos.colCollectionColumns, "");
                db.insert(Infos.tableName, null, cv);
            } else {//表存在，判断字段
                //找到已有的字段
                String columns = null;
                Map<String, Byte> existCols = new HashMap<String, Byte>(csr.getCount());
                while (csr.moveToNext()) {
                    columns = csr.getString(csr.getColumnIndex(Infos.colCollectionColumns));
                    String[] ec = columns.trim().split(",");
                    for (int i = 0; i < ec.length; i++) {
                        existCols.put(ec[i], new Byte((byte) 1));
                    }
                }
                //如果cols不在existCols里面则需要创建
                boolean addedNewCol = false;
                for (int i = 0; i < cols.length; i++) {
                    if (!existCols.containsKey(cols[i])) {
                        DLog.log(DBClient.class, "创建字段：" + cols[i]);
                        db.execSQL("ALTER TABLE " + tableName
                                + " ADD COLUMN " + cols[i] + " text not null default ''; ");
                        columns += "," + cols[i];
                    }
                }
                if (addedNewCol) {
                    DLog.log(DBClient.class, "字段有增加，修改后的总字段：" + columns);
                    ContentValues cv = new ContentValues();
                    cv.put(Infos.colCollectionColumns, columns);
                    db.update(Infos.tableName, cv, Infos.colColletionName + " = ?", new String[]{tableName});
                }
            }
        } catch (Exception ex) {

        } finally {
            if (csr != null && csr.isClosed() == false) {
                csr.close();
                csr = null;
            }
        }
    }

}
