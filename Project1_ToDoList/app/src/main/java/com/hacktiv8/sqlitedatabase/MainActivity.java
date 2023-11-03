package com.hacktiv8.sqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.editTodoListener, TodoAdapter.deleteTodoListener{

    private Button addButton;
    private RecyclerView listTodoRv;

    SQLiteDatabaseHandler db;

    private List<Todo> todoList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.add_data);
        listTodoRv = findViewById(R.id.list_todo_rv);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForm(null, false);
            }
        });

        db = new SQLiteDatabaseHandler(this);
        loadDataTodo();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listTodoRv.setLayoutManager(layoutManager);

    }

    private void loadDataTodo(){
        todoList = db.getAllTodo();
        todoAdapter = new TodoAdapter(this, todoList, this, this);
        listTodoRv.setAdapter(todoAdapter);
    }

    private void showForm(Todo todoEdit, boolean isEdit) {
        AlertDialog.Builder formBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.form_todo, null);

        formBuilder.setView(view);

        AlertDialog popup = formBuilder.create();
        popup.show();

        EditText todoNameInput = view.findViewById(R.id.todo_name_et);
        Button saveButton = view.findViewById(R.id.save_data);

        if (isEdit){
            todoNameInput.setText(todoEdit.getTodoName());
            saveButton.setText("UPDATE DATA");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //SAVE Data Ke SQLITE
                String todoName = todoNameInput.getText().toString();


                if (!isEdit){
                    //ADD Data
                    Todo todo = new Todo(todoName);
                    db.addTodo(todo);

                }else {
                    //EDIT DATA
                    todoEdit.setTodoName(todoName);
                    db.updateTodo(todoEdit);
                }

                loadDataTodo();
                popup.dismiss();
            }
        });
    }


    @Override
    public void onEditTodo(int position) {
        Todo selectedTodoEdit = todoList.get(position);
        showForm(selectedTodoEdit, true);
    }

    @Override
    public void onDeleteTodo(int position) {
        Todo selectedTodoDelete = todoList.get(position);
        db.deleteTodo(selectedTodoDelete);
        loadDataTodo();
    }
}