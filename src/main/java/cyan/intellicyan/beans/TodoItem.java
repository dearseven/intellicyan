package cyan.intellicyan.beans;

/**
 * 这个是{@link cyan.intellicyan.activities.TodoListActivity}的RecyclerView的数据对象<br/>
 * 其实也用对数据库进行插入，但是要要有一个detail进行配合<br/>
 * 其实真正的数据是存储在detail里面的，todoitem是在列表显示的时候用<br/>
 * Created by wx on 2017/3/28.
 */

public class TodoItem {
    public static enum col {
        objKeyId, title, detetime
    }

    private int objKeyId;
    private String title;
    private String datetime;

    /**
     * 真的数据
     */
    public static class TodoDetail {

    }

    public int getObjKeyId() {
        return objKeyId;
    }

    public void setObjKeyId(int objKeyId) {
        this.objKeyId = objKeyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
