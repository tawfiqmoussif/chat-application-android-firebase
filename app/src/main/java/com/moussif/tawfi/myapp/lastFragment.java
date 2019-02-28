package com.moussif.tawfi.myapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class lastFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("name");
    EditText editText;
    ListView listItem;
    NotesAdapter adapter = null;
    ArrayList<Note> listOfNotes ;
    DbManager db;
    public lastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_last, container, false);

        listItem=(ListView) view.findViewById(R.id.list_item);
        View footerView =  ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        listItem.addFooterView(footerView);

        //listOfNotes=db.getNotes2();
        //  db.querySearch("%");
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),EditeNoteActivity.class);
                startActivity(i);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        db=new DbManager(getActivity());
        listOfNotes=db.getAllNotes();
        adapter=new NotesAdapter(getContext(), listOfNotes);
        listItem.setAdapter(adapter);

        super.onResume();
    }
    class NotesAdapter extends BaseAdapter {
        Context context;
        List<Note> list;
        DbManager db;
        int resource;
        public NotesAdapter(Context context, List list) {
            this.context = context;
            this.list=list;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

            convertView = inflater.inflate(R.layout.note_layout,null);
            TextView note_text=(TextView) convertView.findViewById(R.id.note_text);
            TextView title=(TextView) convertView.findViewById(R.id.note_title);
            title.setText(list.get(position).getTitle());
            note_text.setText(list.get(position).getNoteText());
            ImageView note_delete=(ImageView)convertView.findViewById(R.id.note_delete);
            note_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert();
                }

                private void showAlert() {
                    AlertDialog.Builder alertBuilder=new AlertDialog.Builder(context);
                    alertBuilder.setTitle("Confirmation")
                            .setMessage("Are you sure ? ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db=new DbManager(context);
                                    int idNote=list.get(position).getNoteId();
                                    db.deletNote(idNote);
                                    onResume();
                                }


                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog=alertBuilder.create();
                    dialog.show();
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,EditActivity.class);
                    intent.putExtra("id",list.get(position).getNoteId());
                    intent.putExtra("title",list.get(position).getTitle());
                    intent.putExtra("input_note",list.get(position).getNoteText());
                    context.startActivity(intent);
                }
            });



            return convertView;
        }



        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }


}
