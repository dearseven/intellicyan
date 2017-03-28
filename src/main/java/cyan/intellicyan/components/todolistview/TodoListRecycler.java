package cyan.intellicyan.components.todolistview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cyan.intellicyan.R;
import cyan.intellicyan.beans.TodoItem;

/**
 * Created by wx on 2017/3/15.
 */

public class TodoListRecycler extends RecyclerView.Adapter<TodoListRecycler.TodoItemHolder> {
    private List<TodoItem> todos = null;
    private RecyclerView rv;
    private Father father;

    public TodoListRecycler(Father father, List<TodoItem> todos, int recyclerViewId) {
        this.father = father;
        //
        this.todos = todos;
        this.rv = father.findFatherViewById(recyclerViewId);
    }

    //------start RecyclerView.Adapter and ViewHolder------
    @Override
    public TodoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_view, parent, false);
        TodoItemHolder holder = new TodoItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(TodoItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    public class TodoItemHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView datetime;

        public TodoItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.todo_item_detail_title);
            datetime = (TextView) itemView.findViewById(R.id.todo_item_detail_datetime);
        }
    }
    //------end RecyclerView.Adapter and ViewHolder------

    public interface Father {
        <T> T findFatherViewById(int id);

        void onItemListRecyclerClick(int viewId, Object param);
    }

    /**
     * 插入一个todoitem
     *
     * @param index
     * @param item
     * @param refreshView 是否刷新视图
     */
    public void insertItem(int index, TodoItem item, boolean refreshView) {

    }

    /**
     * 追加一组清除所有todoitem
     *
     * @param items
     * @param refreshView 是否刷新视图
     */
    public void appendItem(List<TodoItem> items, boolean refreshView) {

    }

    /**
     * 追加一个item
     *
     * @param item
     * @param refreshView 是否刷新视图
     */
    public void appendItem(TodoItem item, boolean refreshView) {

    }

    /**
     * 清除所有todoitem
     *
     * @param refreshView 是否刷新视图
     */
    public void clearItem(boolean refreshView) {
    }

    /**
     * 删除一个todoitem，根据是索引号
     *
     * @param index
     * @param refreshView 是否刷新视图
     */
    public void delItem(int index, boolean refreshView) {

    }

    /**
     * 删除一个todoitem，实际上是根据objKeyId
     *
     * @param item
     * @param refreshView 是否刷新视图
     */
    public void delItem(TodoItem item, boolean refreshView) {

    }

    /**
     * 释放一下activity或者fragment
     */
    public void release() {
        father = null;
    }

}
