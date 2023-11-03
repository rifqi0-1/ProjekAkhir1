package com.hacktiv8.sqlitedatabase;

public class Todo {

    int id;
    String todoName;


    public Todo(){
        super();
    }

    public Todo(int id, String todoName){
        super();
        this.id = id;
        this.todoName = todoName;

    }

    public Todo(String todoName){
        this.todoName = todoName;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }


}
