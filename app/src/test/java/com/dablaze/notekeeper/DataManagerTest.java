package com.dablaze.notekeeper;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    @Test
    public void createNewNote () throws Exception {

        final DataManager dm = DataManager.getInstance();
        final CourseInfo course = dm.getCourse("android_async");
        final String noteTittle = "Test new note";
        final  String noteText = "This is the body";

        int newNoteIndex = dm.createNewNote();
         NoteInfo noteIndex = dm.getNotes().get(newNoteIndex);
        noteIndex.setCourse(course);
        noteIndex.setTitle(noteTittle);
        noteIndex.setText(noteText);

        NoteInfo compareNote = dm.getNotes().get(newNoteIndex);
        assertEquals(compareNote.getCourse(), course);
        assertEquals(compareNote.getTitle(), noteTittle);
        assertEquals(compareNote.getText(),noteText);
    }
}