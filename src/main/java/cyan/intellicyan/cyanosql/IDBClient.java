package cyan.intellicyan.cyanosql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wx on 2017/3/26.
 */

public interface IDBClient {

    public List<Map<String, String>> retrieve(String tableName, String[] cols, String[] where, String[] cause);

    public boolean insert(String tableName, String[] cols, String[] vals);

    public boolean update(String tableName, String[] cols, String[] vals, String[] where, String[] cause);

    public boolean delete(String tableName, String[] where, String[] cause);
}
