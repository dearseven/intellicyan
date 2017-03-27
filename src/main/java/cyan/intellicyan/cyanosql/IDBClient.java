package cyan.intellicyan.cyanosql;

import java.util.List;
import java.util.Map;

/**
 * Created by wx on 2017/3/26.
 */

public interface IDBClient {

    /**
     * 查询，所有字段必须都存在,否则肯定是没有值的啊 对不对
     *
     * @param tableName
     * @param cols
     * @param where
     * @param cause
     * @return
     */
    public List<Map<String, String>> retrieve(String tableName, String[] cols, String[] where, String[] cause);

    /**
     * 插入数据库
     *
     * @param tableName
     * @param cols
     * @param vals
     * @return
     */
    public boolean insert(String tableName, String[] cols, String[] vals);

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
    public boolean update(String tableName, String[] cols, String[] vals, String[] where, String[] cause);

    /**
     * 删除,where里的字段必须保证已经存在
     *
     * @param tableName
     * @param where
     * @param cause
     * @return
     */
    public boolean delete(String tableName, String[] where, String[] cause);
}
