package com.example.notesapp;

public class NoteModal {

    //Name and Description
    private String noteTitle;
    private String noteDescription;

    //Constructor for Variables
    public NoteModal(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    //Creating getter and setters
    public String getNoteTitle() {

        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {

        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {

        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}