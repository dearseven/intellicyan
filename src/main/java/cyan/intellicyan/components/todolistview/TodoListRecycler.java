package cyan.intellicyan.components.todolistview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        Context ctx = father.getAnyContext();
        this.rv.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
        this.rv.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
        this.rv.setItemAnimator(new DefaultItemAnimator());
        this.rv.setAdapter(this);
        notifyDataSetChanged();
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
        final TodoItem item = todos.get(position);

        holder.title.setText(item.title);
        holder.datetime.setText(item.datetime);


        holder.titleAndDatetimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                father.onItemListRecyclerClick(0, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    public class TodoItemHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView datetime;
        public LinearLayout titleAndDatetimeLayout;

        public TodoItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.todo_item_detail_title);
            datetime = (TextView) itemView.findViewById(R.id.todo_item_detail_datetime);
            titleAndDatetimeLayout = (LinearLayout) itemView.findViewById(R.id.todo_item_detail_linear_layout);
        }
    }
    //------end RecyclerView.Adapter and ViewHolder------

    public interface Father {
        <T> T findFatherViewById(int id);

        void onItemListRecyclerClick(int viewId, Object param);

        <T> T getAnyContext();

    }

    /**
     * 插入一个todoitem
     *
     * @param index
     * @param item
     * @param refreshView 是否刷新视图
     */
    public void insertItem(int index, TodoItem item, boolean refreshView) {
        todos.add(index, item);
        if (refreshView) {
            notifyDataSetChanged();
        }
    }

    /**
     * 追加一组清除所有todoitem
     *
     * @param items
     * @param refreshView 是否刷新视图
     */
    public void appendItem(List<TodoItem> items, boolean refreshView) {
        for (int i = 0; i < items.size(); i++) {
            todos.add(items.get(i));
        }
        if (refreshView) {
            notifyDataSetChanged();
        }
    }

    /**
     * 追加一个item
     *
     * @param item
     * @param refreshView 是否刷新视图
     */
    public void appendItem(TodoItem item, boolean refreshView) {
        todos.add(item);
        if (refreshView) {
            notifyDataSetChanged();
        }
    }

    /**
     * 清除所有todoitem
     *
     * @param refreshView 是否刷新视图
     */
    public void clearItem(boolean refreshView) {
        todos.clear();
        if (refreshView) {
            notifyDataSetChanged();
        }
    }

    /**
     * 删除一个todoitem，根据是索引号
     *
     * @param index
     * @param refreshView 是否刷新视图
     */
    public void delItem(int index, boolean refreshView) {
        todos.remove(index);
        if (refreshView) {
            notifyDataSetChanged();
        }
    }

    /**
     * 删除一个todoitem，实际上是根据objKeyId
     *
     * @param item
     * @param refreshView 是否刷新视图
     */
    public void delItem(TodoItem item, boolean refreshView) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).objKeyId == item.objKeyId) {
                todos.remove(i);
                break;
            }
        }
        if (refreshView) {
            notifyDataSetChanged();
        }
    }

    /**
     * 释放一下activity或者fragment
     */
    public void release() {
        father = null;
    }

}
