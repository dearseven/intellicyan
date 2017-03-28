package cyan.intellicyan.beans;

/**
 * TodoItem的真的数据的存放对象
 */

public class TodoItemDetail {


    public static String TABLE_NAME = "tododetail";

    public static enum col {
        objKeyId, uuid, todoItemUuid
    }

    public int objKeyId;
    public String uuid;
    public String todoItemUuid;//上一层的uuid

    @Override
    public String toString() {
        return "TodoItemDetail{" +
                "objKeyId=" + objKeyId +
                ", uuid='" + uuid + '\'' +
                ", todoItemUuid='" + todoItemUuid + '\'' +
                '}';
    }
}
