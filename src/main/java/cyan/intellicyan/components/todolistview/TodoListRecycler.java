package cyan.intellicyan.components.todolistview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by apple on 2017/3/15.
 */

public class TodoListRecycler extends RecyclerView.Adapter<TodoListRecycler.TodoItemHolder> {

    //------start RecyclerView.Adapter and ViewHolder------
    @Override
    public TodoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TodoItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TodoItemHolder extends RecyclerView.ViewHolder {
        public TodoItemHolder(View itemView) {
            super(itemView);
        }
    }
    //------end RecyclerView.Adapter and ViewHolder------


}
