package com.hacktiv8.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_TODO" ;
    private static final int DATABASE_VERSION = 1 ;
    private static  final String TABLE_TODO = "TODO";
    private static  final String KEY_ID ="id";
    private static  final String TODO_NAME = "todo_name";


    public SQLiteDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreateSql = " CREATE TABLE " + TABLE_TODO+ "("+
                                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                TODO_NAME +" TEXT) ";
        System.out.println("create Table : "+tableCreateSql);
        db.execSQL(tableCreateSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void addTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TODO_NAME, todo.getTodoName());

        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public List<Todo> getAllTodo(){

        List<Todo> todoList = new ArrayList<>();
        //
        String queryAll = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryAll, null);

        if (cursor.moveToFirst()){
            do{
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setTodoName(cursor.getString(1));

                todoList.add(todo);
            }
            while ( cursor.moveToNext());
        }
        //
        return todoList;
    }

    public void deleteTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }

    public int updateTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_NAME, todo.getTodoName());

        return db.update(TABLE_TODO, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(todo.getId())});

    }
}
