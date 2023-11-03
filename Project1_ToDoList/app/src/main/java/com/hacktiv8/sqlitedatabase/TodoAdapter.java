package com.hacktiv8.sqlitedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<Todo> todoList;

    private Context context;

    public TodoAdapter(Context context, List<Todo> todoList){
        this.context = context;
        this.todoList = todoList;
    }

    public TodoAdapter(Context context, List<Todo> todoList, editTodoListener editListener, deleteTodoListener deleteListener){
        this.context = context;
        this.todoList = todoList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        Todo todo = todoList.get(position);

        holder.idTodoTv.setText(String.valueOf(todo.getId()));
        holder.todoNameTv.setText(todo.getTodoName());

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView idTodoTv;
        private  TextView todoNameTv;

        private Button editButton;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTodoTv = itemView.findViewById(R.id.id_todo_tv);
            todoNameTv = itemView.findViewById(R.id.todo_name_tv);

            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editListener.onEditTodo(getAdapterPosition());
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteListener.onDeleteTodo(getAdapterPosition());
                }
            });
        }
    }

    public interface editTodoListener{
        void onEditTodo(int position);

    }

    public interface deleteTodoListener{
        void onDeleteTodo(int position);
    }

    private editTodoListener editListener;
    private deleteTodoListener deleteListener;
}
