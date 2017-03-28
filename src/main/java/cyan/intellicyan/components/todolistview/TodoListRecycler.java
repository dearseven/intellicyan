package cyan.intellicyan.components.todolistview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cyan.intellicyan.R;

/**
 * Created by wx on 2017/3/15.
 */

public class TodoListRecycler extends RecyclerView.Adapter<TodoListRecycler.TodoItemHolder> {


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
        return 0;
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



}
