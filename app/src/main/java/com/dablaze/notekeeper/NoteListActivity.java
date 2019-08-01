package com.dablaze.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private NoteRecyclerAdapter fNoteRecyclerAdapter;

//    private ArrayAdapter<NoteInfo> mAdapternotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeDisplayContent();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
            });
        initializeDisplayContent();




        }

    @Override
    protected void onResume() {
        super.onResume();
//        mAdapternotes.notifyDataSetChanged();
        fNoteRecyclerAdapter.notifyDataSetChanged();

    }

    private void initializeDisplayContent() {


         final RecyclerView recyclerViewNotes = findViewById(R.id.list_notes);
         final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
         recyclerViewNotes.setLayoutManager(notesLayoutManager);


         List<NoteInfo> notes =DataManager.getInstance().getNotes();
        fNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
         recyclerViewNotes.setAdapter(fNoteRecyclerAdapter);


            }

    }



