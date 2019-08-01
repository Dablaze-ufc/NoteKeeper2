package com.dablaze.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION = "com.dablaze.notekeeper.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    public static final String ORIGINAL_COURSE_ID ="com.dablaze.notekeeper.ORIGINAL_COURSE_ID";
    public static final String  ORIGINAL_NOTE_TITTLE ="com.dablaze.notekeeper.ORIGINAL_NOTE_TITTLE";
    public static final String  ORIGINAL_NOTE_TEXT ="com.dablaze.notekeeper.ORIGINAL_NOTE_TEXT";
    private NoteInfo note2;
    private boolean isNewNote;
    private Spinner spinnerCourses;
    private EditText noteText;
    private EditText noteTittle;
    private int mNotePosition;
    private boolean isCancelling;
    private String originalNoteCourseId;
    private String orinalNoteTittle;
    private String originalNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerCourses = (Spinner) findViewById(R.id.spinner_courses);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapterCourses);

        readDisplyedStateValues();
        if (savedInstanceState == null){
        saveOriginalNoteValues();}
        else{
            restoreOriginalNoteValues(savedInstanceState);
        }


        noteText = findViewById(R.id.edit_note);
        noteTittle = findViewById(R.id.text_note_tittle);

        if (isNewNote) {
            createNewNote();
        } else {
            displayNote(spinnerCourses, noteText, noteTittle);
        }

    }

    private void restoreOriginalNoteValues(Bundle savedInstanceState) {
        originalNoteCourseId = savedInstanceState.getString(ORIGINAL_COURSE_ID);
        originalNoteText = savedInstanceState.getString(ORIGINAL_NOTE_TEXT);
        orinalNoteTittle = savedInstanceState.getString(ORIGINAL_NOTE_TITTLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORIGINAL_COURSE_ID,originalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TEXT,originalNoteText);
        outState.putString(orinalNoteTittle,orinalNoteTittle);
    }

    private void saveOriginalNoteValues() {
        if (isNewNote)
            return;
        originalNoteCourseId = note2.getCourse().getCourseId();
        orinalNoteTittle = note2.getTitle();
        originalNoteText = note2.getText();

    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        note2 = dm.getNotes().get(mNotePosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCancelling) {
            if (isNewNote) {
                DataManager.getInstance().removeNote(mNotePosition);
            } else {
                restoreSavedState();
            }
        } else {
            saveNote();
        }
    }

    private void restoreSavedState() {
        CourseInfo courses = DataManager.getInstance().getCourse(originalNoteCourseId);
        note2.setCourse(courses);
        note2.setTitle(orinalNoteTittle);
        note2.setText(originalNoteText);
    }

    private void saveNote() {
        note2.setCourse((CourseInfo) spinnerCourses.getSelectedItem());
        note2.setText(noteText.getText().toString());
        note2.setTitle(noteTittle.getText().toString());
    }

    private void displayNote(Spinner spinnerCourses, EditText noteText, EditText noteTittle) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int cousesIndex = courses.indexOf(note2.getCourse());
        spinnerCourses.setSelection(cousesIndex);
        noteText.setText(note2.getText());
        noteTittle.setText(note2.getTitle());

    }

    private void readDisplyedStateValues() {
        Intent noteintent2 = getIntent();
        int position = noteintent2.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        isNewNote = position == POSITION_NOT_SET;
        if (!isNewNote)
            note2 = DataManager.getInstance().getNotes().get(position);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
        int lastNoteInsex=DataManager.getInstance().getNotes().size() -1;
        item.setEnabled(mNotePosition < lastNoteInsex);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_email) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {

            isCancelling = true;
            finish();
        }else if (id == R.id.action_next)
            moveNext();

        return super.onOptionsItemSelected(item);
    }

    private void moveNext() {
        saveNote();

        ++mNotePosition;
        note2 = DataManager.getInstance().getNotes().get(mNotePosition);
        saveOriginalNoteValues();
        displayNote(spinnerCourses, noteText, noteTittle);
        invalidateOptionsMenu();
     }

    private void sendEmail() {
        CourseInfo copurse = (CourseInfo) spinnerCourses.getSelectedItem();
        String subject = noteTittle.getText().toString();
        String body = "Check out what i learned on this pluralsight course \"" + copurse.getTitle() + "\" \n" + noteText.getText().toString();
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc2822");
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(mailIntent);
    }
}
