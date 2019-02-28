package com.moussif.tawfi.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditeNoteActivity extends AppCompatActivity {
    EditText titleNote,input_note;
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleNote=(EditText)findViewById(R.id.titleNote);
        input_note=(EditText)findViewById(R.id.input_note);
        db=new DbManager(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edite_note, menu);
        MenuItem item = menu.findItem(R.id.save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            String title=titleNote.getText().toString();
            String in_note=input_note.getText().toString();
            Note note=new Note(title,in_note);
            db.addNote(note);
            Toast.makeText(this,"add",Toast.LENGTH_LONG).show();
        }
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
