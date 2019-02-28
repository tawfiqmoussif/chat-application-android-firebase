package com.moussif.tawfi.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbManager extends SQLiteOpenHelper {
    static final String SQL_DB_NAME ="MyNotes";
    static final String dbNotestable="Notes";
    static final int Db_version=1;
    static final String Key_id="id";
    static final String Key_note="note";
    static final String Key_title="title";
    static final String TABLE_NOTES="notes";

    public DbManager( Context context) {
        super(context, SQL_DB_NAME, null, Db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table="create table IF NOT EXISTS "+TABLE_NOTES+"("+Key_id+" integer primary key,"+Key_title+" TEXT ,"+Key_note+" TEXT)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String delete_query="DROP table "+TABLE_NOTES+" IF EXISTS";
        db.execSQL(delete_query);
        onCreate(db);
    }
    public void addNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Key_title,note.getTitle());
        values.put(Key_note,note.getNoteText());
        db.insert(TABLE_NOTES,null,values);
    }
    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes=new ArrayList<>();
        String select_query="select * from "+TABLE_NOTES;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(select_query,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(Key_id));
                String title=cursor.getString(cursor.getColumnIndex(Key_title));
                String noteText=cursor.getString(cursor.getColumnIndex(Key_note));
                Note note=new Note(id,title,noteText);
                notes.add(note);
            }while (cursor.moveToNext());
        }
        return notes;
    }
    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NOTES,new String[]{Key_title,Key_note,},Key_title+"like ?",new String[]{"%"},null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(Key_id));
                String title=cursor.getString(cursor.getColumnIndex(Key_title));
                String noteText=cursor.getString(cursor.getColumnIndex(Key_note));
                Note note=new Note(id,title,noteText);
                notes.add(note);
            }while (cursor.moveToNext());
        }
        return notes;
    }

    public Note getNoteById(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql_query="select * from "+TABLE_NOTES+" where id="+id;
        Cursor cursor=db.rawQuery(sql_query,null);
        Note note=null;
        if(cursor.moveToFirst()){
            int id_note=cursor.getInt(cursor.getColumnIndex(Key_id));
            String title=cursor.getString(cursor.getColumnIndex(Key_title));
            String noteText=cursor.getString(cursor.getColumnIndex(Key_note));
            note=new Note(id,title,noteText);
        }
        return note;
    }
    public void updateNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Key_title,note.getTitle());
        values.put(Key_note,note.getNoteText());
        db.update(TABLE_NOTES,values,Key_id+"= ?",new String[]{String.valueOf(note.getNoteId())});
    }
    public void deletNote(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NOTES,Key_id+"=?",new String[]{String.valueOf(id)});
    }
}

