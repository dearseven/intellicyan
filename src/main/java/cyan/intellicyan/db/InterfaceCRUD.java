package cyan.intellicyan.db;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

/**
 * Created by wx on 2017/3/9.
 * @deprecated
 */

public interface InterfaceCRUD<T> {
    public void insert(Context ctx, ContentValues contentValues);

    public void update(Context ctx, ContentValues contentValues, String where, String[] whereCause);

    public List<T> find(Context ctx, String where, String[] whereCause);

    public void del(Context ctx, String where, String[] whereCause);

    public List<T> find(Context ctx,String raw);
}
