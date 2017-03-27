package cyan.intellicyan.cyanosql;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

/**
 * Created by wx on 2017/3/26.
 */

public interface IDBClient {
    /**
     * 原生查询
     *
     * @param db
     * @param sql           select * from table where a=? and b=?
     * @param selectionArgs new String[]{"fora","forb"};
     * @param colNames      new String[]{"a","b"} 要查询的字段
     * @return
     */
    public List<Map<String, String>> retrieve(SQLiteDatabase db, String sql, String[] selectionArgs, String[] colNames);

    /**
     * 查询，所有字段必须都存在,否则肯定是没有值的啊 对不对
     *
     * @param tableName
     * @param cols
     * @param where
     * @param cause
     * @return
     */
    public List<Map<String, String>> retrieve(SQLiteDatabase db, String tableName, String[] cols, String where, String[] cause);

    /**
     * 插入数据库
     *
     * @param tableName
     * @param cols
     * @param vals
     * @return
     */
    public boolean insert(SQLiteDatabase db, String tableName, String[] cols, String[] vals);

    /**
     * 修改数据库，where里的字段必须保证已经存在
     *
     * @param tableName
     * @param cols
     * @param vals
     * @param where
     * @param cause
     * @return
     */
    public boolean update(SQLiteDatabase db, String tableName, String[] cols, String[] vals, String where, String[] cause);

    /**
     * 删除,where里的字段必须保证已经存在
     *
     * @param tableName
     * @param where
     * @param cause
     * @return
     */
    public boolean delete(SQLiteDatabase db, String tableName, String where, String[] cause);
}
