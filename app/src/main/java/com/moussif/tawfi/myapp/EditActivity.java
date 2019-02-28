package com.moussif.tawfi.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    DbManager db;
    int idNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        String title=bundle.getString("title");
        String inpute_note=bundle.getString("input_note");
        EditText titleD =(EditText) findViewById(R.id.edit_titleNote);
        EditText inpute_noteD =(EditText) findViewById(R.id.edit_input_note);
        titleD.setText(title);
        inpute_noteD.setText(inpute_note);
        idNote=bundle.getInt("id",0);
        db=new DbManager(this);
        Note note = db.getNoteById(idNote);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
        MenuItem item = menu.findItem(R.id.update);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update) {
            EditText titleD =(EditText) findViewById(R.id.edit_titleNote);
            EditText inpute_noteD =(EditText) findViewById(R.id.edit_input_note);
            String titleEdite=titleD.getText().toString();
            String noteEdite=inpute_noteD.getText().toString();
            Note newNote=new Note(idNote,titleEdite,noteEdite);
            db.updateNote(newNote);
            Toast.makeText(this,"note updated with success",Toast.LENGTH_LONG).show();
        }
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
