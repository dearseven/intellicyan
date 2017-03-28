package cyan.intellicyan.beans;

/**
 * 这个是{@link cyan.intellicyan.activities.TodoListActivity}的RecyclerView的数据对象<br/>
 * 其实也用对数据库进行插入，但是要要有一个detail进行配合<br/>
 * 其实真正的数据是存储在detail里面的，todoitem是在列表显示的时候用<br/>
 * Created by wx on 2017/3/28.
 */

public class TodoItem {
    public static String TABLE_NAME = "todoitem";

    public static enum col {
        objKeyId, title, datetime, uuid
    }

    public int objKeyId;
    public String title;
    public String datetime;
    public String uuid;

    @Override
    public String toString() {
        return "TodoItem{" +
                "objKeyId=" + objKeyId +
                ", title='" + title + '\'' +
                ", datetime='" + datetime + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
